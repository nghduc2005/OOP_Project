package app.ui;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {
    JButton teacherButton, studentButton;
    MainPanel mainPanel;

    public RoleSelectionPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(null);

        JLabel title = new JLabel("You are : ");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(620, 150, 300, 50);

        teacherButton = new JButton("Teacher");
        studentButton = new JButton("Student");

        teacherButton.setBounds(650, 220, 200, 40);
        studentButton.setBounds(650, 280, 200, 40);

        // sự kiện bấm nút
        teacherButton.addActionListener(e -> mainPanel.show("Log_t"));
        studentButton.addActionListener(e -> mainPanel.show("Log_s"));

        add(title);
        add(teacherButton);
        add(studentButton);
    }
}
