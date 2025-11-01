package app.ui;

import app.model.Subject;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;
import app.dao.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardPanel extends JPanel {
    JPanel header;
    HeaderComponent headerComponent;
    CardSubjectTeacher cardSubjectTeachers;
    LabelComponent titleLabel;
    MainPanel mainPanel;
    public DashboardPanel(MainPanel mainPanel) {
        this.mainPanel =mainPanel;
        setLayout(new BorderLayout());
        headerComponent = new HeaderComponent(new String[]{ "Trang chủ", "Lịch học", "Thông tin cá nhân","Chỉnh sửa lớp học", "Đổi mật khẩu","Đăng xuất","Quay lại"},
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
        combinePanel.add(cardListPanel(), BorderLayout.CENTER);
//        combinePanel.add(tableStudent(), BorderLayout.CENTER);
        return combinePanel;
    }
    public JPanel cardListPanel() {
        JPanel cardListPanel = new JPanel();
        cardListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // căn giữa + spacing
        cardListPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = 4; c.gridy = 0;
        String query = "SELECT * from classes where teacher_id = 1";
        List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
        int i=1;
        for (HashMap<String, Object> row : results) {
            int classId = Integer.parseInt(row.get("class_id").toString());
            int subjectId = (int) row.get("subject_id");
            String subjectName = row.get("subject_name").toString();
            int credit =(int) row.get("credit"); // có thể NULL
            CardSubjectTeacher cardSubjectTeacher = new CardSubjectTeacher(new Subject(subjectId , subjectName, (int) row.get("credit")), Integer.toString(i));
            cardSubjectTeacher.setName(String.format("%s", i));
            cardListPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    mainPanel.show("ClassDetail");
                }
            });
            cardListPanel.add(cardSubjectTeacher);

            i++;
        }
        JButton addclass = new JButton("+");
        addclass.setFont(new Font("Arial", Font.BOLD, 50));
        addclass.setBackground(new Color(150,150,150));
        addclass.setPreferredSize(new Dimension(100, 100));
        addclass.setForeground(Color.WHITE);
        cardListPanel.add(addclass);
        addclass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tạo popup dialog chứa AddClass JPanel
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(DashboardPanel.this),
                        "Thêm lớp",
                        true);

                AddClass addClassPanel = new AddClass(mainPanel, dialog);

                dialog.setContentPane(addClassPanel);
                dialog.pack();
                dialog.setSize(500, 450);
                dialog.setLocationRelativeTo(DashboardPanel.this);
                dialog.setVisible(true);
            }
        });
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
