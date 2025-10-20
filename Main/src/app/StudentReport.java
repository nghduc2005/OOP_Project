package app;

import app.dao.DatabaseConnection;
import java.util.HashMap;
import java.util.List;

/**
 * In báo cáo tên, id, môn, giáo viên, điểm
 */
public class StudentReport {

    public static void main(String[] args) {
        // SQL
        String sqlQuery = "SELECT " +
                "    stu.fullname AS student_name, " +
                "    stu.username AS student_id, " +
                "    sub.name AS subject_name, " +
                "    t.fullname AS teacher_name, " +
                "    sig.score " +
                "FROM " +
                "    student_in_group sig " +
                "JOIN " +
                "    students stu ON sig.student_id = stu.id " +
                "JOIN " +
                "    `group` g ON sig.group_id = g.id " +
                "JOIN " +
                "    subject sub ON g.subject_id = sub.id " +
                "JOIN " +
                "    teachers t ON g.teacher_id = t.id " +
                "ORDER BY " +
                "    stu.fullname, sub.name";

        System.out.println("Đang thực thi truy vấn để lấy báo cáo điểm...");

        // Gọi readTable để lấy dữ liệu từ db
        List<HashMap<String, Object>> data = DatabaseConnection.readTable(sqlQuery);

        System.out.println("\n============== BÁO CÁO ==============");

        // Check xem có dữ liệu trả về hay không
        if (data == null || data.isEmpty()) {
            System.out.println("-> Không có dữ liệu để hiển thị. (Vì chưa có bản ghi mẫu)");
        } else {
            // Dữ liệu được in
            System.out.printf("%-25s | %-15s | %-30s | %-25s | %s%n", "Học sinh", "ID", "Môn học", "Giáo viên", "Điểm");
            System.out.println(new String(new char[105]).replace('\0', '-')); // In dòng kẻ ngang

            for (HashMap<String, Object> row : data) {
                String studentName = (String) row.get("student_name");
                String studentId = (String) row.get("student_id");
                String subjectName = (String) row.get("subject_name");
                String teacherName = (String) row.get("teacher_name");
                Object scoreObj = row.get("score");
                String scoreStr = (scoreObj == null) ? "N/A" : scoreObj.toString();

                System.out.printf("%-25s | %-15s | %-30s | %-25s | %s%n",
                        studentName, studentId, subjectName, teacherName, scoreStr);
            }
        }
        System.out.println("==========================================================");
    }
}