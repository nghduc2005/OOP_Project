package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

 public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://trolley.proxy.rlwy.net:50772/railway";
    private static final String USER = "postgres";
    private static final String PASSWORD = "nXaDReqIxhxnRJALsPnNQdRIWsoFYqoX";

    public static Connection getConnection() {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công tới Railway PostgreSQL!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối CSDL:");
            e.printStackTrace();
            return null;
        }
    }
}
