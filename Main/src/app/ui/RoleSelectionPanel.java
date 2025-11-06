package app.ui;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {
    static boolean I_am_teacher=false, I_am_student=false;
    JButton teacherButton, studentButton;
    MainPanel mainPanel;
    int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    int H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public RoleSelectionPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(null);
        setBackground(new Color(245, 247, 250)); // Màu nền sáng

        JLabel title = new JLabel("Chọn hình thức đăng nhập : ");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setBounds(W/2-125, H/2-200, 300, 50);
        title.setForeground(new Color(44, 62, 80)); // Màu chữ đẹp thay vì đỏ

        teacherButton = new JButton("Giáo viên");
        studentButton = new JButton("Học sinh");
        
        // Style cho button giáo viên
        teacherButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        teacherButton.setBackground(new Color(142, 68, 173)); // Tím
        teacherButton.setForeground(Color.WHITE);
        teacherButton.setBorderPainted(false);
        teacherButton.setFocusPainted(false);
        teacherButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        teacherButton.setBounds(W/2-100, H/2-150, 200, 40);
        
        // Style cho button học sinh
        studentButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        studentButton.setBackground(new Color(52, 152, 219)); // Xanh dương
        studentButton.setForeground(Color.WHITE);
        studentButton.setBorderPainted(false);
        studentButton.setFocusPainted(false);
        studentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        studentButton.setBounds(W/2-100, H/2-150+60, 200, 40);
        // sự kiện bấm nút
        teacherButton.addActionListener(e -> {
                    I_am_teacher=true;
                    mainPanel.show("Log_t");
                }
        );
        studentButton.addActionListener(e -> {
            I_am_student=true;
            mainPanel.show("Log_s");
        });

        add(title);
        add(teacherButton);
        add(studentButton);
    }
}
