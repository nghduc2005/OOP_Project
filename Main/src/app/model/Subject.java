package app.model;

public class Subject {
    private String subjectId;
    private String subjectName;
    private int credit;
    private String teacherName;
    private double[] grades;

    public Subject(String subjectId, String subjectName, int credit) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
        this.teacherName = "";
        this.grades = new double[5];
    }

    public Subject(String subjectId, String subjectName, int credit, String teacherName, double[] grades) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
        this.teacherName = teacherName != null ? teacherName : "";
        this.grades = grades != null ? grades.clone() : new double[5];
    }

    public String getSubjectId() { return subjectId; }
    public String getSubjectName() { return subjectName; }
    public int getCredit() { return credit; }
    public String getTeacherName() { return teacherName; }
    public double getGrade(int index) {
        return (index >= 0 && index < grades.length) ? grades[index] : 0.0;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName != null ? teacherName : "";
    }
    public void setGrade(int index, double grade) {
        if (index >= 0 && index < grades.length) {
            grades[index] = grade;
        }
    }
}