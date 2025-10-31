package app.service;

import app.dao.SubjectDao;
import app.model.Subject;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class SubjectService {

    public static boolean createSubject(String subjectId, String subjectName, int credit, String teacherName) {
        validateSubjectInput(subjectId, subjectName, credit);
        Subject subject = new Subject(subjectId, subjectName, credit);
        if (teacherName != null && !teacherName.trim().isEmpty()) {
            subject.setTeacherName(teacherName);
        }
        boolean result = SubjectDao.createSubject(subject);
        if (result) {
            System.out.println("✓ Tạo môn học thành công!");
        } else {
            System.out.println("✗ Tạo môn học thất bại!");
        }
        return result;
    }

    public static boolean updateSubject(String subjectId, String subjectName, int credit, String teacherName) {
        validateSubjectInput(subjectId, subjectName, credit);
        Subject existingSubject = SubjectDao.getSubjectById(Integer.parseInt(subjectId));
        if (existingSubject == null) {
            throw new IllegalArgumentException("Môn học không tồn tại: " + subjectId);
        }
        Subject subject = new Subject(subjectId, subjectName, credit);
        if (teacherName != null && !teacherName.trim().isEmpty()) {
            subject.setTeacherName(teacherName);
        }
        boolean result = SubjectDao.updateSubject(subject);
        if (result) {
            System.out.println("✓ Cập nhật môn học thành công!");
        } else {
            System.out.println("✗ Cập nhật môn học thất bại!");
        }
        return result;
    }

    public static boolean deleteSubject(String subjectId) {
        if (subjectId == null || subjectId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã môn học không được rỗng!");
        }
        Subject existingSubject = SubjectDao.getSubjectById(Integer.parseInt(subjectId));
        if (existingSubject == null) {
            throw new IllegalArgumentException("Môn học không tồn tại: " + subjectId);
        }
        boolean result = SubjectDao.deleteSubject(Integer.parseInt(subjectId));
        if (result) {
            System.out.println("✓ Xóa môn học thành công!");
        } else {
            System.out.println("✗ Xóa môn học thất bại!");
        }
        return result;
    }

    public static Subject getSubjectById(String subjectId) {
        if (subjectId == null || subjectId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã môn học không được rỗng!");
        }
        return SubjectDao.getSubjectById(Integer.parseInt(subjectId));
    }

    public static List<Subject> getAllSubjects() {
        return SubjectDao.getAllSubjects();
    }

    public static List<Subject> getSubjectsByTeacher(String teacherName) {
        if (teacherName == null || teacherName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên giảng viên không được rỗng!");
        }
        return SubjectDao.getSubjectsByTeacher(teacherName);
    }

    public static List<Subject> searchSubjectByName(String searchTerm) {
        return SubjectDao.searchSubjectByName(searchTerm);
    }

    public static boolean isSubjectExists(String subjectId) {
        return SubjectDao.isSubjectIdExists(Integer.parseInt(subjectId));
    }

    private static void validateSubjectInput(String subjectId, String subjectName, int credit) {
        if (subjectId == null || subjectId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã môn học không được rỗng!");
        }
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên môn học không được rỗng!");
        }
        if (subjectId.length() < 3 || subjectId.length() > 10) {
            throw new IllegalArgumentException("Mã môn học phải từ 3 đến 10 ký tự!");
        }
        if (subjectName.length() < 3 || subjectName.length() > 100) {
            throw new IllegalArgumentException("Tên môn học phải từ 3 đến 100 ký tự!");
        }
        if (credit < 1 || credit > 10) {
            throw new IllegalArgumentException("Số tín chỉ phải từ 1 đến 10!");
        }
    }

    public static String generateSubjectId() {
        List<Subject> allSubjects = SubjectDao.getAllSubjects();
        int maxId = 0;
        for (Subject subject : allSubjects) {
            String id = subject.getSubjectId();
            if (id.startsWith("SUB")) {
                try {
                    int num = Integer.parseInt(id.substring(3));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.format("SUB%05d", maxId + 1);
    }


    /**
     * Lấy đối tượng Subject hoàn chỉnh (bao gồm cả tên giảng viên và 5 đầu điểm)
     * để truyền vào SubjectPopup.
     * @param subjectId Mã môn học.
     * @param studentId Mã sinh viên.
     * @return Đối tượng Subject hoặc null nếu không tìm thấy.
     */
    public static Subject getSubjectDetails(String subjectId, String studentId) {
        if (subjectId == null || subjectId.trim().isEmpty() || studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã môn học và Mã sinh viên không được rỗng!");
        }

        // Lấy Tên + Tín chỉ, teacherName sẽ là rỗng
        Subject subject = SubjectDao.getSubjectById(Integer.parseInt(subjectId));
        if (subject == null) {
            return null;
        }

        // Lấy tên teacher
        String teacherName = SubjectDao.getTeacherNameBySubjectId(subjectId);
        subject.setTeacherName(teacherName);

        // Lấy 4 điểm thành phần CC, CK, GK, TH từ Database
        List<HashMap<String, Object>> gradeResults = SubjectDao.getGradeSubject(subjectId, studentId);

        // Lắp ráp 5 điểm
        double[] grades = calculateAndSetGrades(gradeResults);

        // Cập nhật điểm vào đối tượng Subject
        for (int i = 0; i < grades.length; i++) {
            subject.setGrade(i, grades[i]);
        }

        return subject;
    }


    // Tính toán + Sắp xếp điểm
    private static double[] calculateAndSetGrades(List<HashMap<String, Object>> gradeResults) {
        // Thứ tự điểm trong SubjectPopup: 0: CC, 1: GK, 2: TH, 3: CK, 4: Tổng kết
        double[] rawGrades = new double[4];

        if (gradeResults == null || gradeResults.isEmpty()) {
            return new double[5]; // Trả về 0.0 nếu không có điểm
        }

        // Ánh xạ điểm từ DB vào mảng theo thứ tự của SubjectPopup
        for (HashMap<String, Object> row : gradeResults) {
            String type = (String) row.get("grade_type");
            double score = Double.parseDouble(row.get("score").toString());

            switch (type) {
                case "CC":
                    rawGrades[0] = score;
                    break;
                case "GK":
                    rawGrades[1] = score;
                    break;
                case "TH":
                    rawGrades[2] = score;
                    break;
                case "CK":
                    rawGrades[3] = score;
                    break;
            }
        }

        // Tính điểm tổng kết
        double finalGrade = (rawGrades[0] * 0.10) +
                (rawGrades[1] * 0.20) +
                (rawGrades[2] * 0.20) +
                (rawGrades[3] * 0.50);

        // Tạo mảng 5 phần tử để trả về cho SubjectPopup
        return new double[]{
                rawGrades[0],   // 0 chuyên cần
                rawGrades[1],   // 1 giữa kỳ
                rawGrades[2],   // 2 thực hành
                rawGrades[3],   // 3 cuối kỳ
                finalGrade      // 4 tổng kết
        };
    }
}
