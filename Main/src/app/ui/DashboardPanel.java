package app.ui;

import app.model.Subject;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    JPanel header;
    HeaderComponent headerComponent;
    CardSubjectTeacher cardSubjectTeachers;
    LabelComponent titleLabel;
    public DashboardPanel(MainPanel mainPanel) {
        setLayout(new BorderLayout());
        headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Thông tin cá nhân"});
        add(headerComponent, BorderLayout.NORTH);
        JPanel centerPanel = centerPanel();
        add(centerPanel, BorderLayout.CENTER);
    }
    public JPanel centerPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        // TitleLabel
        titleLabel = new LabelComponent("Trang chủ", 50);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);
//        centerPanel.add(cardListPanel());
//        centerPanel.add(testPanel());
        centerPanel.add(combinePanel());
        return centerPanel;
    }
    public JPanel combinePanel() {
        JPanel combinePanel = new JPanel();
        combinePanel.setLayout(new BoxLayout(combinePanel, BoxLayout.Y_AXIS));
        combinePanel.add(cardListPanel());
        combinePanel.add(testPanel());
        return combinePanel;
    }
    public JPanel cardListPanel() {
        JPanel cardListPanel = new JPanel();
        cardListPanel.setLayout(new GridBagLayout());
        cardListPanel.setBackground(Color.GREEN);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 4; c.gridy = 0;
        for(int i = 1; i <= 7; i++) {
            CardSubjectTeacher cardSubjectTeacher = new CardSubjectTeacher(new Subject("1", "Giải tích", 3), "3");
            cardListPanel.add(cardSubjectTeacher, c);
            if(i%4==0) {
                c.gridy++;
                c.gridx = 4;
            }
            c.gridx--;
        }
        cardListPanel.setMaximumSize(cardListPanel.getPreferredSize());
        return cardListPanel;
    }
    public JPanel testPanel() {
        JPanel testPanel = new JPanel();
        testPanel.setSize(200, 200);
        testPanel.setBackground(Color.RED);
        return testPanel;
    }
}
