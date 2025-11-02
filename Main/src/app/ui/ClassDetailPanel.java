package app.ui;

import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClassDetailPanel extends JPanel {
    JLabel titleLabel, groupLabel, creditLabel, totalStudentLabel;
    private MainPanel mainPanel;
    ClassDetailPanel(MainPanel mainPanel,String subjectName,int credit, int classid ) {
        this.mainPanel = mainPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        HeaderComponent headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Chỉnh sửa lớp học này"},
                mainPanel);
        add(headerComponent);
        add(classDescriptionPanel("Tên môn học", subjectName, credit, classid));
        TableComponent table = new TableComponent(
                new String[]{"Họ và tên", "Điểm CC", "Điểm BT", "Điểm GK", "Điểm CK"},
                new int[]{300, 200,200, 200, 200}
        );
        add(table);
    }
    public JLabel createLabel(String title, String content) {
        JLabel label = new LabelComponent(title+": "+content, 15);
        return label;
    }
    public JPanel classDescriptionPanel(String title, String content, int credit, int classid) {
        JPanel classDescriptionPanel = new JPanel();
        JButton editclass = new JButton("Chỉnh sửa");

        classDescriptionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(30, 200, 30, 200);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 0;
        classDescriptionPanel.add(createLabel("Tên môn", content),c);
        c.gridx = 1; c.gridy = 1;
        classDescriptionPanel.add(createLabel("Số tín chỉ", String.valueOf(credit)),c);
        // nút chỉnh sửa
        c.gridx = 1; c.gridy = 2;
        editclass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tạo popup dialog chứa AddClass JPanel
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(ClassDetailPanel.this),
                        "Sửa lớp",
                        true);

                EditClass ec = new EditClass(mainPanel,dialog, classid);
                dialog.setContentPane(ec);
                dialog.pack();
                dialog.setSize(500, 450);
                dialog.setLocationRelativeTo(ClassDetailPanel.this);
                dialog.setVisible(true);
            }

        });
        classDescriptionPanel.add(editclass);
        classDescriptionPanel.setMaximumSize(classDescriptionPanel.getPreferredSize());
        return classDescriptionPanel;
    }
}
