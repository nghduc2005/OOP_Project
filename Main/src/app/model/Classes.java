package app.model;

public class Classes {
    private Integer classId;
    private Integer teacherId;
    private Integer totalStudent;
    private String subjectName;
    private Integer maxNumberStudent;
    private Integer subjectId;
    private String scheduleId;
    private Integer credit;
    public Classes() {}
    
    public Classes(String subjectName, Integer maxNumberStudent, Integer subjectId, Integer credit) {
        this.subjectName = subjectName;
        this.maxNumberStudent = maxNumberStudent;
        this.subjectId = subjectId;
        this.credit = credit;
    }

    public Classes(Integer classId, Integer teacherId, Integer totalStudent, 
                   String subjectName, Integer maxNumberStudent, Integer subjectId, String scheduleId) {
        this.classId = classId;
        this.teacherId = teacherId;
        this.totalStudent = totalStudent;
        this.subjectName = subjectName;
        this.maxNumberStudent = maxNumberStudent;
        this.subjectId = subjectId;
        this.scheduleId = scheduleId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCredit() {
        return credit;
    }
    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(Integer totalStudent) {
        this.totalStudent = totalStudent;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getMaxNumberStudent() {
        return maxNumberStudent;
    }

    public void setMaxNumberStudent(Integer maxNumberStudent) {
        this.maxNumberStudent = maxNumberStudent;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    @Override
    public String toString() {
        return subjectName + "-" + classId;
    }
}
