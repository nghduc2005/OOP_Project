package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AutoGeneratorDao {

    public static String generateStudentId() {
        int currentYear = LocalDate.now().getYear() % 100;
        String prefix = "B" + String.format("%02d", currentYear) + "DCCN";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                return generateStudentIdFallback();
            }

            String query = "SELECT student_id FROM students WHERE student_id LIKE ? ORDER BY student_id DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, prefix + "%");

            ResultSet rs = stmt.executeQuery();

            int nextCounter = 470;
            if (rs.next()) {
                String lastStudentId = rs.getString("student_id");
                String counterStr = lastStudentId.substring(prefix.length());
                int lastCounter = Integer.parseInt(counterStr);
                nextCounter = lastCounter + 1;
            }

            return prefix + String.format("%03d", nextCounter);

        } catch (SQLException e) {
            System.err.println("Database error in generateStudentId: " + e.getMessage());
            return generateStudentIdFallback();
        }
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
}
