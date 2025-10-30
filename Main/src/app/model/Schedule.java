package app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Schedule {
    private Integer scheduleId;
    private LocalDate studyDate;
    private String studyShift;
    private Integer totalSessions;
    private String learningMethod;
    private String classroom;
    private String note;
    private Integer subjectId;
    private Integer classId;
    private LocalDateTime startTime;

    public Schedule() {
        this.learningMethod = "Offline";
        this.note = "";
    }

    public Schedule(Integer scheduleId, LocalDate studyDate, String studyShift,
                   Integer totalSessions, String learningMethod, String classroom,
                   String note, Integer subjectId, Integer classId, LocalDateTime startTime) {
        this.scheduleId = scheduleId;
        this.studyDate = studyDate;
        this.studyShift = studyShift;
        this.totalSessions = totalSessions;
        this.learningMethod = learningMethod != null ? learningMethod : "Offline";
        this.classroom = classroom;
        this.note = note != null ? note : "";
        this.subjectId = subjectId;
        this.classId = classId;
        this.startTime = startTime;
    }

    // Getters
    public Integer getScheduleId() { return scheduleId; }
    public LocalDate getStudyDate() { return studyDate; }
    public String getStudyShift() { return studyShift; }
    public Integer getTotalSessions() { return totalSessions; }
    public String getLearningMethod() { return learningMethod; }
    public String getClassroom() { return classroom; }
    public String getNote() { return note; }
    public Integer getSubjectId() { return subjectId; }
    public Integer getClassId() { return classId; }
    public LocalDateTime getStartTime() { return startTime; }

    // Setters
    public void setScheduleId(Integer scheduleId) { this.scheduleId = scheduleId; }
    public void setStudyDate(LocalDate studyDate) { this.studyDate = studyDate; }
    public void setStudyShift(String studyShift) { this.studyShift = studyShift; }
    public void setTotalSessions(Integer totalSessions) { this.totalSessions = totalSessions; }
    public void setLearningMethod(String learningMethod) { this.learningMethod = learningMethod; }
    public void setClassroom(String classroom) { this.classroom = classroom; }
    public void setNote(String note) { this.note = note; }
    public void setSubjectId(Integer subjectId) { this.subjectId = subjectId; }
    public void setClassId(Integer classId) { this.classId = classId; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", studyDate=" + studyDate +
                ", studyShift='" + studyShift + '\'' +
                ", totalSessions=" + totalSessions +
                ", learningMethod='" + learningMethod + '\'' +
                ", classroom='" + classroom + '\'' +
                ", subjectId=" + subjectId +
                ", classId=" + classId +
                ", startTime=" + startTime +
                '}';
    }
}
