package app.dao;
import app.model.*;
import java.util.*;

public class ClassDao {

    public static boolean CreateClass(Classes cl) {
        if (cl == null) {
            System.out.println("Đối tượng lớp bị null!");
            return false;
        }

        String query = String.format(
                "INSERT INTO classes (teacher_id, subject_name, maxnumberstudent, subject_id, credit) " +
                        "VALUES (1 ,'%s', '%s', '%s', '%s')",
                escapeString(String.valueOf(cl.getSubjectName())),
                escapeString(String.valueOf(cl.getMaxNumberStudent())),
                escapeString(String.valueOf(cl.getSubjectId())),
                escapeString(String.valueOf(cl.getCredit()))
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

    //  HÀM SỬA LỚP HỌC
    public static boolean updateClass(Classes cl) {
        //  Kiểm tra dữ liệu đầu vào
        if (cl == null || cl.getClassId() == null) {
            System.out.println(cl + " " + cl.getClassId());
            System.out.println("Dữ liệu lớp học không hợp lệ!");
            return false;
        }

        // Kiểm tra lớp có tồn tại không
        if (!isClassIdExists(String.valueOf(cl.getClassId()))) {
            System.out.println("Không tìm thấy lớp có mã: " + cl.getClassId());
            return false;
        }

        //  Câu truy vấn SQL UPDATE
        String query = String.format(
                "UPDATE classes SET subject_name = '%s', maxnumberstudent = '%s', credit = '%s', subject_id ='%s'" +
                        "WHERE class_id = '%s'",
                escapeString(String.valueOf(cl.getSubjectName())),
                escapeString(String.valueOf(cl.getMaxNumberStudent())),
                escapeString(String.valueOf(cl.getCredit())),
                escapeString(String.valueOf(cl.getSubjectId())),
                escapeString(String.valueOf(cl.getClassId()))
        );

        //  Thực thi truy vấn
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

    //HÀM KIỂM TRA CLASS_ID
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

    // HÀM XÓA LỚP
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
    public static List<Classes> getAllClasses() {
        String query = "SELECT class_id, subject_name, subject_id FROM classes ORDER BY class_id";
        List<Classes> classes = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Classes cl = new Classes();
                    cl.setClassId((int) row.get("class_id"));
                    cl.setSubjectName((String) row.get("subject_name"));
                    cl.setSubjectId((int) row.get("subject_id"));
                    classes.add(cl);
                }
            }
            return classes;
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách sinh viên: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    // HÀM XỬ LÝ CHUỖI AN TOÀN
    private static String escapeString(String str) {
        if (str == null) return "";
        return str.replace("'", "''");
    }
}
