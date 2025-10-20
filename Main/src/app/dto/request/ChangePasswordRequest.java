package app.dto.request;

import app.model.Student;
import app.session.Session;
import app.dao.*;
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
    public static boolean updatePassword(String username, String newPassword) {
        String role="";
        if(Session.getRole().equals("Teacher")) role = "teachers";
        else role= "students";
        String query = String.format(
                "UPDATE %s SET password = '%s' WHERE username = '%s'",
                escapeString(role),
                escapeString(newPassword),
                escapeString(username)
        );

        try {
            boolean result = DatabaseConnection.insertTable(query);
            if (result) {
                System.out.println("Đổi mật khẩu thành công ");
            }
            return result;
        } catch (Exception e) {
            System.out.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    private static String escapeString(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("'", "''");
    }
}
