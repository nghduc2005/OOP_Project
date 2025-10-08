package app.ui;
import java.awt.*;
import javax.swing.*;
public class MainFrame extends JFrame {
    public MainFrame() {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        //Set up các cấu hình cơ bản của frame
        setTitle("Quản lý lớp học");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
}
