package app;
import java.awt.*;
import java.sql.Connection;
import javax.swing.*;

import app.ui.LoginPanel;
import app.ui.MainFrame;
import app.ui.MainPanel;
import app.dao.DatabaseConnection;
public class Main {
    public static void main(String[] args) {
            // Kiểm tra kết nối DB khi khởi động
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(null,
                        "Không thể kết nối đến CSDL!",
                        "Lỗi kết nối",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1); //thoát ngay khi thông báo xong
            } else {
                System.out.println("Kết nối DB thành công!");
            }

            //Kết nối thành công, mở giao diện chính
            MainFrame frame = new MainFrame();
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);

            frame.setVisible(true);
    }
}