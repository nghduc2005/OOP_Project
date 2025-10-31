package app.service;

import java.util.List;

import app.dao.GradeDao;
import app.model.Grade;

public class GradeService {

    /**
     * Thêm điểm mới
     */
    public static boolean createGrade(Grade grade) {
        if (grade == null) {
            System.out.println("Đối tượng điểm không được null!");
            return false;
        }

        // Validate điểm
        if (!validateGrade(grade)) {
            return false;
        }

        // Tính toán điểm tổng
        grade.calculateTotalScore();

        return GradeDao.createGrade(grade);
    }

    /**
     * Thêm điểm với thông tin đầy đủ
     */
    public static boolean createGrade(String studentId, String subjectId, String classId,
                                      Double attendanceScore, Double assignmentScore,
                                      Double midtermScore, Double finalScore,
                                      String semester, String academicYear) {
        Grade grade = new Grade(null, studentId, subjectId, classId,
                attendanceScore, assignmentScore, midtermScore, finalScore,
                semester, academicYear);
        return createGrade(grade);
    }

    /**
     * Cập nhật điểm
     */
    public static boolean updateGrade(Grade grade) {
        if (grade == null) {
            System.out.println("Đối tượng điểm không được null!");
            return false;
        }

        if (!validateGrade(grade)) {
            return false;
        }

        // Tính toán lại điểm tổng
        grade.calculateTotalScore();

        return GradeDao.updateGrade(grade);
    }

    /**
     * Cập nhật điểm chuyên cần
     */
    public static boolean updateAttendanceScore(String gradeId, Double score) {
        Grade grade = GradeDao.getGradeById(gradeId);
        if (grade == null) {
            System.out.println("Không tìm thấy điểm với ID: " + gradeId);
            return false;
        }
        
        if (!isValidScore(score)) {
            System.out.println("Điểm chuyên cần không hợp lệ! (0-10)");
            return false;
        }

        grade.setAttendanceScore(score);
        return GradeDao.updateGrade(grade);
    }

    /**
     * Cập nhật điểm bài tập
     */
    public static boolean updateAssignmentScore(String gradeId, Double score) {
        Grade grade = GradeDao.getGradeById(gradeId);
        if (grade == null) {
            System.out.println("Không tìm thấy điểm với ID: " + gradeId);
            return false;
        }

        if (!isValidScore(score)) {
            System.out.println("Điểm bài tập không hợp lệ! (0-10)");
            return false;
        }

        grade.setAssignmentScore(score);
        return GradeDao.updateGrade(grade);
    }

    /**
     * Cập nhật điểm giữa kỳ
     */
    public static boolean updateMidtermScore(String gradeId, Double score) {
        Grade grade = GradeDao.getGradeById(gradeId);
        if (grade == null) {
            System.out.println("Không tìm thấy điểm với ID: " + gradeId);
            return false;
        }

        if (!isValidScore(score)) {
            System.out.println("Điểm giữa kỳ không hợp lệ! (0-10)");
            return false;
        }

        grade.setMidtermScore(score);
        return GradeDao.updateGrade(grade);
    }

    /**
     * Cập nhật điểm cuối kỳ
     */
    public static boolean updateFinalScore(String gradeId, Double score) {
        Grade grade = GradeDao.getGradeById(gradeId);
        if (grade == null) {
            System.out.println("Không tìm thấy điểm với ID: " + gradeId);
            return false;
        }

        if (!isValidScore(score)) {
            System.out.println("Điểm cuối kỳ không hợp lệ! (0-10)");
            return false;
        }

        grade.setFinalScore(score);
        return GradeDao.updateGrade(grade);
    }

    /**
     * Xóa điểm
     */
    public static boolean deleteGrade(String gradeId) {
        try {
            Integer gradeIdInt = Integer.parseInt(gradeId);
            return GradeDao.deleteGrade(gradeIdInt);
        } catch (NumberFormatException e) {
            System.out.println("ID điểm không hợp lệ: " + gradeId);
            return false;
        }
    }

    /**
     * Lấy điểm theo ID
     */
    public static Grade getGradeById(String gradeId) {
        return GradeDao.getGradeById(gradeId);
    }

    /**
     * Lấy tất cả điểm của một sinh viên
     */
    public static List<Grade> getGradesByStudentId(String studentId) {
        return GradeDao.getGradesByStudentId(studentId);
    }

    /**
     * Lấy tất cả điểm trong hệ thống
     */
    public static List<Grade> getAllGrades() {
        return GradeDao.getAllGrades();
    }

    /**
     * Lấy điểm của một lớp
     */
    public static List<Grade> getGradesByClassId(String classId) {
        return GradeDao.getGradesByClassId(classId);
    }

    /**
     * Lấy điểm theo môn học
     */
    public static List<Grade> getGradesBySubjectId(String subjectId) {
        return GradeDao.getGradesBySubjectId(subjectId);
    }

    /**
     * Lấy điểm của sinh viên trong một lớp cụ thể
     */
    public static Grade getGradeByStudentAndClass(String studentId, String classId, String subjectId) {
        return GradeDao.getGradeByStudentAndClass(studentId, classId, subjectId);
    }

    /**
     * Tìm kiếm điểm
     */
    public static List<Grade> searchGrades(String searchTerm) {
        return GradeDao.searchGrades(searchTerm);
    }

    /**
     * Tính điểm trung bình của sinh viên
     */
    public static Double calculateAverageGrade(String studentId) {
        List<Grade> grades = GradeDao.getGradesByStudentId(studentId);
        if (grades.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Grade grade : grades) {
            sum += grade.getTotalScore();
        }
        return sum / grades.size();
    }

    /**
     * Tính điểm trung bình của lớp
     */
    public static Double calculateClassAverage(String classId) {
        List<Grade> grades = GradeDao.getGradesByClassId(classId);
        if (grades.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Grade grade : grades) {
            sum += grade.getTotalScore();
        }
        return sum / grades.size();
    }

    /**
     * Validate điểm
     */
    private static boolean validateGrade(Grade grade) {
        if (grade.getStudentId() == null || grade.getStudentId().trim().isEmpty()) {
            System.out.println("Mã sinh viên không được rỗng!");
            return false;
        }

        if (grade.getSubjectId() == null || grade.getSubjectId().trim().isEmpty()) {
            System.out.println("Mã môn học không được rỗng!");
            return false;
        }

        if (!isValidScore(grade.getAttendanceScore())) {
            System.out.println("Điểm chuyên cần không hợp lệ! (0-10)");
            return false;
        }

        if (!isValidScore(grade.getAssignmentScore())) {
            System.out.println("Điểm bài tập không hợp lệ! (0-10)");
            return false;
        }

        if (!isValidScore(grade.getMidtermScore())) {
            System.out.println("Điểm giữa kỳ không hợp lệ! (0-10)");
            return false;
        }

        if (!isValidScore(grade.getFinalScore())) {
            System.out.println("Điểm cuối kỳ không hợp lệ! (0-10)");
            return false;
        }

        return true;
    }

    /**
     * Kiểm tra điểm có hợp lệ không (0-10)
     */
    private static boolean isValidScore(Double score) {
        return score != null && score >= 0.0 && score <= 10.0;
    }

    /**
     * Lấy xếp hạng học lực
     */
    public static String getAcademicRank(Double averageScore) {
        if (averageScore >= 9.0) return "Xuất sắc";
        if (averageScore >= 8.0) return "Giỏi";
        if (averageScore >= 6.5) return "Khá";
        if (averageScore >= 5.0) return "Trung bình";
        return "Yếu";
    }
}
