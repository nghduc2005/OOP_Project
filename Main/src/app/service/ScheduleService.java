package app.service;

import app.dao.ScheduleDao;
import app.model.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleService {

    /**
     * Tạo lịch học mới
     */
    public static boolean createSchedule(Schedule schedule) {
        if (schedule == null) {
            System.out.println("Lịch học không được null!");
            return false;
        }
        return ScheduleDao.createSchedule(schedule);
    }

    /**
     * Tạo lịch học với thông tin đầy đủ
     */
    public static boolean createSchedule(String subjectName, String teacherName,
                                         String room, String building, LocalDate scheduleDate,
                                         LocalTime startTime, LocalTime endTime,
                                         String repeatType, String format, String note, String groupId) {
        Schedule schedule = new Schedule(null, subjectName, teacherName, room, building,
                scheduleDate, startTime, endTime, repeatType, format, note, groupId);
        return createSchedule(schedule);
    }

    /**
     * Lấy lịch học theo ID
     */
    public static Schedule getScheduleById(String scheduleId) {
        return ScheduleDao.getScheduleById(scheduleId);
    }

    /**
     * Lấy tất cả lịch học
     */
    public static List<Schedule> getAllSchedules() {
        return ScheduleDao.getAllSchedules();
    }

    /**
     * Lấy lịch học theo giảng viên
     */
    public static List<Schedule> getSchedulesByTeacher(String teacherName) {
        return ScheduleDao.getSchedulesByTeacher(teacherName);
    }

    /**
     * Lấy lịch học theo môn học
     */
    public static List<Schedule> getSchedulesBySubject(String subjectName) {
        return ScheduleDao.getSchedulesBySubject(subjectName);
    }

    /**
     * Lấy lịch học theo nhóm/lớp
     */
    public static List<Schedule> getSchedulesByGroup(String groupId) {
        return ScheduleDao.getSchedulesByGroup(groupId);
    }

    /**
     * Lấy lịch học theo khoảng thời gian
     */
    public static List<Schedule> getSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        return ScheduleDao.getSchedulesByDateRange(startDate, endDate);
    }

    /**
     * Lấy lịch học theo ngày cụ thể
     */
    public static List<Schedule> getSchedulesByDate(LocalDate date) {
        return ScheduleDao.getSchedulesByDate(date);
    }

    /**
     * Lấy lịch học hôm nay
     */
    public static List<Schedule> getTodaySchedules() {
        return ScheduleDao.getSchedulesByDate(LocalDate.now());
    }

    /**
     * Lấy lịch học tuần này
     */
    public static List<Schedule> getThisWeekSchedules() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        return ScheduleDao.getSchedulesByDateRange(startOfWeek, endOfWeek);
    }

    /**
     * Lấy lịch học tháng này
     */
    public static List<Schedule> getThisMonthSchedules() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return ScheduleDao.getSchedulesByDateRange(startOfMonth, endOfMonth);
    }

    /**
     * Cập nhật lịch học
     */
    public static boolean updateSchedule(Schedule schedule) {
        if (schedule == null) {
            System.out.println("Lịch học không được null!");
            return false;
        }
        return ScheduleDao.updateSchedule(schedule);
    }

    /**
     * Xóa lịch học
     */
    public static boolean deleteSchedule(String scheduleId) {
        return ScheduleDao.deleteSchedule(scheduleId);
    }

    /**
     * Tìm kiếm lịch học
     */
    public static List<Schedule> searchSchedules(String searchTerm) {
        return ScheduleDao.searchSchedules(searchTerm);
    }

    /**
     * Kiểm tra xung đột lịch học (cùng phòng, cùng thời gian)
     */
    public static boolean checkScheduleConflict(Schedule schedule) {
        if (schedule == null || schedule.getScheduleDate() == null) {
            return false;
        }

        List<Schedule> sameDateSchedules = ScheduleDao.getSchedulesByDate(schedule.getScheduleDate());

        for (Schedule existingSchedule : sameDateSchedules) {
            // Skip if comparing with itself
            if (schedule.getScheduleId() != null &&
                    schedule.getScheduleId().equals(existingSchedule.getScheduleId())) {
                continue;
            }

            // Check if same room and building
            if (schedule.getRoom().equals(existingSchedule.getRoom()) &&
                    schedule.getBuilding().equals(existingSchedule.getBuilding())) {

                // Check time overlap
                if (isTimeOverlap(schedule.getStartTime(), schedule.getEndTime(),
                        existingSchedule.getStartTime(), existingSchedule.getEndTime())) {
                    System.out.println("Xung đột lịch học: Phòng " + schedule.getRoom() +
                            " đã có lịch từ " + existingSchedule.getStartTime() +
                            " đến " + existingSchedule.getEndTime());
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Kiểm tra 2 khoảng thời gian có trùng nhau không
     */
    private static boolean isTimeOverlap(LocalTime start1, LocalTime end1,
                                         LocalTime start2, LocalTime end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }

    /**
     * Validate dữ liệu lịch học
     */
    public static boolean validateScheduleData(String subjectName, String teacherName,
                                               String room, String building,
                                               LocalDate scheduleDate, LocalTime startTime,
                                               LocalTime endTime) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            System.out.println("Tên môn học không được rỗng!");
            return false;
        }
        if (teacherName == null || teacherName.trim().isEmpty()) {
            System.out.println("Tên giảng viên không được rỗng!");
            return false;
        }
        if (room == null || room.trim().isEmpty()) {
            System.out.println("Phòng học không được rỗng!");
            return false;
        }
        if (building == null || building.trim().isEmpty()) {
            System.out.println("Tòa nhà không được rỗng!");
            return false;
        }
        if (scheduleDate == null) {
            System.out.println("Ngày học không được rỗng!");
            return false;
        }
        if (startTime == null) {
            System.out.println("Thời gian bắt đầu không được rỗng!");
            return false;
        }
        if (endTime == null) {
            System.out.println("Thời gian kết thúc không được rỗng!");
            return false;
        }
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            System.out.println("Thời gian bắt đầu phải trước thời gian kết thúc!");
            return false;
        }
        if (scheduleDate.isBefore(LocalDate.now())) {
            System.out.println("Không thể tạo lịch cho ngày trong quá khứ!");
            return false;
        }
        return true;
    }
}
