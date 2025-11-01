package app.dao;
import app.model.*;
import java.util.*;

public class ClassDao {

    public static boolean CreateClass(Classes cl) {
        if (cl == null) {
            System.out.println("Đối tượng lớp bị null!");
            return false;
        }

        if (isClassIdExists(String.valueOf(cl.getClassId()))) {
            System.out.println("Class ID đã tồn tại, không thể thêm mới!");
            return false;
        }

        String query = String.format(
                "INSERT INTO classes (teacher_id, class_id, total_student, subject_name, maxnumberstudent) " +
                        "VALUES (1 ,'%s', '%s', '%s', '%s')",
                escapeString(String.valueOf(cl.getClassId())),
                escapeString(String.valueOf(cl.getTotalStudent())),
                escapeString(String.valueOf(cl.getSubjectName())),
                escapeString(String.valueOf(cl.getMaxNumberStudent()))
        );

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

    // ✅ HÀM SỬA LỚP HỌC
    public static boolean updateClass(Classes cl) {
        // 1️⃣ Kiểm tra dữ liệu đầu vào
        if (cl == null || cl.getClassId() == null) {
            System.out.println("Dữ liệu lớp học không hợp lệ!");
            return false;
        }

        // 2️⃣ Kiểm tra lớp có tồn tại không
        if (!isClassIdExists(String.valueOf(cl.getClassId()))) {
            System.out.println("Không tìm thấy lớp có mã: " + cl.getClassId());
            return false;
        }

        // 3️⃣ Câu truy vấn SQL UPDATE
        String query = String.format(
                "UPDATE classes SET total_student = '%s', subject_name = '%s', maxnumberstudent = '%s' " +
                        "WHERE class_id = '%s'",
                escapeString(String.valueOf(cl.getTotalStudent())),
                escapeString(String.valueOf(cl.getSubjectName())),
                escapeString(String.valueOf(cl.getMaxNumberStudent())),
                escapeString(String.valueOf(cl.getClassId()))
        );

        // 4️⃣ Thực thi truy vấn
        try {
            boolean result = DatabaseConnection.updateRecord(query);
            if (result) {
                System.out.println("Cập nhật thông tin lớp học thành công!");
                return true;
            } else {
                System.out.println("Cập nhật lớp học thất bại!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật lớp học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ HÀM KIỂM TRA CLASS_ID
    private static boolean isClassIdExists(String classId) {
        if (classId == null || classId.trim().isEmpty()) {
            return false;
        }

        String query = String.format(
                "SELECT COUNT(*) AS count FROM classes WHERE class_id = '%s'",
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

    // ✅ HÀM XÓA LỚP
    public static boolean deleteClass(String classId) {
        if (classId == null || classId.trim().isEmpty()) {
            System.out.println("Mã lớp không hợp lệ!");
            return false;
        }

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

    // ✅ HÀM XỬ LÝ CHUỖI AN TOÀN
    private static String escapeString(String str) {
        if (str == null) return "";
        return str.replace("'", "''");
    }
}
