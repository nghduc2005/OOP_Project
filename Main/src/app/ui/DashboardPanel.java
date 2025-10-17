package app.ui;

import app.model.Subject;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardPanel extends JPanel {
    JPanel header;
    HeaderComponent headerComponent;
    CardSubjectTeacher cardSubjectTeachers;
    LabelComponent titleLabel;
    MainPanel mainPanel;
    public DashboardPanel(MainPanel mainPanel) {
        this.mainPanel =mainPanel;
        setLayout(new BorderLayout());
        headerComponent = new HeaderComponent(new String[]{"Đăng xuất", "Trang chủ", "Thông tin cá nhân", "Quay lại"},
                mainPanel);
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
        centerPanel.add(combinePanel());

        return centerPanel;
    }
    public JPanel combinePanel() {
        JPanel combinePanel = new JPanel();
        combinePanel.setLayout(new BorderLayout());
        combinePanel.add(cardListPanel(), BorderLayout.NORTH);
        combinePanel.add(tableStudent(), BorderLayout.CENTER);
        return combinePanel;
    }
    public JPanel cardListPanel() {
        JPanel cardListPanel = new JPanel();
        cardListPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 4; c.gridy = 0;
        for(int i = 1; i <= 7; i++) {
            CardSubjectTeacher cardSubjectTeacher = new CardSubjectTeacher(new Subject("1", "Giải tích", 3), "3");
            cardSubjectTeacher.setName(String.format("%s", i));
            cardListPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mainPanel.show("ClassDetail");
                }
            });
            cardListPanel.add(cardSubjectTeacher, c);
            if(i%4==0) {
                c.gridy++;
                c.gridx = 4;
            }
            c.gridx--;
        }
        cardListPanel.setMaximumSize(cardListPanel.getPreferredSize());
        cardListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return cardListPanel;
    }
    public TableComponent tableStudent() {
        TableComponent table = new TableComponent(
                new String[]{"STT", "Mã sinh viên", "Họ và tên", "Điểm CC", "Điểm BT", "Điểm GK", "Điểm CK"},
                new int[]{50, 200, 200, 200, 100, 100, 100}
        );
//        tablePanel.add(table);
        return table;
    }


}
