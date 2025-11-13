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
import app.session.Session; // <-- THÊM IMPORT NÀY

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

                    // Lấy role Studen/Teacher
                    String role = Session.getRole();
                    if (role == null) {
                        role = ""; // Tránh lỗi NullPointerException
                    }

                    switch (label.getText()) {
                        case "Đăng xuất":
                            mainPanel.show("Role");
                            break;

                        case "Thông tin cá nhân":
                            if (role.equals("Student")) {
                                mainPanel.show("ChangeProfileStudent");
                            } else {
                                mainPanel.show("ChangeProfile");
                            }
                            break;

                        case "Trang chủ":
                            if (role.equals("Student")) {
                                mainPanel.show("student_dashboard");
                            } else {
                                mainPanel.reloadDashboard();
                            }
                            break;

                        case "Lịch học":
                            if (role.equals("Student")) {
                                mainPanel.show("StudentSchedule");
                            } else {
                                mainPanel.show("ScheduleDisplay");
                            }
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