package app.ui;

import app.model.Schedule;
import app.model.Student;
import app.model.Subject;
import app.service.ScheduleService;
import app.ui.component.ButtonComponent;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;
import app.dao.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class DashboardPanel extends JPanel {
    JPanel header, centerPanel;
    HeaderComponent headerComponent;
    CardSubjectTeacher cardSubjectTeachers;
    LabelComponent titleLabel;
    MainPanel mainPanel;
    ButtonComponent addStudent, deleteStudent;
    TableComponent table;
    //Trang chủ
    public DashboardPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        headerComponent = new HeaderComponent(new String[]{ "Trang chủ", "Lịch học", "Thông tin cá nhân", "Đổi mật khẩu","Đăng xuất","Quay lại"},
                mainPanel);
        add(headerComponent, BorderLayout.NORTH);
        centerPanel = centerPanel();
        add(centerPanel, BorderLayout.CENTER);
    }

    public JPanel centerPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        centerPanel.setBackground(new Color(245, 247, 250)); // Màu nền hiện đại
        // TitleLabel
        titleLabel = new LabelComponent("Trang chủ", 50);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);
        //Set up thêm học sinh
        addStudent = new ButtonComponent("Thêm học sinh");
        addStudent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addStudent.setBackground(new Color(52, 152, 219));
        addStudent.setForeground(Color.white);
        addStudent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        addStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        addStudent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tạo popup dialog chứa AddClass JPanel
                java.awt.Window w = SwingUtilities.getWindowAncestor(DashboardPanel.this);
                JFrame owner = (w instanceof JFrame) ? (JFrame) w : null;
                AddStudentForm addStudentForm = new AddStudentForm(owner);
                Student s= addStudentForm.showAddStudentDialog(owner);
                if (s != null) { //Reload nếu có dữ liệu trả về
                    mainPanel.reloadDashboard();
                }
            }
        });
        //Xóa học sinh
        deleteStudent = new ButtonComponent("Xóa học sinh");
        deleteStudent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteStudent.setBackground(new Color(52, 152, 219));
        deleteStudent.setForeground(Color.white);
        deleteStudent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        deleteStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteStudent.addActionListener(e -> deleteStudent());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addStudent);
        buttonPanel.add(deleteStudent);
        centerPanel.add(buttonPanel);
        centerPanel.add(combinePanel());

        return centerPanel;
    }

    public JPanel combinePanel() {
        JPanel combinePanel = new JPanel();
        combinePanel.setLayout(new BorderLayout());
        //Hiển thị dạng scroll bar dựa trên kích thươc
        JScrollPane scrollPanel = new JScrollPane(cardListPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(22);
        combinePanel.add(scrollPanel, BorderLayout.CENTER);
        combinePanel.add(tableStudent(), BorderLayout.SOUTH);
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
        addclass.setFont(new Font("Segoe UI", Font.BOLD, 50));
        addclass.setBackground(Color.WHITE);
        addclass.setForeground(new Color(0x7F8C8D));
        addclass.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 2));
        addclass.setPreferredSize(new Dimension(100, 100));
        addclass.setFocusPainted(false);
        addclass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

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
    private void deleteStudent() {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn học sinh cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username =  String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 1));
        System.out.println("username = " + username);
        if (username != null) {
            StudentDao.deleteStudent(username);
            mainPanel.reloadDashboard();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin học sinh!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public TableComponent tableStudent() {
         table = new TableComponent(
                new String[]{"STT", "Tên đăng nhập","Họ và tên", "Email", "Số điện thoại"},
                new int[]{50, 200, 200, 200, 100, 100, 100}
        );
        String query = "SELECT * from students";
        int i = 1;
        List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
        for (HashMap<String, Object> row : results) {
            table.addRow(new Object[]{i++, row.get("username"), row.get("fullname"), row.get("email"),
                    row.get("phone")});
        }
        table.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getTable().getSelectedRow() != -1;
                deleteStudent.setEnabled(hasSelection);
            }
        });
        return table;
    }
}
