package app.service;

import app.dao.ScheduleDao;
import app.model.Schedule;
import java.time.LocalDate;
import java.util.List;

public class ScheduleService {

    public static boolean createSchedule(Schedule schedule) {
        if (schedule == null) {
            System.out.println("Lịch học không được null!");
            return false;
        }
        return ScheduleDao.createSchedule(schedule);
    }

    public static Schedule getScheduleById(Integer scheduleId) {
        return ScheduleDao.getScheduleById(scheduleId);
    }

    public static List<Schedule> getAllSchedules() {
        return ScheduleDao.getAllSchedules();
    }

    public static List<Schedule> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        return ScheduleDao.getSchedulesByDateRange(startDate, endDate);
    }

    public static List<Schedule> getSchedulesByDate(LocalDate date) {
        return ScheduleDao.getSchedulesByDate(date);
    }

    public static List<Schedule> getTodaySchedules() {
        return ScheduleDao.getSchedulesByDate(LocalDate.now());
    }

    public static List<Schedule> getThisWeekSchedules() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return ScheduleDao.getSchedulesByDateRange(startOfWeek, endOfWeek);
    }

    public static List<Schedule> getThisMonthSchedules() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return ScheduleDao.getSchedulesByDateRange(startOfMonth, endOfMonth);
    }

    public static boolean updateSchedule(Schedule schedule) {
        if (schedule == null) {
            System.out.println("Lịch học không được null!");
            return false;
        }
        return ScheduleDao.updateSchedule(schedule);
    }

    public static boolean deleteSchedule(String scheduleId) {
        return ScheduleDao.deleteSchedule(scheduleId);
    }

    public static List<Schedule> searchSchedules(String searchTerm) {
        return ScheduleDao.searchSchedules(searchTerm);
    }
}
