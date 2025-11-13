package app.ui;

import app.dao.DatabaseConnection;
import app.model.*;
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

import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;


public class DashboardPanel extends JPanel {
    JPanel header;
    HeaderComponent headerComponent;
    LabelComponent titleLabel;
    MainPanel mainPanel;
    ButtonComponent addStudent, deleteStudent;

    TableComponent table;
    JPanel cardListPanel; // Panel chứa các card
    JButton addclass;      // Nút "Thêm lớp"

    public DashboardPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        headerComponent = new HeaderComponent(new String[]{
                "Trang chủ",
                "Lịch học",
                "Thông tin cá nhân",
                "Đổi mật khẩu",
                "Đăng xuất",
                "Quay lại"},
                mainPanel);
        add(headerComponent, BorderLayout.NORTH);
        JPanel centerPanel = centerPanel();

        add(centerPanel, BorderLayout.CENTER);

        loadDataAsync();
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
        addStudent = new ButtonComponent("Thêm học sinh");
        addStudent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addStudent.setBackground(new Color(52, 152, 219));
        addStudent.setForeground(Color.white);
        addStudent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
//        addStudent.setFocusPainted(false);
        addStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
            }
        });
        deleteStudent = new ButtonComponent("Xóa học sinh");
        deleteStudent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteStudent.setBackground(new Color(52, 152, 219));
        deleteStudent.setForeground(Color.white);
        deleteStudent.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
//        addStudent.setFocusPainted(false);
        deleteStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteStudent.addActionListener(e -> deleteStudent());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addStudent);
        buttonPanel.add(deleteStudent);
        centerPanel.add(buttonPanel);
//        centerPanel.add(deleteStudent);
        centerPanel.add(combinePanel());

        return centerPanel;
    }
    public JPanel combinePanel() {
        JPanel combinePanel = new JPanel();
        combinePanel.setLayout(new BorderLayout());
        JScrollPane scrollPanel = new JScrollPane(cardListPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(22);
        combinePanel.add(scrollPanel, BorderLayout.CENTER);
        combinePanel.add(tableStudent(), BorderLayout.SOUTH);
        return combinePanel;
    }

    public JPanel cardListPanel() {
        cardListPanel = new JPanel(); // Gán vào biến toàn cục
        cardListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // căn giữa + spacing
        cardListPanel.setOpaque(false);

        // tạo nút "+"
        addclass = new JButton("+");
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

        cardListPanel.setPreferredSize(new Dimension(600, 300)); // Đặt chiều cao tạm thời
        cardListPanel.setMaximumSize(cardListPanel.getPreferredSize());
        cardListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return cardListPanel;
    }

    private void deleteStudent() {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn học sinh cần xóa!", // Sửa thông báo
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

    // tạo table
    public TableComponent tableStudent() {
        table = new TableComponent(
                new String[]{"STT", "Tên đăng nhập","Họ và tên", "Email", "Số điện thoại"},
                new int[]{50, 200, 200, 200, 100, 100, 100}
        );

        table.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getTable().getSelectedRow() != -1;
                deleteStudent.setEnabled(hasSelection);
            }
        });
        return table;
    }


    private static class DashboardData {
        final List<HashMap<String, Object>> classList;
        final List<HashMap<String, Object>> studentList;

        DashboardData(List<HashMap<String, Object>> classList, List<HashMap<String, Object>> studentList) {
            this.classList = classList;
            this.studentList = studentList;
        }
    }

    // tải data
    private void loadDataAsync() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        SwingWorker<DashboardData, Void> worker = new SwingWorker<>() {

            @Override
            protected DashboardData doInBackground() throws Exception {
                // Chạy cả 2 query trên luồng nền
                String classQuery = "SELECT * from classes where teacher_id = 1 order by class_id";
                List<HashMap<String, Object>> classResults = DatabaseConnection.readTable(classQuery);

                String studentQuery = "SELECT * from students";
                List<HashMap<String, Object>> studentResults = DatabaseConnection.readTable(studentQuery);

                return new DashboardData(classResults, studentResults);
            }

            @Override
            protected void done() {
                try {
                    DashboardData data = get();
                    cardListPanel.removeAll();
                    cardListPanel.add(addclass);

                    if (data.classList != null) {
                        for (HashMap<String, Object> row : data.classList) {
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

                        int height = 300 * data.classList.size()/6;
                        cardListPanel.setPreferredSize(new Dimension(600, height));
                        cardListPanel.setMaximumSize(cardListPanel.getPreferredSize());
                    }

                    DefaultTableModel model = (DefaultTableModel) table.getTable().getModel();
                    model.setRowCount(0); // Xóa data cũ

                    if (data.studentList != null) {
                        int i = 1;
                        for (HashMap<String, Object> row : data.studentList) {
                            table.addRow(new Object[]{i++, row.get("username"), row.get("fullname"), row.get("email"),
                                    row.get("phone")});
                        }
                    }

                    // Vẽ lại UI
                    cardListPanel.revalidate();
                    cardListPanel.repaint();
                    table.revalidate();
                    table.repaint();

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(DashboardPanel.this, "Lỗi khi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };
        worker.execute();
    }
}