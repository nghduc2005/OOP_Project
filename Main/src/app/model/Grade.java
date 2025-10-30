package app.model;

public class Grade {
    private Integer gradeId;
    private String studentId;
    private String subjectId;
    private String classId;
    private Double attendanceScore; // Điểm chuyên cần (CC)
    private Double assignmentScore; // Điểm bài tập (BT)
    private Double midtermScore;    // Điểm giữa kỳ (GK)
    private Double finalScore;      // Điểm cuối kỳ (CK)
    private Double totalScore;      // Điểm tổng kết
    private String letterGrade;     // Điểm chữ (A, B+, B, C+, C, D+, D, F)
    private String semester;        // Học kỳ
    private String academicYear;    // Năm học

    public Grade() {
        this.attendanceScore = 0.0;
        this.assignmentScore = 0.0;
        this.midtermScore = 0.0;
        this.finalScore = 0.0;
        this.totalScore = 0.0;
        this.letterGrade = "";
    }

    public Grade(Integer gradeId, String studentId, String subjectId, String classId,
                 Double attendanceScore, Double assignmentScore, Double midtermScore,
                 Double finalScore, String semester, String academicYear) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.classId = classId;
        this.attendanceScore = attendanceScore != null ? attendanceScore : 0.0;
        this.assignmentScore = assignmentScore != null ? assignmentScore : 0.0;
        this.midtermScore = midtermScore != null ? midtermScore : 0.0;
        this.finalScore = finalScore != null ? finalScore : 0.0;
        this.semester = semester;
        this.academicYear = academicYear;
        calculateTotalScore();
    }

    /**
     * Tính điểm tổng kết theo công thức:
     * Điểm tổng = CC*10% + BT*20% + GK*20% + CK*50%
     */
    public void calculateTotalScore() {
        this.totalScore = (this.attendanceScore * 0.1) +
                         (this.assignmentScore * 0.2) +
                         (this.midtermScore * 0.2) +
                         (this.finalScore * 0.5);
        this.letterGrade = convertToLetterGrade(this.totalScore);
    }

    /**
     * Chuyển điểm số sang điểm chữ
     */
    private String convertToLetterGrade(double score) {
        if (score >= 9.0) return "A+";
        if (score >= 8.5) return "A";
        if (score >= 8.0) return "B+";
        if (score >= 7.0) return "B";
        if (score >= 6.5) return "C+";
        if (score >= 5.5) return "C";
        if (score >= 5.0) return "D+";
        if (score >= 4.0) return "D";
        return "F";
    }

    // Getters
    public Integer getGradeId() { return gradeId; }
    public String getStudentId() { return studentId; }
    public String getSubjectId() { return subjectId; }
    public String getClassId() { return classId; }
    public Double getAttendanceScore() { return attendanceScore; }
    public Double getAssignmentScore() { return assignmentScore; }
    public Double getMidtermScore() { return midtermScore; }
    public Double getFinalScore() { return finalScore; }
    public Double getTotalScore() { return totalScore; }
    public String getLetterGrade() { return letterGrade; }
    public String getSemester() { return semester; }
    public String getAcademicYear() { return academicYear; }

    // Setters
    public void setGradeId(Integer gradeId) { this.gradeId = gradeId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setClassId(String classId) { this.classId = classId; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public void setAttendanceScore(Double attendanceScore) {
        this.attendanceScore = attendanceScore;
        calculateTotalScore();
    }

    public void setAssignmentScore(Double assignmentScore) {
        this.assignmentScore = assignmentScore;
        calculateTotalScore();
    }

    public void setMidtermScore(Double midtermScore) {
        this.midtermScore = midtermScore;
        calculateTotalScore();
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
        calculateTotalScore();
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId='" + gradeId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", classId='" + classId + '\'' +
                ", attendanceScore=" + attendanceScore +
                ", assignmentScore=" + assignmentScore +
                ", midtermScore=" + midtermScore +
                ", finalScore=" + finalScore +
                ", totalScore=" + totalScore +
                ", letterGrade='" + letterGrade + '\'' +
                ", semester='" + semester + '\'' +
                ", academicYear='" + academicYear + '\'' +
                '}';
    }
}
