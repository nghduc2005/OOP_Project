package app.ui;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionPanel extends JPanel {
    JButton teacherButton, studentButton;
    MainPanel mainPanel;
    int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    int H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public RoleSelectionPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(null);

        JLabel title = new JLabel("You are : ");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(W/2-150, H/2-200, 300, 50);

        teacherButton = new JButton("Teacher");
        studentButton = new JButton("Student");

        teacherButton.setBounds(W/2-100, H/2-150, 200, 40);
        studentButton.setBounds(W/2-100, H/2-150+60, 200, 40);
        title.setForeground(Color.RED);
        // sự kiện bấm nút
        teacherButton.addActionListener(e -> mainPanel.show("Log_t"));
        studentButton.addActionListener(e -> mainPanel.show("Log_s"));

        add(title);
        add(teacherButton);
        add(studentButton);
    }
}
