package app.dao;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/class_management";
    private static final String USER = "root"; //nhập username host vào
    private static final String PASSWORD = "your_password"; //nhập password vào

    static { //khối static, chạy 1 lần khi class nạp
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // nạp driver MySQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD); //kết nối DB qua driver với cấu hình
        } catch (SQLException e) {
            System.out.println("Kết nối DB thất bại!");
            e.printStackTrace();
            return null;
        }
    }
}
