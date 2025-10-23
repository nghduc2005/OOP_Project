package app.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private String scheduleId;
    private String subjectName;
    private String teacherName;
    private String room;
    private String building;
    private LocalDate scheduleDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String repeatType; // "Không lặp", "Hàng tuần", "Hàng ngày", "Hàng tháng", "Hàng quý", "Hàng năm"
    private String format; // "Offline", "Online", "Tự học"
    private String note;
    private String groupId;

    public Schedule() {
        this.repeatType = "Không lặp";
        this.format = "Offline";
        this.note = "";
    }

    public Schedule(String scheduleId, String subjectName, String teacherName,
                   String room, String building, LocalDate scheduleDate,
                   LocalTime startTime, LocalTime endTime, String repeatType,
                   String format, String note, String groupId) {
        this.scheduleId = scheduleId;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.room = room;
        this.building = building;
        this.scheduleDate = scheduleDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatType = repeatType != null ? repeatType : "Không lặp";
        this.format = format != null ? format : "Offline";
        this.note = note != null ? note : "";
        this.groupId = groupId;
    }

    // Getters
    public String getScheduleId() { return scheduleId; }
    public String getSubjectName() { return subjectName; }
    public String getTeacherName() { return teacherName; }
    public String getRoom() { return room; }
    public String getBuilding() { return building; }
    public LocalDate getScheduleDate() { return scheduleDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getRepeatType() { return repeatType; }
    public String getFormat() { return format; }
    public String getNote() { return note; }
    public String getGroupId() { return groupId; }

    // Setters
    public void setScheduleId(String scheduleId) { this.scheduleId = scheduleId; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public void setRoom(String room) { this.room = room; }
    public void setBuilding(String building) { this.building = building; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setRepeatType(String repeatType) { this.repeatType = repeatType; }
    public void setFormat(String format) { this.format = format; }
    public void setNote(String note) { this.note = note; }
    public void setGroupId(String groupId) { this.groupId = groupId; }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", room='" + room + '\'' +
                ", building='" + building + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", repeatType='" + repeatType + '\'' +
                ", format='" + format + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
