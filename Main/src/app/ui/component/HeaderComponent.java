package app.ui.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import app.ui.MainPanel;

public class HeaderComponent extends JPanel {
    MainPanel mainPanel;
    public HeaderComponent(String[] navItem, MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(41, 128, 185)); // Màu xanh dương chuyên nghiệp

        for(int i = 0; i< navItem.length; i++) {
            JLabel label = createLabel(navItem[i]);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (label.getText()) {
                        case "Đăng xuất":
                            mainPanel.show("Role");
                            break;
                        case "Thông tin cá nhân":
                            mainPanel.show("ChangeProfile");
                            break;
                        case "Trang chủ":
                            mainPanel.show("teacher_dashboard");
                            break;
                        case "Lịch học":
                            mainPanel.show("ScheduleDisplay");
                            break;
                        case "Quản lý điểm":
                            mainPanel.show("GradeManagement");
                            break;
                        case "Đổi mật khẩu":
                            mainPanel.show("ChangePassword");
                            break;
                        case "Chỉnh sửa lớp học":
                            mainPanel.show("Edit_class");
                            break;
                        case "Lịch học học sinh":
                            mainPanel.show("StudentSchedule");
                            break;
                        case "Thông tin cá nhân học sinh":
                            mainPanel.show("ChangeProfileStudent");
                            break;
                        case "Trang chủ học sinh":
                            mainPanel.show("student_dashboard");
                            break;
                        case "Quay lại":
                            mainPanel.back();
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(new Color(241, 196, 15)); // Màu vàng đẹp khi hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setForeground(Color.WHITE);
                }
            });
            add(label);
        }
    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return label;
    }
}
