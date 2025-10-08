package app.model;

public class Subject {
    private String subjectId;
    private String subjectName;
    private int credit;
    public Subject(String subjectId, String subjectName, int credit) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
    }
}
