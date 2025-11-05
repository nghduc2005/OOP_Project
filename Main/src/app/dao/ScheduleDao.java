package app.dao;

import app.model.Schedule;
import app.session.Session;

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
        return getSchedulesByQuery("SELECT * FROM schedules s join classes c on s.class_id = c.class_id  ORDER BY " +
                "study_date, " +
                "start_time");
    }
    public static List<Schedule> getAllStudentSchedules() {
        return getSchedulesByQuery("select * from schedules sche join classes cl on sche.class_id = cl.class_id join " +
                "student_class sc on sc.class_id = cl.class_id join students s on s.username = sc.username where s" +
                ".username = '" + Session.getUsername() +"'");
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
        String query = "DELETE FROM schedules WHERE schedule_id = " + scheduleId;
        try {
            return DatabaseConnection.deleteRecord(query);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Schedule> searchSchedules(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllSchedules();
        }
        
        // Try enhanced search with JOINs first, fallback to basic search if tables don't exist
        try {
            String enhancedQuery = String.format(
                    "SELECT DISTINCT s.* FROM schedules s " +
                    "LEFT JOIN subjects sub ON s.subject_id = sub.subject_id " +
                    "LEFT JOIN classes c ON s.class_id = c.class_id " +
                    "WHERE s.classroom LIKE '%%%s%%' " +
                    "OR s.study_shift LIKE '%%%s%%' " +
                    "OR s.note LIKE '%%%s%%' " +
                    "OR s.study_date LIKE '%%%s%%' " +
                    "OR sub.name LIKE '%%%s%%' " +
                    "OR c.subject_name LIKE '%%%s%%' " +
                    "ORDER BY s.study_date, s.start_time",
                    escapeString(searchTerm), escapeString(searchTerm), escapeString(searchTerm),
                    escapeString(searchTerm), escapeString(searchTerm), escapeString(searchTerm)
            );
            
            List<Schedule> results = getSchedulesByQuery(enhancedQuery);
            if (results != null && !results.isEmpty()) {
                return results;
            }
        } catch (Exception e) {
            System.out.println("Enhanced search failed, falling back to basic search: " + e.getMessage());
        }
        
        // Fallback to basic search if enhanced search fails
        String basicQuery = String.format(
                "SELECT * FROM schedules WHERE classroom LIKE '%%%s%%' OR study_shift LIKE '%%%s%%' " +
                        "OR note LIKE '%%%s%%' ORDER BY study_date",
                escapeString(searchTerm), escapeString(searchTerm), escapeString(searchTerm)
        );
        return getSchedulesByQuery(basicQuery);
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

    /**
     * Tìm kiếm lịch theo tên môn học
     */
    public static List<Schedule> searchSchedulesBySubject(String subjectName) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            return getAllSchedules();
        }
        String query = String.format(
                "SELECT DISTINCT s.* FROM schedules s " +
                "LEFT JOIN subjects sub ON s.subject_id = sub.subject_id " +
                "LEFT JOIN classes c ON s.class_id = c.class_id " +
                "WHERE sub.name LIKE '%%%s%%' OR c.subject_name LIKE '%%%s%%' " +
                "ORDER BY s.study_date, s.start_time",
                escapeString(subjectName), escapeString(subjectName)
        );
        return getSchedulesByQuery(query);
    }

    /**
     * Tìm kiếm lịch theo mã lớp hoặc tên lớp
     */
    public static List<Schedule> searchSchedulesByClass(String className) {
        if (className == null || className.trim().isEmpty()) {
            return getAllSchedules();
        }
        String query = String.format(
                "SELECT DISTINCT s.* FROM schedules s " +
                "LEFT JOIN classes c ON s.class_id = c.class_id " +
                "WHERE s.class_id LIKE '%%%s%%' OR c.subject_name LIKE '%%%s%%' " +
                "ORDER BY s.study_date, s.start_time",
                escapeString(className), escapeString(className)
        );
        return getSchedulesByQuery(query);
    }

    /**
     * Tìm kiếm lịch theo ngày (hỗ trợ nhiều format)
     */
    public static List<Schedule> searchSchedulesByDate(String dateSearch) {
        if (dateSearch == null || dateSearch.trim().isEmpty()) {
            return getAllSchedules();
        }
        
        // Hỗ trợ tìm kiếm theo năm (2024), tháng-năm (12-2024), hoặc ngày đầy đủ
        String query = String.format(
                "SELECT * FROM schedules WHERE study_date LIKE '%%%s%%' " +
                "ORDER BY study_date, start_time",
                escapeString(dateSearch)
        );
        return getSchedulesByQuery(query);
    }

    /**
     * Tìm kiếm nâng cao với nhiều tiêu chí
     */
    public static List<Schedule> advancedSearch(String subjectName, String className, String classroom, String date) {
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT s.* FROM schedules s ");
        queryBuilder.append("LEFT JOIN subjects sub ON s.subject_id = sub.subject_id ");
        queryBuilder.append("LEFT JOIN classes c ON s.class_id = c.class_id WHERE 1=1 ");
        
        if (subjectName != null && !subjectName.trim().isEmpty()) {
            queryBuilder.append(String.format("AND (sub.name LIKE '%%%s%%' OR c.subject_name LIKE '%%%s%%') ", 
                    escapeString(subjectName), escapeString(subjectName)));
        }
        
        if (className != null && !className.trim().isEmpty()) {
            queryBuilder.append(String.format("AND s.class_id LIKE '%%%s%%' ", escapeString(className)));
        }
        
        if (classroom != null && !classroom.trim().isEmpty()) {
            queryBuilder.append(String.format("AND s.classroom LIKE '%%%s%%' ", escapeString(classroom)));
        }
        
        if (date != null && !date.trim().isEmpty()) {
            queryBuilder.append(String.format("AND s.study_date LIKE '%%%s%%' ", escapeString(date)));
        }
        
        queryBuilder.append("ORDER BY s.study_date, s.start_time");
        
        return getSchedulesByQuery(queryBuilder.toString());
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
            String subjectName =  (String) row.get("subject_name");
            Integer subjectId = subjectIdObj instanceof Integer ? (Integer) subjectIdObj :
                    subjectIdObj instanceof Long ? ((Long) subjectIdObj).intValue() : null;
            Object classIdObj = row.get("class_id");
            Integer classId = classIdObj instanceof Integer ? (Integer) classIdObj :
                    classIdObj instanceof Long ? ((Long) classIdObj).intValue() : null;
            Object startTimeObj = row.get("start_time");
            LocalDateTime startTime = startTimeObj instanceof java.sql.Timestamp ?
                    ((java.sql.Timestamp) startTimeObj).toLocalDateTime() : LocalDateTime.now();
            return new Schedule(scheduleId, studyDate, studyShift, totalSessions,
                    learningMethod, classroom, note, subjectId, classId, startTime, subjectName);
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
