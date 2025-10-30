package app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import app.Constant;

public class DatabaseConnection {
    private static final String URL = Constant.DB_URL_CONNECT + 
        "?connectTimeout=30000" +      // 30 giây timeout
        "&socketTimeout=30000" +       // 30 giây socket timeout
        "&autoReconnect=true" +        // Auto reconnect
        "&maxReconnects=3";            // Thử reconnect 3 lần
    private static final String USER = Constant.DB_USER_CONNECT;
    private static final String PASSWORD = Constant.DB_PASSWORD_CONNECT;

    public static Connection getConnection() {
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công tới Railway MySQL!");
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
            return results;
        } catch (Exception e) {
            System.out.println("Truy vấn thất bại!");
            e.printStackTrace();
            return null;
        }
    }
    public static Boolean insertTable(String queryString) {
        try(
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            String sql = queryString;
            stmt.executeUpdate(sql);
            System.out.println("Chèn thành công!");
            return true;
        } catch (Exception e) {
            System.out.println("Chèn thất bại!");
            e.printStackTrace();
            return false;
        }
    }
    public static Boolean deleteRecord(String queryString) {
        try(
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            String sql = queryString;
            stmt.executeUpdate(sql);
            System.out.println("Xóa thành công!");
            return true;
        } catch (Exception e) {
            System.out.println("Xóa thất bại!");
            e.printStackTrace();
            return false;
        }
    }
    public static Boolean updateRecord(String queryString) {
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
        ) {
            String sql = queryString;
            int rowsAffected = stmt.executeUpdate(sql);
            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công! (" + rowsAffected + " dòng bị ảnh hưởng)");
                return true;
            } else {
                System.out.println("Không có dòng nào được cập nhật!");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Cập nhật thất bại!");
            e.printStackTrace();
            return false;
        }
    }

}
