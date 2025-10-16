package app.dao;

import app.Constant;

import java.sql.*;
import java.util.*;

public class DatabaseConnection {
    private static final String URL = Constant.DB_URL_CONNECT;
    private static final String USER = Constant.DB_USER_CONNECT;
    private static final String PASSWORD = Constant.DB_PASSWORD_CONNECT;

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

    //Trả về danh sách bản ghi truy vấn được
    public static List<HashMap<String,Object>> readTable(String queryString) {
        List<HashMap<String, Object>> results = new ArrayList<>();
        try(
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ) {
            String sql = queryString;
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            while(rs.next()) {
                HashMap<String,Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String colName = meta.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    row.put(colName, value);
                }
                results.add(row);
            }
            System.out.println("Truy vấn thành công!");
            conn.close();
            return results;
        } catch (Exception e) {
            System.out.println("Truy vấn thất bại!");
            e.printStackTrace();
            return null;
        }
    }
    public static Boolean insertTable(String queryString) {
        List<HashMap<String, Object>> results = new ArrayList<>();
        try(
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            String sql = queryString;
            stmt.executeUpdate(sql);
            System.out.println("Chèn thành công!");
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println("Chèn thất bại!");
            e.printStackTrace();
            return false;
        }
    }
}
