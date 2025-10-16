package app;


import app.dao.DatabaseConnection;
import app.ui.MainFrame;
import app.ui.MainPanel;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
            DatabaseConnection connection = new DatabaseConnection();

            //Kết nối thành công, mở giao diện chính
            MainFrame frame = new MainFrame();
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);
            frame.setVisible(true);
    }
}

