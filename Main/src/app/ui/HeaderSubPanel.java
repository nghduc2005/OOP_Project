package app.ui;

import javax.swing.*;
import java.awt.*;

// Quay lại + Thông Tin Cá Nhân + Đăng Xuất
public class HeaderSubPanel extends JPanel {
    private JLabel backLabel;
    private JLabel infoLabel;
    private JLabel logoutLabel;

    public HeaderSubPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(150, 150, 150)); // nền xám

        backLabel = createLabel("Quay lại");
        infoLabel = createLabel("Thông tin cá nhân");
        logoutLabel = createLabel("Đăng xuất");

        add(backLabel);
        add(infoLabel);
        add(logoutLabel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    public JLabel getBackLabel() { return backLabel; }
    public JLabel getInfoLabel() { return infoLabel; }
    public JLabel getLogoutLabel() { return logoutLabel; }
}
