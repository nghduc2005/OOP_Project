package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.Teacher;
import app.session.Session;

public class TeacherDao {
    private static String escapeString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }
    public static String getPasswordByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng!");
            return null;
        }

        String sql = "SELECT password FROM teachers WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy mật khẩu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateTeacher(String full_name, String birthday, String phone, String email) {


        String query = String.format(
                "UPDATE teachers SET fullname = '%s', dateOfBirth = '%s' ,email = '%s', phone = '%s' WHERE username = '%s'",
                escapeString(full_name),
                escapeString(birthday),
                escapeString(email != null ? email: ""),
                escapeString(phone != null ? phone : ""),
                escapeString(Session.getUsername())
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Cập nhật giáo viên thành công ");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật giáo viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy tất cả giảng viên từ database
     */
    public static List<Teacher> getAllTeachers() {
        String query = "SELECT * FROM teachers ORDER BY teacher_id";
        List<Teacher> teachers = new ArrayList<>();

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Teacher teacher = mapToTeacher(row);
                    if (teacher != null) {
                        teachers.add(teacher);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách giảng viên: " + e.getMessage());
            e.printStackTrace();
        }
        return teachers;
    }

    /**
     * Map dữ liệu từ database sang Teacher object
     */
    private static Teacher mapToTeacher(HashMap<String, Object> row) {
        try {
            Integer teacherId = (Integer) row.get("teacher_id");
            String userName = (String) row.get("username");
            String password = (String) row.get("password");
            String phoneNumber = (String) row.get("phone_number");
            String email = (String) row.get("email");
            
            String firstName = "";
            String lastName = "";
            
            // Try to use first_name and last_name from database first
            Object firstNameObj = row.get("first_name");
            Object lastNameObj = row.get("last_name");
            
            if (firstNameObj != null && lastNameObj != null) {
                firstName = (String) firstNameObj;
                lastName = (String) lastNameObj;
            } else {
                // Fallback to splitting fullname if first/last names not available
                String fullname = (String) row.get("fullname");
                if (fullname != null && !fullname.isEmpty()) {
                    String[] nameParts = fullname.trim().split("\\s+");
                    if (nameParts.length > 0) {
                        lastName = nameParts[0];
                        if (nameParts.length > 1) {
                            firstName = String.join(" ", java.util.Arrays.copyOfRange(nameParts, 1, nameParts.length));
                        }
                    }
                }
            }
            
            // Parse date of birth
            Object dobObj = row.get("date_of_birth");
            LocalDate dateOfBirth = null;
            if (dobObj instanceof java.sql.Date) {
                dateOfBirth = ((java.sql.Date) dobObj).toLocalDate();
            } else if (dobObj instanceof String) {
                dateOfBirth = LocalDate.parse((String) dobObj);
            }

            return new Teacher(teacherId, lastName, firstName, userName, password, 
                             phoneNumber, email, dateOfBirth);
        } catch (Exception e) {
            System.out.println("Lỗi khi chuyển đổi dữ liệu giảng viên: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
