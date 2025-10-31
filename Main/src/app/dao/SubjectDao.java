package app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.model.Subject;

public class SubjectDao {

    public static boolean createSubject(Subject subject) {
        if (subject == null) {
            System.out.println("Subject không được null!");
            return false;
        }

        if (!validateSubject(subject)) return false;
        if (isSubjectIdExists(Integer.parseInt(subject.getSubjectId()))) {
            System.out.println("Mã môn học đã tồn tại: " + subject.getSubjectId());
            return false;
        }

        String query = String.format(
                "INSERT INTO subjects (subject_id, name, credit, test_type) " +
                        "VALUES (%d, '%s', %d, '%s')",
                subject.getSubjectId(),
                escapeString(subject.getSubjectName()),
                subject.getCredit(),
                escapeString(subject.getTestType() != null ? subject.getTestType() : "")
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) System.out.println("Thêm môn học thành công: " + subject.getSubjectName());
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm môn học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Subject getSubjectById(Integer subjectId) {
        if (subjectId == null) {
            System.out.println("Mã môn học không được null!");
            return null;
        }

        String query = String.format(
                "SELECT * FROM subjects WHERE subject_id = %d",
                subjectId
        );

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                HashMap<String, Object> row = results.get(0);
                return mapToSubject(row);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin môn học: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<Subject> getAllSubjects() {
        String query = "SELECT * FROM subject ORDER BY subject_id";
        List<Subject> subjects = new ArrayList<>();

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Subject subject = mapToSubject(row);
                    if (subject != null) subjects.add(subject);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách môn học: " + e.getMessage());
            e.printStackTrace();
        }
        return subjects;
    }

    public static List<Subject> getSubjectsByTeacher(String teacherName) {
        if (teacherName == null || teacherName.trim().isEmpty()) {
            System.out.println("Tên giảng viên không được rỗng!");
            return new ArrayList<>();
        }

        String query = String.format(
                "SELECT * FROM subjects WHERE teacher_name = '%s' ORDER BY subject_id",
                escapeString(teacherName)
        );

        List<Subject> subjects = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Subject subject = mapToSubject(row);
                    if (subject != null) subjects.add(subject);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách môn học của giảng viên: " + e.getMessage());
            e.printStackTrace();
        }
        return subjects;
    }

    public static boolean updateSubject(Subject subject) {
        if (subject == null) {
            System.out.println("Subject không được null!");
            return false;
        }

        if (!validateSubject(subject)) return false;
        if (!isSubjectIdExists(Integer.parseInt(subject.getSubjectId()))) {
            System.out.println("Môn học không tồn tại: " + subject.getSubjectId());
            return false;
        }

        String query = String.format(
                "UPDATE subjects SET name = '%s', credit = %d, test_type = '%s' " +
                        "WHERE subject_id = %d",
                escapeString(subject.getSubjectName()),
                subject.getCredit(),
                escapeString(Integer.parseInt(subject.getTestType()) != null ? subject.getTestType() : ""),
                subject.getSubjectId()
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) System.out.println("Cập nhật môn học thành công: " + subject.getSubjectName());
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật môn học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteSubject(Integer subjectId) {
        if (subjectId == null) {
            System.out.println("Mã môn học không được null!");
            return false;
        }

        if (!isSubjectIdExists(subjectId)) {
            System.out.println("Môn học không tồn tại: " + subjectId);
            return false;
        }

        String query = String.format(
                "DELETE FROM subjects WHERE subject_id = %d",
                subjectId
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) System.out.println("Xóa môn học thành công: " + subjectId);
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa môn học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSubjectIdExists(Integer subjectId) {
        if (subjectId == null) return false;

        String query = String.format(
                "SELECT COUNT(*) as count FROM subjects WHERE subject_id = %d",
                subjectId
        );

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                Object countObj = results.get(0).get("count");
                long count = 0;
                if (countObj instanceof Integer) count = ((Integer) countObj).longValue();
                else if (countObj instanceof Long) count = (Long) countObj;
                return count > 0;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi kiểm tra mã môn học: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static List<Subject> searchSubjectByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) return getAllSubjects();

        String query = String.format(
                "SELECT * FROM subjects WHERE LOWER(subject_name) LIKE LOWER('%%%s%%') ORDER BY subject_id",
                escapeString(searchTerm)
        );

        List<Subject> subjects = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Subject subject = mapToSubject(row);
                    if (subject != null) subjects.add(subject);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm môn học: " + e.getMessage());
            e.printStackTrace();
        }
        return subjects;
    }

    private static boolean validateSubject(Subject subject) {
        if (subject.getSubjectId() == null) {
            System.out.println("Mã môn học không được null!");
            return false;
        }
        if (subject.getSubjectName() == null || subject.getSubjectName().trim().isEmpty()) {
            System.out.println("Tên môn học không được rỗng!");
            return false;
        }
        if (subject.getCredit() < 1 || subject.getCredit() > 10) {
            System.out.println("Số tín chỉ phải từ 1 đến 10!");
            return false;
        }
        return true;
    }

    private static Subject mapToSubject(HashMap<String, Object> row) {
        try {
            Integer subjectId = (Integer) row.get("subject_id");
            String subjectName = (String) row.get("name");
            Object creditObj = row.get("credit");
            Integer credit = creditObj instanceof Integer ? (Integer) creditObj : Integer.parseInt(creditObj.toString());
            String testType = (String) row.get("test_type");
            return new Subject(subjectId, subjectName, credit, testType);
        } catch (Exception e) {
            System.out.println("Lỗi khi chuyển đổi dữ liệu môn học: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static String escapeString(String str) {
        if (str == null) return "";
        return str.replace("'", "''");
    }

    /**
     * @param subjectId
     * @param studentId
     * @return List gồm điểm CC, GK, TH, CK
     */
    public static List<HashMap<String, Object>> getGradeSubject(String subjectId, String studentId) {
        if (subjectId == null || subjectId.trim().isEmpty()) return null;

        String query = String.format(
                "SELECT grade_type, score FROM grades " +
                        "WHERE subject_id = '%s' AND student_id = '%s' " +
                        "AND grade_type IN ('CC', 'GK', 'TH', 'CK')",
                escapeString(subjectId),
                escapeString(studentId)
        );

        try {
            // Tái sử dụng phương thức đọc bảng đã có
            return DatabaseConnection.readTable(query);
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy điểm: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param subjectId
     * @return teacher fullname
     */
    public static String getTeacherNameBySubjectId(String subjectId) {
        if (subjectId == null || subjectId.trim().isEmpty()) return "Chưa phân công";

        // Truy vấn
        String query = String.format(
                "SELECT t.fullname FROM teachers t " +
                        "INNER JOIN classes c ON t.teacher_id = c.teacher_id " +
                        "WHERE c.subject_id = '%s' LIMIT 1",
                escapeString(subjectId)
        );

        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                // Tên GV được lưu trong cột 'fullname'
                Object fullNameObj = results.get(0).get("fullname");
                return fullNameObj != null ? fullNameObj.toString() : "Chưa phân công";
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy tên giảng viên: " + e.getMessage());
            e.printStackTrace();
        }
        return "Chưa phân công";
    }
}
