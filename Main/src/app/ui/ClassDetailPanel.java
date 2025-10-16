package app.ui;

import app.ui.component.LabelComponent;

import javax.swing.*;
import java.awt.*;

public class ClassDetailPanel extends JPanel {
    JLabel titleLabel, groupLabel, creditLabel, totalStudentLabel;
    ClassDetailPanel(MainPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(classDescriptionPanel("Tên môn học", "Vật lý"));
    }
    public JLabel createLabel(String title, String content) {
        JLabel label = new LabelComponent(title+": "+content, 15);
        return label;
    }
    public JPanel classDescriptionPanel(String title, String content) {
        JPanel classDescriptionPanel = new JPanel();
        classDescriptionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(30, 200, 30, 200);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 0;
        for(int i = 1; i<=4; i++) {
            classDescriptionPanel.add(createLabel("Tên môn", "Vật lý"),c);
            if(i%2==0) {
                c.gridx = 1;
                c.gridy++;
            }
            c.gridx--;
        }
        classDescriptionPanel.setMaximumSize(classDescriptionPanel.getPreferredSize());
        return classDescriptionPanel;
    }
}
