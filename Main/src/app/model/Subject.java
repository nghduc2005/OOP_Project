package app.model;

public class Subject {
    private Integer subjectId;
    private String subjectName;
    private Integer credit;
    private String testType;
    private String teacherName;
    private double[] grades = new double[5]; // 0: CC, 1: GK, 2: TH, 3: CK, 4: Tổng kết

    public Subject() {}
    
    public Subject(Integer subjectId, String subjectName, Integer credit) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
    }

    public Subject(Integer subjectId, String subjectName, Integer credit, String testType) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credit = credit;
        this.testType = testType;
    }

    public Integer getSubjectId() { 
        return subjectId; 
    }
    
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
    
    public String getSubjectName() { 
        return subjectName; 
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public Integer getCredit() { 
        return credit; 
    }
    
    public void setCredit(Integer credit) {
        this.credit = credit;
    }
    
    public String getTestType() {
        return testType;
    }
    
    public void setTestType(String testType) {
        this.testType = testType;
    }
    
    public String getTeacherName() {
        return teacherName;
    }
    
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    
    public double getGrade(int index) {
        if (index >= 0 && index < grades.length) {
            return grades[index];
        }
        return 0.0;
    }
    
    public void setGrade(int index, double grade) {
        if (index >= 0 && index < grades.length) {
            grades[index] = grade;
        }
    }
    
    public double[] getGrades() {
        return grades;
    }
    
    public void setGrades(double[] grades) {
        if (grades != null && grades.length == this.grades.length) {
            System.arraycopy(grades, 0, this.grades, 0, grades.length);
        }
    }
}
