package app.model;

public class StudentClass {
    private Integer studentId;
    private Integer classId;

    public StudentClass() {}

    public StudentClass(Integer studentId, Integer classId) {
        this.studentId = studentId;
        this.classId = classId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "studentId=" + studentId +
                ", classId=" + classId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StudentClass that = (StudentClass) obj;
        return studentId.equals(that.studentId) && classId.equals(that.classId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode() * 31 + classId.hashCode();
    }
}
