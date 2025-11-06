package app.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.Student;
import app.session.Session;

public class StudentDao {

    public static Student getStudentByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng!");
            return null;
        }

        String query = String.format(
                "SELECT * FROM students WHERE username = '%s'",
                escapeString(username)
        );

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                HashMap<String, Object> row = results.get(0);
                return mapToStudent(row);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<Student> getAllStudents() {
        String query = "SELECT * FROM students ORDER BY student_id";
        List<Student> students = new ArrayList<>();

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Student student = mapToStudent(row);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    public static boolean createStudent(Student student) {
        if (student == null) {
            System.out.println("Student không được null!");
            return false;
        }
        System.out.println(student.getClassId());
        // student_id, username, password, fullname, dateOfBirth, email, phone
        String sql = "INSERT INTO students (student_id, username, password, fullname, dateOfBirth, email, phone, first_name, last_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String fullName = (student.getLastName() + " " + student.getFirstName()).trim();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // nếu student_id là số tự tăng, không thì fix sau
            String sql_fixed = "INSERT INTO students (username, password, fullname, dateOfBirth, email, phone, " +
                    "first_name, last_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt_fixed = conn.prepareStatement(sql_fixed);

            stmt_fixed.setString(1, student.getUserName());
            stmt_fixed.setString(2, student.getPassword());
            stmt_fixed.setString(3, fullName);
            stmt_fixed.setObject(4, student.getDateOfBirth());
            stmt_fixed.setString(5, student.getEmail());
            stmt_fixed.setString(6, student.getPhoneNumber());
            stmt_fixed.setString(7, student.getFirstName());
            stmt_fixed.setString(8, student.getLastName());

            int rowsAffected = stmt_fixed.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm sinh viên thành công - Username: " + student.getUserName());
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println(student.getClassId());
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static boolean addStudentInClass(String username, int classid) {
        String insertSQL = "INSERT INTO student_class (username, class_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, username);
            stmt.setInt(2, classid);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thêm sinh viên thành công - Username: " + username);
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateStudent(String full_name, String birthday, String phone, String email) {


        String query = String.format(
                "UPDATE students SET fullname = '%s', dateOfBirth = '%s', email = '%s', phone = '%s' WHERE username = '%s'",
                escapeString(full_name),
                escapeString(birthday),
                escapeString(email != null ? email: ""),
                escapeString(phone != null ? phone : ""),
                escapeString(Session.getUsername())
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Cập nhật sinh viên thành công ");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin sinh viên dựa theo username (dùng cho EditStudent.java)
     */
    public static boolean updateStudentByUsername(String username, BigDecimal attendence, BigDecimal assignment,
                                                  BigDecimal midterm, BigDecimal finalGrade, int classId) {

        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng khi cập nhật!");
            return false;
        }

        String sql = "UPDATE student_class SET attendence = ?, assignment =?, midterm = ?, final = ? WHERE username =" +
                " ? and class_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, attendence);
            stmt.setBigDecimal(2, assignment);
            stmt.setBigDecimal(3, midterm);
            stmt.setBigDecimal(4, finalGrade);
            stmt.setString(5, username);
            stmt.setInt(6, classId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật sinh viên thành công (by parameter) - Username: " + username);
                return true;
            }
            System.out.println("Không tìm thấy sinh viên để cập nhật (by parameter) - Username: " + username);
            return false;

        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sinh viên (by parameter): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteStudent(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng!");
            return false;
        }

        String sql = "DELETE FROM students WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa sinh viên thành công - Username: " + username);
                return true;
            }
            System.out.println("Không tìm thấy sinh viên để xóa - Username: " + username);
            return false;

        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Student mapToStudent(HashMap<String, Object> row) {
        try {
            Integer studentId = (Integer) row.get("student_id");
            String username = (String) row.get("username");
            String fullname = (String) row.get("fullname");
            String email = (String) row.get("email");
            String phone = (String) row.get("phone");

            String firstName = "";
            String lastName = "";
            
            // Try to use first_name and last_name from database first
            Object firstNameObj = row.get("first_name");
            Object lastNameObj = row.get("last_name");
            
            if (firstNameObj != null && lastNameObj != null) {
                firstName = (String) firstNameObj;
                lastName = (String) lastNameObj;
            } else if (fullname != null && !fullname.isEmpty()) {
                // Fallback to splitting fullname
                String[] nameParts = fullname.trim().split("\\s+");
                if (nameParts.length > 0) {
                    lastName = nameParts[0];
                    if (nameParts.length > 1) {
                        firstName = String.join(" ", java.util.Arrays.copyOfRange(nameParts, 1, nameParts.length));
                    }
                }
            }

            Student student = new Student(studentId, firstName, lastName, email, phone);
            return student;
        } catch (Exception e) {
            System.out.println("Lỗi khi chuyển đổi dữ liệu sinh viên: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

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

        String sql = "SELECT password FROM students WHERE username = ?";

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

    public static boolean deleteStudentInClass(String username, int classid) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng!");
            return false;
        }

        String sql = "DELETE FROM student_class WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Xóa sinh viên thành công - Username: " + username);
                return true;
            }
            System.out.println("Không tìm thấy sinh viên để xóa - Username: " + username);
            return false;

        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
