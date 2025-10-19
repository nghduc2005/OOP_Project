package app.dao;

import app.model.Student;
import app.model.Subject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentDao {

    public static Student getStudentById(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            System.out.println("Mã sinh viên không được rỗng!");
            return null;
        }

        String query = String.format(
                "SELECT * FROM student WHERE student_id = '%s'",
                escapeString(studentId)
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
        String query = "SELECT * FROM student ORDER BY student_id";
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

        String query = String.format(
                "INSERT INTO student (student_id, student_name, email, phone) VALUES ('%s', '%s', '%s', '%s')",
                escapeString(student.getStudentId()),
                escapeString(student.getStudentName()),
                escapeString(student.getEmail() != null ? student.getEmail() : ""),
                escapeString(student.getPhone() != null ? student.getPhone() : "")
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Thêm sinh viên thành công: " + student.getStudentName());
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateStudent(Student student) {
        if (student == null) {
            System.out.println("Student không được null!");
            return false;
        }

        String query = String.format(
                "UPDATE student SET student_name = '%s', email = '%s', phone = '%s' WHERE student_id = '%s'",
                escapeString(student.getStudentName()),
                escapeString(student.getEmail() != null ? student.getEmail() : ""),
                escapeString(student.getPhone() != null ? student.getPhone() : ""),
                escapeString(student.getStudentId())
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Cập nhật sinh viên thành công: " + student.getStudentName());
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sinh viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            System.out.println("Mã sinh viên không được rỗng!");
            return false;
        }

        String query = String.format(
                "DELETE FROM student WHERE student_id = '%s'",
                escapeString(studentId)
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Xóa sinh viên thành công: " + studentId);
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
            String studentId = (String) row.get("student_id");
            String studentName = (String) row.get("student_name");
            String email = (String) row.get("email");
            String phone = (String) row.get("phone");

            Student student = new Student(studentId, studentName, new ArrayList<>());
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
}
