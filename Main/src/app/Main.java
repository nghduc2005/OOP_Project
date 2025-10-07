package app;


import app.dao.DatabaseConnection;
import app.ui.MainFrame;
import app.ui.MainPanel;

public class Main {
    public static void main(String[] args) {
            DatabaseConnection connection = new DatabaseConnection();
            if(connection.getConnection() == null) {
                System.exit(0);
            }
            //Kết nối thành công, mở giao diện chính
            MainFrame frame = new MainFrame();
            MainPanel mainPanel = new MainPanel();
            frame.add(mainPanel);

            frame.setVisible(true);

    }
}