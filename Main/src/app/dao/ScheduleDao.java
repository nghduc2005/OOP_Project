package app.dao;

import app.model.Schedule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleDao {

    public static boolean createSchedule(Schedule schedule) {
        if (schedule == null || !validateSchedule(schedule)) {
            return false;
        }

        String query = String.format(
                "INSERT INTO schedules (study_date, study_shift, total_sessions, learning_method, " +
                        "classroom, note, subject_id, class_id, start_time) " +
                        "VALUES ('%s', '%s', %d, '%s', '%s', '%s', %d, %d, '%s')",
                schedule.getStudyDate(), escapeString(schedule.getStudyShift()), schedule.getTotalSessions(),
                escapeString(schedule.getLearningMethod()), escapeString(schedule.getClassroom()),
                escapeString(schedule.getNote()), schedule.getSubjectId(), schedule.getClassId(),
                schedule.getStartTime() != null ? schedule.getStartTime() : LocalDateTime.now()
        );

        try {
            return DatabaseConnection.insertTable(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Schedule getScheduleById(Integer scheduleId) {
        if (scheduleId == null) return null;

        String query = "SELECT * FROM schedules WHERE schedule_id = " + scheduleId;
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null && !results.isEmpty()) {
                return mapToSchedule(results.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Schedule> getAllSchedules() {
        return getSchedulesByQuery("SELECT * FROM schedules ORDER BY study_date, start_time");
    }

    public static boolean updateSchedule(Schedule schedule) {
        if (schedule == null || schedule.getScheduleId() == null || !validateSchedule(schedule)) {
            return false;
        }

        String query = String.format(
                "UPDATE schedules SET study_date='%s', study_shift='%s', total_sessions=%d, " +
                        "learning_method='%s', classroom='%s', note='%s', subject_id=%d, class_id=%d, start_time='%s' " +
                        "WHERE schedule_id=%d",
                schedule.getStudyDate(), escapeString(schedule.getStudyShift()), schedule.getTotalSessions(),
                escapeString(schedule.getLearningMethod()), escapeString(schedule.getClassroom()),
                escapeString(schedule.getNote()), schedule.getSubjectId(), schedule.getClassId(),
                schedule.getStartTime() != null ? schedule.getStartTime() : LocalDateTime.now(),
                schedule.getScheduleId()
        );

        try {
            return DatabaseConnection.updateRecord(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteSchedule(String scheduleId) {
        return false;
    }

    public static List<Schedule> searchSchedules(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllSchedules();
        }
        String query = String.format(
                "SELECT * FROM schedules WHERE classroom LIKE '%%%s%%' OR study_shift LIKE '%%%s%%' " +
                        "OR note LIKE '%%%s%%' ORDER BY study_date",
                escapeString(searchTerm), escapeString(searchTerm), escapeString(searchTerm)
        );
        return getSchedulesByQuery(query);
    }

    public static List<Schedule> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        String query = String.format(
                "SELECT * FROM schedules WHERE study_date BETWEEN '%s' AND '%s' ORDER BY study_date, start_time",
                startDate, endDate
        );
        return getSchedulesByQuery(query);
    }

    public static List<Schedule> getSchedulesByDate(LocalDate date) {
        String query = "SELECT * FROM schedules WHERE study_date = '" + date + "' ORDER BY start_time";
        return getSchedulesByQuery(query);
    }


    private static boolean validateSchedule(Schedule schedule) {
        return schedule.getStudyDate() != null && schedule.getStudyShift() != null &&
                schedule.getClassroom() != null && schedule.getSubjectId() != null &&
                schedule.getClassId() != null && schedule.getTotalSessions() != null && schedule.getTotalSessions() > 0;
    }

    private static List<Schedule> getSchedulesByQuery(String query) {
        List<Schedule> schedules = new ArrayList<>();
        try {
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            if (results != null) {
                for (HashMap<String, Object> row : results) {
                    Schedule schedule = mapToSchedule(row);
                    if (schedule != null) schedules.add(schedule);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }

    private static Schedule mapToSchedule(HashMap<String, Object> row) {
        try {
            Integer scheduleId = (Integer) row.get("schedule_id");
            Object dateObj = row.get("study_date");
            LocalDate studyDate = dateObj instanceof java.sql.Date ? ((java.sql.Date) dateObj).toLocalDate() : LocalDate.now();
            String studyShift = (String) row.get("study_shift");
            Object sessionsObj = row.get("total_sessions");
            Integer totalSessions = sessionsObj instanceof Integer ? (Integer) sessionsObj :
                    sessionsObj instanceof Long ? ((Long) sessionsObj).intValue() : 1;
            String learningMethod = (String) row.get("learning_method");
            String classroom = (String) row.get("classroom");
            String note = (String) row.get("note");
            Object subjectIdObj = row.get("subject_id");
            Integer subjectId = subjectIdObj instanceof Integer ? (Integer) subjectIdObj :
                    subjectIdObj instanceof Long ? ((Long) subjectIdObj).intValue() : null;
            Object classIdObj = row.get("class_id");
            Integer classId = classIdObj instanceof Integer ? (Integer) classIdObj :
                    classIdObj instanceof Long ? ((Long) classIdObj).intValue() : null;
            Object startTimeObj = row.get("start_time");
            LocalDateTime startTime = startTimeObj instanceof java.sql.Timestamp ?
                    ((java.sql.Timestamp) startTimeObj).toLocalDateTime() : LocalDateTime.now();

            return new Schedule(scheduleId, studyDate, studyShift, totalSessions,
                    learningMethod, classroom, note, subjectId, classId, startTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String escapeString(String str) {
        if (str == null) return "";
        return str.replace("'", "''");
    }
}
