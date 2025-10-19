package app.util;

public class ValidationConstants {

    // SUBJECT CONSTRAINTS
    public static final int SUBJECT_ID_MIN_LENGTH = 3;
    public static final int SUBJECT_ID_MAX_LENGTH = 10;
    public static final int SUBJECT_NAME_MIN_LENGTH = 3;
    public static final int SUBJECT_NAME_MAX_LENGTH = 100;
    public static final int CREDIT_MIN = 1;
    public static final int CREDIT_MAX = 10;

    // GROUP CONSTRAINTS
    public static final int GROUP_ID_MIN_LENGTH = 3;
    public static final int GROUP_ID_MAX_LENGTH = 20;
    public static final int GROUP_NAME_MIN_LENGTH = 3;
    public static final int GROUP_NAME_MAX_LENGTH = 100;
    public static final int MAX_STUDENTS_MIN = 10;
    public static final int MAX_STUDENTS_MAX = 100;
    public static final int DEFAULT_MAX_STUDENTS = 30;

    // STUDENT CONSTRAINTS
    public static final int STUDENT_ID_MIN_LENGTH = 3;
    public static final int STUDENT_ID_MAX_LENGTH = 20;
    public static final int STUDENT_NAME_MIN_LENGTH = 3;
    public static final int STUDENT_NAME_MAX_LENGTH = 100;
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int PHONE_MIN_LENGTH = 10;
    public static final int PHONE_MAX_LENGTH = 15;

    // TEACHER CONSTRAINTS
    public static final int TEACHER_ID_MIN_LENGTH = 3;
    public static final int TEACHER_ID_MAX_LENGTH = 20;
    public static final int TEACHER_NAME_MIN_LENGTH = 3;
    public static final int TEACHER_NAME_MAX_LENGTH = 100;

    // ERROR MESSAGES
    public static final String SUBJECT_ID_REQUIRED = "Mã môn học không được rỗng!";
    public static final String SUBJECT_NAME_REQUIRED = "Tên môn học không được rỗng!";
    public static final String SUBJECT_ID_INVALID_LENGTH =
            "Mã môn học phải từ " + SUBJECT_ID_MIN_LENGTH + " đến " + SUBJECT_ID_MAX_LENGTH + " ký tự!";
    public static final String SUBJECT_NAME_INVALID_LENGTH =
            "Tên môn học phải từ " + SUBJECT_NAME_MIN_LENGTH + " đến " + SUBJECT_NAME_MAX_LENGTH + " ký tự!";
    public static final String CREDIT_INVALID_RANGE =
            "Số tín chỉ phải từ " + CREDIT_MIN + " đến " + CREDIT_MAX + "!";

    public static final String GROUP_ID_REQUIRED = "Mã lớp không được rỗng!";
    public static final String GROUP_NAME_REQUIRED = "Tên lớp không được rỗng!";
    public static final String SUBJECT_NAME_FOR_GROUP_REQUIRED = "Tên môn học không được rỗng!";
    public static final String TEACHER_NAME_REQUIRED = "Tên giảng viên không được rỗng!";
    public static final String GROUP_ID_INVALID_LENGTH =
            "Mã lớp phải từ " + GROUP_ID_MIN_LENGTH + " đến " + GROUP_ID_MAX_LENGTH + " ký tự!";
    public static final String GROUP_NAME_INVALID_LENGTH =
            "Tên lớp phải từ " + GROUP_NAME_MIN_LENGTH + " đến " + GROUP_NAME_MAX_LENGTH + " ký tự!";
    public static final String MAX_STUDENTS_INVALID_RANGE =
            "Số sinh viên tối đa phải từ " + MAX_STUDENTS_MIN + " đến " + MAX_STUDENTS_MAX + "!";
    public static final String GROUP_FULL = "Lớp học đã đầy!";
    public static final String STUDENT_ALREADY_IN_GROUP = "Sinh viên đã có trong lớp!";
    public static final String CANNOT_REDUCE_MAX_STUDENTS =
            "Số sinh viên tối đa mới không thể nhỏ hơn số sinh viên hiện tại!";

    public static final String STUDENT_ID_REQUIRED = "Mã sinh viên không được rỗng!";
    public static final String STUDENT_NAME_REQUIRED = "Tên sinh viên không được rỗng!";
    public static final String EMAIL_REQUIRED = "Email không được rỗng!";
    public static final String EMAIL_INVALID_FORMAT = "Email không hợp lệ!";

    public static final String ENTITY_NULL = "Entity không được null!";
    public static final String ID_REQUIRED = "ID không được rỗng!";
    public static final String ENTITY_NOT_FOUND = "Entity không tồn tại: ";
    public static final String ENTITY_ALREADY_EXISTS = "Entity đã tồn tại: ";

    // REGEX PATTERNS (đồng bộ với app.Constant)
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@ptit\\.edu\\.vn$";
    public static final String PHONE_PATTERN = "^[0-9]{10}$";
    public static final String STUDENT_ID_PATTERN = "^STU\\d{6}$";
    public static final String TEACHER_ID_PATTERN = "^TC\\d{6}$";

    // OPTIONAL: nếu cần dùng kiểm tra mật khẩu theo Constant
    public static final String STUDENT_PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]+$";
    public static final String TEACHER_PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]+$";

    // UTILITY METHODS
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return email.matches(EMAIL_PATTERN);
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return false;
        return phone.matches(PHONE_PATTERN);
    }

    public static boolean isValidStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) return false;
        return studentId.matches(STUDENT_ID_PATTERN);
    }

    public static boolean isValidTeacherId(String teacherId) {
        if (teacherId == null || teacherId.trim().isEmpty()) return false;
        return teacherId.matches(TEACHER_ID_PATTERN);
    }

    public static boolean isValidStudentPassword(String password) {
        if (password == null || password.trim().isEmpty()) return false;
        return password.matches(STUDENT_PASSWORD_PATTERN);
    }

    public static boolean isValidTeacherPassword(String password) {
        if (password == null || password.trim().isEmpty()) return false;
        return password.matches(TEACHER_PASSWORD_PATTERN);
    }

    private ValidationConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
