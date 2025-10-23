package app.dao;
import app.model.*;
import java.util.*;
public class ClassDao {
    public boolean CreateClass(Classes cl) {
        // 1️⃣ Kiểm tra dữ liệu đầu vào
        if (cl == null) {
            System.out.println("Đối tượng lớp bị null!");
            return false;
        }

        // 2️⃣ Kiểm tra class_id đã tồn tại chưa
        if (isClassIdExists(String.valueOf(cl.getClass_id()))) {
            System.out.println("Class ID đã tồn tại, không thể thêm mới!");
            return false;
        }

        // 3️⃣ Tạo câu lệnh SQL INSERT
        String query = String.format(
                "INSERT INTO classes (class_id, total_student, subject_name, maxnumberstudent) " +
                        "VALUES ('%s', '%s', '%s', '%s')",
                escapeString(String.valueOf(cl.getClass_id())),
                escapeString(String.valueOf(cl.getTotal_student())),
                escapeString(String.valueOf(cl.getSubject_name())),
                escapeString(String.valueOf(cl.getMaxNumberStudent()))
        );

        // 4️⃣ Thực thi truy vấn
        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Thêm lớp học mới thành công!");
                return true;
            } else {
                System.out.println("Thêm lớp học thất bại!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm lớp học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isClassIdExists(String classId) {
        if (classId == null || classId.trim().isEmpty()) {
            return false;
        }

        String query = String.format(
                "SELECT COUNT(*) AS count FROM class WHERE class_id = '%s'",
                escapeString(classId)
        );

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                Object countObj = results.get(0).get("count");
                long count = 0;
                if (countObj instanceof Integer) {
                    count = ((Integer) countObj).longValue();
                } else if (countObj instanceof Long) {
                    count = (Long) countObj;
                }
                return count > 0;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra class_id: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    private static String escapeString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }
    public boolean deleteClass(String classId) {
        if (classId == null || classId.trim().isEmpty()) {
            System.out.println("Mã lớp không hợp lệ!");
            return false;
        }

        // Kiểm tra lớp có tồn tại không
        if (!isClassIdExists(classId)) {
            System.out.println("Không tìm thấy lớp có mã: " + classId);
            return false;
        }

        String query = String.format(
                "DELETE FROM classes WHERE class_id = '%s'",
                escapeString(classId)
        );

        try {
            boolean result = DatabaseConnection.deleteRecord(query);
            if (result) {
                System.out.println("Xóa lớp học thành công!");
                return true;
            } else {
                System.out.println("Xóa lớp học thất bại!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa lớp học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
