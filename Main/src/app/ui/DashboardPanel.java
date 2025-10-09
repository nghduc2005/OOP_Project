package app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPanel extends JPanel {
    public DashboardPanel(MainPanel mainPanel) {
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.setBounds(10, 10, 100, 30);
        dashboardButton.addActionListener(new  ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.show("login");
            }
        });
        add(dashboardButton);
    }
}
