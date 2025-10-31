package app.service;

import app.dao.GroupDao;
import app.dao.SubjectDao;
import app.model.Group;
import app.model.Student;
import app.model.Subject;
import java.util.List;

public class GroupService {

    public static boolean createGroup(String groupId, String groupName, String subjectName,
                                      String teacherName, int maxStudents) {
        validateGroupInput(groupId, groupName, subjectName, teacherName, maxStudents);

        Subject subject = SubjectDao.getSubjectById(Integer.parseInt(subjectName));
        if (subject == null) {
            System.out.println("⚠ Cảnh báo: Môn học chưa tồn tại trong hệ thống: " + subjectName);
        }

        Group group = new Group(groupId, groupName, subjectName, teacherName, maxStudents);
        boolean result = GroupDao.createGroup(group);

        if (result) {
            System.out.println("✓ Tạo lớp học thành công!");
            System.out.println("  - Mã lớp: " + groupId);
            System.out.println("  - Tên lớp: " + groupName);
            System.out.println("  - Môn học: " + subjectName);
            System.out.println("  - Giảng viên: " + teacherName);
            System.out.println("  - Sĩ số tối đa: " + maxStudents);
        } else {
            System.out.println("✗ Tạo lớp học thất bại!");
        }

        return result;
    }

    public static boolean createGroup(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Group không được null!");
        }
        return createGroup(
                group.getGroupId(),
                group.getGroupName(),
                group.getSubjectName(),
                group.getTeacherName(),
                group.getMaxStudents()
        );
    }

    public static boolean updateGroup(String groupId, String groupName, String subjectName,
                                      String teacherName, int maxStudents) {
        validateGroupInput(groupId, groupName, subjectName, teacherName, maxStudents);

        Group existingGroup = GroupDao.getGroupById(groupId);
        if (existingGroup == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại: " + groupId);
        }

        if (maxStudents < existingGroup.getNumberOfStudent()) {
            throw new IllegalArgumentException(
                    "Số sinh viên tối đa mới (" + maxStudents + ") không được nhỏ hơn " +
                            "số sinh viên hiện tại (" + existingGroup.getNumberOfStudent() + ")!"
            );
        }

        Group group = new Group(groupId, groupName, subjectName, teacherName, maxStudents);
        boolean result = GroupDao.updateGroup(group);

        if (result) {
            System.out.println("✓ Cập nhật lớp học thành công!");
        } else {
            System.out.println("✗ Cập nhật lớp học thất bại!");
        }

        return result;
    }

    public static boolean deleteGroup(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp không được rỗng!");
        }

        Group existingGroup = GroupDao.getGroupById(groupId);
        if (existingGroup == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại: " + groupId);
        }

        if (existingGroup.getNumberOfStudent() > 0) {
            System.out.println("⚠ Cảnh báo: Lớp học có " + existingGroup.getNumberOfStudent() + " sinh viên!");
        }

        boolean result = GroupDao.deleteGroup(groupId);

        if (result) {
            System.out.println("✓ Xóa lớp học thành công!");
        } else {
            System.out.println("✗ Xóa lớp học thất bại!");
        }

        return result;
    }

    public static boolean addStudentToGroup(String groupId, String studentId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp không được rỗng!");
        }
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sinh viên không được rỗng!");
        }

        Group group = GroupDao.getGroupById(groupId);
        if (group == null) {
            throw new IllegalArgumentException("Lớp học không tồn tại: " + groupId);
        }

        if (group.isFull()) {
            throw new IllegalArgumentException("Lớp học đã đầy! (" +
                    group.getNumberOfStudent() + "/" + group.getMaxStudents() + ")");
        }

        if (GroupDao.isStudentInGroup(groupId, studentId)) {
            throw new IllegalArgumentException("Sinh viên đã có trong lớp!");
        }

        boolean result = GroupDao.addStudentToGroup(groupId, studentId);

        if (result) {
            System.out.println("✓ Thêm sinh viên vào lớp thành công!");
        } else {
            System.out.println("✗ Thêm sinh viên vào lớp thất bại!");
        }

        return result;
    }

    public static boolean removeStudentFromGroup(String groupId, String studentId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp không được rỗng!");
        }
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sinh viên không được rỗng!");
        }

        if (!GroupDao.isStudentInGroup(groupId, studentId)) {
            throw new IllegalArgumentException("Sinh viên không có trong lớp!");
        }

        boolean result = GroupDao.removeStudentFromGroup(groupId, studentId);

        if (result) {
            System.out.println("✓ Xóa sinh viên khỏi lớp thành công!");
        } else {
            System.out.println("✗ Xóa sinh viên khỏi lớp thất bại!");
        }

        return result;
    }

    public static Group getGroupById(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp không được rỗng!");
        }
        return GroupDao.getGroupById(groupId);
    }

    public static List<Group> getAllGroups() {
        return GroupDao.getAllGroups();
    }

    public static List<Group> getGroupsByTeacher(String teacherName) {
        if (teacherName == null || teacherName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên giảng viên không được rỗng!");
        }
        return GroupDao.getGroupsByTeacher(teacherName);
    }

    public static List<Group> getGroupsBySubject(String subjectName) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên môn học không được rỗng!");
        }
        return GroupDao.getGroupsBySubject(subjectName);
    }

    public static List<Student> getStudentsInGroup(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp không được rỗng!");
        }
        return GroupDao.getStudentsInGroup(groupId);
    }

    public static List<Group> searchGroupByName(String searchTerm) {
        return GroupDao.searchGroupByName(searchTerm);
    }

    public static List<Group> getAvailableGroups() {
        return GroupDao.getAvailableGroups();
    }

    public static boolean isGroupExists(String groupId) {
        return GroupDao.isGroupIdExists(groupId);
    }

    private static void validateGroupInput(String groupId, String groupName, String subjectName,
                                           String teacherName, int maxStudents) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã lớp không được rỗng!");
        }
        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên lớp không được rỗng!");
        }
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên môn học không được rỗng!");
        }
        if (teacherName == null || teacherName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên giảng viên không được rỗng!");
        }
        if (groupId.length() < 3 || groupId.length() > 20) {
            throw new IllegalArgumentException("Mã lớp phải từ 3 đến 20 ký tự!");
        }
        if (groupName.length() < 3 || groupName.length() > 100) {
            throw new IllegalArgumentException("Tên lớp phải từ 3 đến 100 ký tự!");
        }
        if (maxStudents < 10 || maxStudents > 100) {
            throw new IllegalArgumentException("Số sinh viên tối đa phải từ 10 đến 100!");
        }
    }
}
