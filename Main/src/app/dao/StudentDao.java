package app.dao;

import app.model.Student;
import app.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        String fullName = (student.getLastName() + " " + student.getFirstName()).trim();
        String username = student.getStudentId();

        String query = String.format(
                "INSERT INTO students (username, password, fullname, email, phone) VALUES ('%s', '%s', '%s', '%s', '%s')",
                escapeString(username),
                escapeString("Pass1234"),
                escapeString(fullName),
                escapeString(student.getEmail() != null ? student.getEmail() : ""),
                escapeString(student.getPhoneNumber() != null ? student.getPhoneNumber() : "")
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Thêm sinh viên thành công - Username: " + username + ", Họ tên: " + fullName);
            }
            return result;
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

    public static boolean deleteStudent(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng!");
            return false;
        }

        String query = String.format(
                "DELETE FROM students WHERE username = '%s'",
                escapeString(username)
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Xóa sinh viên thành công - Username: " + username);
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Student mapToStudent(HashMap<String, Object> row) {
        try {
            String username = (String) row.get("username");
            String fullname = (String) row.get("fullname");
            String email = (String) row.get("email");
            String phone = (String) row.get("phone");

            String firstName = "";
            String lastName = "";
            if (fullname != null && !fullname.isEmpty()) {
                String[] nameParts = fullname.trim().split("\\s+");
                if (nameParts.length > 0) {
                    lastName = nameParts[0];
                    if (nameParts.length > 1) {
                        firstName = String.join(" ", java.util.Arrays.copyOfRange(nameParts, 1, nameParts.length));
                    }
                }
            }

            Student student = new Student(username, firstName, lastName, email, phone);
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

    /**
     * Collect danh sách các môn học và điểm số của SV từ database.
     * @param studentId ID của SV cần truy vấn.
     * @return Một List chứa thông tin môn học và điểm số.
     */
    public List<HashMap<String, Object>> findClassAndScoreByStudentId(int studentId) {
        // subject, group, student_in_group
        String sqlQuery = "SELECT s.name AS subject_name, sig.score " +
                "FROM student_in_group sig " +
                "JOIN `group` g ON sig.group_id = g.id " +
                "JOIN subject s ON g.subject_id = s.id " +
                "WHERE sig.student_id = " + studentId;

        // In SQL command ra để check
        System.out.println("Executing SQL for Task 1: " + sqlQuery);

        // Gọi readTable từ lớp kết nối chung để execute
        return DatabaseConnection.readTable(sqlQuery);
    }

}
