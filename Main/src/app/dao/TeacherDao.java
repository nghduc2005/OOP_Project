package app.dao;

import app.model.Student;
import app.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherDao {
    private static String escapeString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }
    public static String getPasswordByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username không được rỗng!");
            return null;
        }

        String sql = "SELECT password FROM teachers WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy mật khẩu: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateTeacher(String full_name, String birthday, String phone, String email) {


        String query = String.format(
                "UPDATE teachers SET fullname = '%s', dateOfBirth = '%s' ,email = '%s', phone = '%s' WHERE username = '%s'",
                escapeString(full_name),
                escapeString(birthday),
                escapeString(email != null ? email: ""),
                escapeString(phone != null ? phone : ""),
                escapeString(Session.getUsername())
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Cập nhật giáo viên thành công ");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật giáo viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
