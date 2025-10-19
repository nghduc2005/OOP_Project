package app.service;

import app.dao.SubjectDao;
import app.model.Subject;
import java.util.List;

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
        Subject existingSubject = SubjectDao.getSubjectById(subjectId);
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
        Subject existingSubject = SubjectDao.getSubjectById(subjectId);
        if (existingSubject == null) {
            throw new IllegalArgumentException("Môn học không tồn tại: " + subjectId);
        }
        boolean result = SubjectDao.deleteSubject(subjectId);
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
        return SubjectDao.getSubjectById(subjectId);
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
        return SubjectDao.isSubjectIdExists(subjectId);
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
}
