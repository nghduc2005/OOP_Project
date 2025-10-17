package app.ui.component;

import app.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HeaderComponent extends JPanel {
    MainPanel mainPanel;
    public HeaderComponent(String[] navItem, MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(150, 150, 150)); // nền xám

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
                            mainPanel.show("dashboard");
                            break;
                        case "Quay lại":
                            mainPanel.back();
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(Color.ORANGE);
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
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }
}
