package app.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupId;
    private String subjectName;
    private String teacherName;
    private String groupName;
    private int numberOfStudent;
    private int maxStudents;
    private List<Student> students;

    public Group() {
        this.students = new ArrayList<>();
        this.numberOfStudent = 0;
        this.maxStudents = 30;
    }


    public Group(String groupId, String groupName, String subjectName,
                 String teacherName, int maxStudents) {
        this();
        this.groupId = groupId;
        this.groupName = groupName;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.maxStudents = maxStudents;
    }


    public String getGroupId() { return groupId; }
    public String getSubjectName() { return subjectName; }
    public String getTeacherName() { return teacherName; }
    public String getGroupName() { return groupName; }
    public int getNumberOfStudent() { return numberOfStudent; }
    public int getMaxStudents() { return maxStudents; }
    public List<Student> getStudents() { return new ArrayList<>(students); }

    public void setGroupId(String groupId) { this.groupId = groupId; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public void setMaxStudents(int maxStudents) { this.maxStudents = maxStudents; }


    public boolean addStudent(Student student) {
        if (numberOfStudent < maxStudents && !students.contains(student)) {
            students.add(student);
            numberOfStudent = students.size();
            return true;
        }
        return false;
    }

    public boolean removeStudent(Student student) {
        boolean removed = students.remove(student);
        if (removed) {
            numberOfStudent = students.size();
        }
        return removed;
    }

    public boolean isFull() {
        return numberOfStudent >= maxStudents;
    }

    public int getAvailableSlots() {
        return maxStudents - numberOfStudent;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", numberOfStudent=" + numberOfStudent + "/" + maxStudents +
                '}';
    }
}
