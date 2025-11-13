package app;

public final class Constant {
    public final static String STU_USERNAME_PATTERN = "^STU\\d{6}$";
    public final static String STU_PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]+$";

    public final static String TC_USERNAME_PATTERN = "^TC\\d{6}$";
    public final static String TC_PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]+$";

    public final static String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@ptit\\.edu\\.vn$"; //đuôi @ptit.edu.vn
    public final static String LASTNAME_PATTERN = "^[A-Za-zÀ-ỹ\\s]+$"; //cho phép latinh, tiếng việt, khoảng trắng
    public final static String FIRSTNAME_PATTERN = "^[A-Za-zÀ-ỹ\\s]+$"; //cho phép latinh, tiếng việt, khoảng trắng
    public final static String PHONENUMBER_PATTERN = "^[0-9]{10}$";

    public final static String DB_URL_CONNECT = "jdbc:mysql://crossover.proxy.rlwy.net:35590/railway";
    public final static String DB_USER_CONNECT = "root";
    public final static String DB_PASSWORD_CONNECT = "wmhsqZmmgNFujhyCElFykkdkqigAzMTP";

    public final static String TEACHER_USERNAME = "NVA000001";
}