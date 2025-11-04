package app.ui;

import app.model.Student;
import app.model.Subject;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;
import app.dao.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class DashboardPanel extends JPanel {
    JPanel header;
    HeaderComponent headerComponent;
    CardSubjectTeacher cardSubjectTeachers;
    LabelComponent titleLabel;
    MainPanel mainPanel;
    JButton addStudent, deleteStudent;
    //Trang chủ
    public DashboardPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        headerComponent = new HeaderComponent(new String[]{ "Trang chủ", "Lịch học", "Thông tin cá nhân", "Đổi mật khẩu","Đăng xuất","Quay lại"},
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
        addStudent = new JButton("Thêm học sinh");
        addStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tạo popup dialog chứa AddClass JPanel
                java.awt.Window w = SwingUtilities.getWindowAncestor(DashboardPanel.this);
                JFrame owner = (w instanceof JFrame) ? (JFrame) w : null; // an toàn nếu không phải JFrame
                AddStudentForm addStudentForm = new AddStudentForm(owner);
                Student s= addStudentForm.showAddStudentDialog(owner);
                if (s != null) {
                    // reload bảng hoặc addRow như bạn đang làmg
                    mainPanel.reloadDashboard();
                }
//                dialog.setContentPane(ec);
//                dialog.pack();
//                dialog.setSize(500, 450);
//                dialog.setLocationRelativeTo(ClassDetailPanel.this);
//                dialog.setVisible(true);
            }
        });
        deleteStudent = new JButton("Xóa học sinh");

        centerPanel.add(addStudent);
        centerPanel.add(combinePanel());

        return centerPanel;
    }
    public JPanel combinePanel() {
        JPanel combinePanel = new JPanel();
        combinePanel.setLayout(new BorderLayout());
        JScrollPane scrollPanel = new JScrollPane(cardListPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        combinePanel.add(scrollPanel, BorderLayout.CENTER);
//        combinePanel.add(tableStudent(), BorderLayout.CENTER);
        return combinePanel;
    }
    public JPanel cardListPanel() {
        JPanel cardListPanel = new JPanel();
        cardListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // căn giữa + spacing
        cardListPanel.setOpaque(false);
        String query = "SELECT * from classes where teacher_id = 1 order by class_id";
        List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
        for (HashMap<String, Object> row : results) {
            int classId = Integer.parseInt(row.get("class_id").toString());
            int subjectId = (int) row.get("subject_id");
            String subjectName = row.get("subject_name").toString();
            int credit =(int) row.get("credit"); // có thể NULL
            int maxStudent = (int) row.get("maxnumberstudent");
            CardSubjectTeacher card = new CardSubjectTeacher(new Subject(subjectId , subjectName, (int) row.get(
                    "credit")), Integer.toString(classId));
            card.setName(String.format("%s", classId));
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String nameofclass = "ClassDetailPanel_"+String.valueOf(classId);
                    boolean isHascard = mainPanel.hasCard(nameofclass);
                    if( !isHascard ){
                        ClassDetailPanel c = new ClassDetailPanel(mainPanel, classId);
                        mainPanel.add(c, nameofclass);
                    }
                    mainPanel.show(nameofclass);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            });
            cardListPanel.add(card);
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
        int height = 300 * results.size()/6;
        cardListPanel.setPreferredSize(new Dimension(600, height));
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
