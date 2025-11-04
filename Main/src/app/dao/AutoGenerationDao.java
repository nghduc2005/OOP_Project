package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.dao.DatabaseConnection;


public class AutoGenerationDao {

    public static String generateStudentId() {
        String query = "SELECT MIN(t1.student_id + 1) AS missing_id FROM students t1 LEFT JOIN students t2 ON t1" +
                ".student_id + 1 = t2.student_id WHERE t2.student_id IS NULL;";
        List<HashMap<String, Object>> list = DatabaseConnection.readTable(query);
        String studentId = "";
        for (HashMap<String, Object> map : list) {
            for (String key : map.keySet()) {
                studentId = String.valueOf( (Long) map.get(key));
            }
        }
        return String.format("STU%06d", Integer.parseInt(studentId));
    }

    public static String generateGroupId() {
        String prefix = "INT";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                return generateGroupIdFallback();
            }

            String query = "SELECT group_id FROM groups WHERE group_id LIKE ? ORDER BY group_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, prefix + "%");

            ResultSet rs = stmt.executeQuery();

            int nextCounter = 1319;
            if (rs.next()) {
                String lastGroupId = rs.getString("group_id");
                String counterPart = lastGroupId.replace(prefix, "");
                int lastCounter = Integer.parseInt(counterPart);
                nextCounter = lastCounter + 1;
            }

            return prefix + nextCounter;

        } catch (SQLException e) {
            System.err.println("Database error in generateGroupId: " + e.getMessage());
            return generateGroupIdFallback();
        }
    }

    public static boolean isStudentIdExists(String studentId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;

            String query = "SELECT COUNT(*) FROM students WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking student ID: " + e.getMessage());
        }
        return false;
    }

    public static boolean isGroupIdExists(String groupId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) return false;

            String query = "SELECT COUNT(*) FROM groups WHERE group_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, groupId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking group ID: " + e.getMessage());
        }
        return false;
    }

    private static String generateStudentIdFallback() {
        int currentYear = LocalDate.now().getYear() % 100;
        int randomNum = (int)(Math.random() * 900) + 470;
        return "B" + String.format("%02d", currentYear) + "DCCN" + randomNum;
    }

    private static String generateGroupIdFallback() {
        int randomNum = (int)(Math.random() * 90000) + 1319;
        return "INT" + randomNum;
    }

    public static String getUniqueStudentId() {
        String studentId;
        int attempts = 0;
        do {
            studentId = generateStudentId();
            attempts++;
            if (attempts > 10) {
                System.err.println("Too many attempts to generate unique student ID");
                break;
            }
        } while (isStudentIdExists(studentId));

        return studentId;
    }

    public static String getUniqueGroupId() {
        String groupId;
        int attempts = 0;
        do {
            groupId = generateGroupId();
            attempts++;
            if (attempts > 10) {
                System.err.println("Too many attempts to generate unique group ID");
                break;
            }
        } while (isGroupIdExists(groupId));

        return groupId;
    }

    /**
     * Tự động tạo ID cho bảng bất kỳ
     * @param tableName Tên bảng
     * @param columnName Tên cột ID
     * @param prefix Prefix của ID (vd: SCH, STU, GRP)
     * @return ID mới được tạo
     */
    public static String autoGenerationId(String tableName, String columnName, String prefix) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                return generateFallbackId(prefix);
            }

            String query = String.format(
                "SELECT %s FROM %s WHERE %s LIKE ? ORDER BY %s DESC LIMIT 1",
                columnName, tableName, columnName, columnName
            );
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, prefix + "%");

            ResultSet rs = stmt.executeQuery();

            int nextCounter = 1;
            if (rs.next()) {
                String lastId = rs.getString(columnName);
                if (lastId != null && lastId.startsWith(prefix)) {
                    String counterStr = lastId.substring(prefix.length());
                    try {
                        int lastCounter = Integer.parseInt(counterStr);
                        nextCounter = lastCounter + 1;
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing counter: " + counterStr);
                    }
                }
            }

            return prefix + String.format("%06d", nextCounter);

        } catch (SQLException e) {
            System.err.println("Database error in autoGenerationId: " + e.getMessage());
            return generateFallbackId(prefix);
        }
    }

    private static String generateFallbackId(String prefix) {
        int randomNum = (int)(Math.random() * 900000) + 1;
        return prefix + String.format("%06d", randomNum);
    }
}
