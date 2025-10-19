package app.dao;

import app.model.Group;
import app.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupDao {

    public static boolean createGroup(Group group) {
        if (group == null) {
            System.out.println("Group không được null!");
            return false;
        }
        if (!validateGroup(group)) {
            return false;
        }
        if (isGroupIdExists(group.getGroupId())) {
            System.out.println("Mã lớp học đã tồn tại: " + group.getGroupId());
            return false;
        }
        String query = String.format(
                "INSERT INTO `group` (group_id, group_name, subject_name, teacher_name, max_students, number_of_student) " +
                        "VALUES ('%s', '%s', '%s', '%s', %d, %d)",
                escapeString(group.getGroupId()),
                escapeString(group.getGroupName()),
                escapeString(group.getSubjectName()),
                escapeString(group.getTeacherName()),
                group.getMaxStudents(),
                group.getNumberOfStudent()
        );
        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Tạo lớp học thành công: " + group.getGroupName() + " (" + group.getGroupId() + ")");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi tạo lớp học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Group getGroupById(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            System.out.println("Mã lớp không được rỗng!");
            return null;
        }
        String query = String.format(
                "SELECT * FROM `group` WHERE group_id = '%s'",
                escapeString(groupId)
        );
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                HashMap<String, Object> row = results.get(0);
                return mapToGroup(row);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy thông tin lớp học: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<Group> getAllGroups() {
        String query = "SELECT * FROM `group` ORDER BY group_id";
        List<Group> groups = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Group group = mapToGroup(row);
                    if (group != null) {
                        groups.add(group);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách lớp học: " + e.getMessage());
            e.printStackTrace();
        }
        return groups;
    }

    public static List<Group> getGroupsByTeacher(String teacherName) {
        if (teacherName == null || teacherName.trim().isEmpty()) {
            System.out.println("Tên giảng viên không được rỗng!");
            return new ArrayList<>();
        }
        String query = String.format(
                "SELECT * FROM `group` WHERE teacher_name = '%s' ORDER BY group_id",
                escapeString(teacherName)
        );
        List<Group> groups = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Group group = mapToGroup(row);
                    if (group != null) {
                        groups.add(group);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách lớp học của giảng viên: " + e.getMessage());
            e.printStackTrace();
        }
        return groups;
    }

    public static List<Group> getGroupsBySubject(String subjectName) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            System.out.println("Tên môn học không được rỗng!");
            return new ArrayList<>();
        }
        String query = String.format(
                "SELECT * FROM `group` WHERE subject_name = '%s' ORDER BY group_id",
                escapeString(subjectName)
        );
        List<Group> groups = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Group group = mapToGroup(row);
                    if (group != null) {
                        groups.add(group);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách lớp học của môn học: " + e.getMessage());
            e.printStackTrace();
        }
        return groups;
    }

    public static boolean updateGroup(Group group) {
        if (group == null) {
            System.out.println("Group không được null!");
            return false;
        }
        if (!validateGroup(group)) {
            return false;
        }
        if (!isGroupIdExists(group.getGroupId())) {
            System.out.println("Lớp học không tồn tại: " + group.getGroupId());
            return false;
        }
        String query = String.format(
                "UPDATE `group` SET group_name = '%s', subject_name = '%s', " +
                        "teacher_name = '%s', max_students = %d, number_of_student = %d " +
                        "WHERE group_id = '%s'",
                escapeString(group.getGroupName()),
                escapeString(group.getSubjectName()),
                escapeString(group.getTeacherName()),
                group.getMaxStudents(),
                group.getNumberOfStudent(),
                escapeString(group.getGroupId())
        );
        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Cập nhật lớp học thành công: " + group.getGroupName());
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật lớp học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteGroup(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            System.out.println("Mã lớp không được rỗng!");
            return false;
        }
        if (!isGroupIdExists(groupId)) {
            System.out.println("Lớp học không tồn tại: " + groupId);
            return false;
        }
        String deleteStudentGroupQuery = String.format(
                "DELETE FROM student_class WHERE group_id = '%s'",
                escapeString(groupId)
        );
        DatabaseConnection.insertTable(deleteStudentGroupQuery);
        String query = String.format(
                "DELETE FROM `group` WHERE group_id = '%s'",
                escapeString(groupId)
        );
        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Xóa lớp học thành công: " + groupId);
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa lớp học: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addStudentToGroup(String groupId, String studentId) {
        if (groupId == null || groupId.trim().isEmpty() ||
                studentId == null || studentId.trim().isEmpty()) {
            System.out.println("Mã lớp và mã sinh viên không được rỗng!");
            return false;
        }
        Group group = getGroupById(groupId);
        if (group == null) {
            System.out.println("Lớp học không tồn tại: " + groupId);
            return false;
        }
        if (group.isFull()) {
            System.out.println("Lớp học đã đầy: " + groupId);
            return false;
        }
        if (isStudentInGroup(groupId, studentId)) {
            System.out.println("Sinh viên đã có trong lớp: " + studentId);
            return false;
        }
        String query = String.format(
                "INSERT INTO student_class (student_id, group_id) VALUES ('%s', '%s')",
                escapeString(studentId),
                escapeString(groupId)
        );
        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                int newCount = group.getNumberOfStudent() + 1;
                String updateQuery = String.format(
                        "UPDATE `group` SET number_of_student = %d WHERE group_id = '%s'",
                        newCount,
                        escapeString(groupId)
                );
                DatabaseConnection.insertTable(updateQuery);
                System.out.println("Thêm sinh viên vào lớp thành công!");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm sinh viên vào lớp: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeStudentFromGroup(String groupId, String studentId) {
        if (groupId == null || groupId.trim().isEmpty() ||
                studentId == null || studentId.trim().isEmpty()) {
            System.out.println("Mã lớp và mã sinh viên không được rỗng!");
            return false;
        }
        if (!isStudentInGroup(groupId, studentId)) {
            System.out.println("Sinh viên không có trong lớp!");
            return false;
        }
        String query = String.format(
                "DELETE FROM student_class WHERE student_id = '%s' AND group_id = '%s'",
                escapeString(studentId),
                escapeString(groupId)
        );
        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                Group group = getGroupById(groupId);
                if (group != null) {
                    int newCount = Math.max(0, group.getNumberOfStudent() - 1);
                    String updateQuery = String.format(
                            "UPDATE `group` SET number_of_student = %d WHERE group_id = '%s'",
                            newCount,
                            escapeString(groupId)
                    );
                    DatabaseConnection.insertTable(updateQuery);
                }
                System.out.println("Xóa sinh viên khỏi lớp thành công!");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi xóa sinh viên khỏi lớp: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<Student> getStudentsInGroup(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            System.out.println("Mã lớp không được rỗng!");
            return new ArrayList<>();
        }
        String query = String.format(
                "SELECT s.* FROM student s " +
                        "INNER JOIN student_class sg ON s.student_id = sg.student_id " +
                        "WHERE sg.group_id = '%s' ORDER BY s.student_id",
                escapeString(groupId)
        );
        List<Student> students = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Student student = StudentDao.mapToStudent(row);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách sinh viên trong lớp: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }

    public static boolean isStudentInGroup(String groupId, String studentId) {
        if (groupId == null || groupId.trim().isEmpty() ||
                studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        String query = String.format(
                "SELECT COUNT(*) as count FROM student_class " +
                        "WHERE student_id = '%s' AND group_id = '%s'",
                escapeString(studentId),
                escapeString(groupId)
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
            System.out.println("Lỗi khi kiểm tra sinh viên trong lớp: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isGroupIdExists(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            return false;
        }
        String query = String.format(
                "SELECT COUNT(*) as count FROM `group` WHERE group_id = '%s'",
                escapeString(groupId)
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
            System.out.println("Lỗi khi kiểm tra mã lớp: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static List<Group> searchGroupByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllGroups();
        }
        String query = String.format(
                "SELECT * FROM `group` WHERE LOWER(group_name) LIKE LOWER('%%%s%%') " +
                        "OR LOWER(subject_name) LIKE LOWER('%%%s%%') ORDER BY group_id",
                escapeString(searchTerm),
                escapeString(searchTerm)
        );
        List<Group> groups = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Group group = mapToGroup(row);
                    if (group != null) {
                        groups.add(group);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm lớp học: " + e.getMessage());
            e.printStackTrace();
        }
        return groups;
    }

    public static List<Group> getAvailableGroups() {
        String query = "SELECT * FROM `group` WHERE number_of_student < max_students ORDER BY group_id";
        List<Group> groups = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Group group = mapToGroup(row);
                    if (group != null) {
                        groups.add(group);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách lớp còn chỗ: " + e.getMessage());
            e.printStackTrace();
        }
        return groups;
    }

    private static boolean validateGroup(Group group) {
        if (group.getGroupId() == null || group.getGroupId().trim().isEmpty()) {
            System.out.println("Mã lớp không được rỗng!");
            return false;
        }
        if (group.getGroupName() == null || group.getGroupName().trim().isEmpty()) {
            System.out.println("Tên lớp không được rỗng!");
            return false;
        }
        if (group.getSubjectName() == null || group.getSubjectName().trim().isEmpty()) {
            System.out.println("Tên môn học không được rỗng!");
            return false;
        }
        if (group.getTeacherName() == null || group.getTeacherName().trim().isEmpty()) {
            System.out.println("Tên giảng viên không được rỗng!");
            return false;
        }
        if (group.getMaxStudents() < 10 || group.getMaxStudents() > 100) {
            System.out.println("Số sinh viên tối đa phải từ 10 đến 100!");
            return false;
        }
        if (group.getNumberOfStudent() < 0) {
            System.out.println("Số sinh viên hiện tại không được âm!");
            return false;
        }
        if (group.getNumberOfStudent() > group.getMaxStudents()) {
            System.out.println("Số sinh viên hiện tại không được lớn hơn số sinh viên tối đa!");
            return false;
        }
        return true;
    }

    private static Group mapToGroup(HashMap<String, Object> row) {
        try {
            String groupId = (String) row.get("group_id");
            String groupName = (String) row.get("group_name");
            String subjectName = (String) row.get("subject_name");
            String teacherName = (String) row.get("teacher_name");
            Object maxStudentsObj = row.get("max_students");
            int maxStudents = maxStudentsObj instanceof Integer ? (Integer) maxStudentsObj :
                    Integer.parseInt(maxStudentsObj.toString());
            Object numberOfStudentObj = row.get("number_of_student");
            int numberOfStudent = numberOfStudentObj instanceof Integer ? (Integer) numberOfStudentObj :
                    Integer.parseInt(numberOfStudentObj.toString());
            Group group = new Group(groupId, groupName, subjectName, teacherName, maxStudents);
            return group;
        } catch (Exception e) {
            System.out.println("Lỗi khi chuyển đổi dữ liệu lớp học: " + e.getMessage());
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
