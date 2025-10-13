package app.ui;

import javax.swing.*;
import java.awt.*;

//Thông tin cá nhân + Đăng Xuất
public class HeaderMainPanel extends JPanel {
    private JLabel infoLabel;
    private JLabel logoutLabel;

    public HeaderMainPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(150, 150, 150)); // nền xám

        infoLabel = createLabel("Thông tin cá nhân");
        logoutLabel = createLabel("Đăng xuất");

        add(infoLabel);
        add(logoutLabel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    public JLabel getInfoLabel() { return infoLabel; }
    public JLabel getLogoutLabel() { return logoutLabel; }
}
