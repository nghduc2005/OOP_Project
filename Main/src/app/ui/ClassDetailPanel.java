package app.ui;

import app.Constant;
import app.dao.DatabaseConnection;
import app.dao.StudentDao;
import app.model.Classes;
import app.model.Student;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

public class ClassDetailPanel extends JPanel {
    JLabel titleLabel, groupLabel, creditLabel, totalStudentLabel;
    private MainPanel mainPanel;
    private int maximumStudent;
    TableComponent table;
    int classid;
    ClassDetailPanel(MainPanel mainPanel,int classid) {
        this.mainPanel = mainPanel;
        this.classid = classid;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        String query = "select * from classes where class_id="+classid;
        List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
        int credit = 0, subjectId = 0, maxStudent = 0;
        String subjectName = "";
        for (HashMap<String, Object> row : results) {
            subjectId= (int) row.get("subject_id");
            subjectName = row.get("subject_name").toString();
            credit = (int) row.get("credit"); // có thể NULL
            maxStudent = (int) row.get("maxnumberstudent");
            this.maximumStudent = maxStudent;
        }
        HeaderComponent headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Lịch học","Thông tin cá nhân",
                "Đổi" +
                " " +
                "mật" +
                " khẩu", "Đăng xuất",
                "Quay lại"},
                mainPanel);
        add(headerComponent);
        add(classDescriptionPanel(this,"Tên môn học", subjectName, credit, classid, maxStudent));
         table = new TableComponent(
                new String[]{"Tên đăng nhập", "Họ và tên", "Điểm chuyên cần", "Điểm bài tập", "Điểm giữa kì", "Điểm " +
                        "Cuối kì", "Tổng kết"},
                new int[]{300, 200,200, 200, 200}
        );
        String queryTable = "select * from students s join student_class sc on s.username = sc.username and sc" +
                ".class_id = " + classid;
        List<HashMap<String, Object>> studentList = DatabaseConnection.readTable(queryTable);
        for (HashMap<String, Object> row : studentList) {
            table.addRow(
                    String.valueOf(row.get("username")),
                    String.valueOf(row.get("fullname")),
                    String.valueOf(row.get("attendence")),
                    String.valueOf(row.get("assignment")),
                    String.valueOf(row.get("midterm")),
                    String.valueOf(row.get("final")),
                    String.format("%.2f",
                            (Double.parseDouble(String.valueOf(row.get("attendence"))) +
                                   Double.parseDouble(String.valueOf(row.get("assignment"))) +
                                   2* Double.parseDouble(String.valueOf(row.get("midterm"))) +
                                    6 * Double.parseDouble(String.valueOf(row.get("final"))))/10
                    )
            );
        }
        //nút thêm học sinh
        JTextField addStudentInput = new JTextField();
        addStudentInput.setColumns(20);
        addStudentInput.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        JButton addStudentButton = new JButton("Thêm học sinh");
        addStudentButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addStudentButton.setBackground(Color.WHITE);
        addStudentButton.setForeground(new Color(0x2C3E50));
        addStudentButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(5, 20, 5, 20)));
        addStudentButton.setFocusPainted(false);
        addStudentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JButton deleteStudentButton = new JButton("Xóa học sinh");
        deleteStudentButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteStudentButton.setBackground(Color.WHITE);
        deleteStudentButton.setForeground(new Color(0x2C3E50));
        deleteStudentButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(5, 20, 5, 20)));
        deleteStudentButton.setFocusPainted(false);
        deleteStudentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteStudentButton.addActionListener(e -> deleteStudent());

        JButton editStudentButton = new JButton("Sửa thông tin học sinh");
        editStudentButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        editStudentButton.setBackground(Color.WHITE);
        editStudentButton.setForeground(new Color(0x2C3E50));
        editStudentButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(5, 20, 5, 20)));
        editStudentButton.setFocusPainted(false);
        editStudentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editStudentButton.addActionListener(e -> editStudent());

        JPanel addStudentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        addStudentPanel.add(addStudentInput);
        addStudentPanel.add(addStudentButton);
        addStudentPanel.add(deleteStudentButton);
        addStudentPanel.add(editStudentButton);
        add(addStudentPanel);
        addStudentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Lấy nội dung từ input
                String studentUsername = addStudentInput.getText().trim();
                // Kiểm tra rỗng
                if (studentUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Vui lòng nhập tên đăng nhập của học sinh!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(!studentUsername.matches(Constant.STU_USERNAME_PATTERN)) {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this, "Vui lòng nhập tên đăng nhập với định dạng " +
                            "STUxxxxxx", "Thông báo",JOptionPane.WARNING_MESSAGE);
                }
                System.out.println(maximumStudent + " " + table.getTable().getRowCount());
                String username = addStudentInput.getText().trim();
                if(table.getTable().getRowCount()==maximumStudent) {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Số học sinh đạt tối đa!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(StudentDao.addStudentInClass(username,classid)) {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Thêm học sinh thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    addStudentInput.setText("");
                    mainPanel.reloaClassDetails(ClassDetailPanel.this, classid);
                    return;
                } else {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Thêm học sinh thất bại!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        });
//        deleteStudentButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // Lấy nội dung từ input
//                String studentUsername = addStudentInput.getText().trim();
//
//                // Kiểm tra rỗng
//                if (studentUsername.isEmpty()) {
//                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
//                            "Vui lòng nhập tên đăng nhập của học sinh!",
//                            "Thông báo",
//                            JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//                String username = addStudentInput.getText().trim();
//                if(StudentDao.deleteStudentInClass(username,classid)) {
//                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
//                            "Xóa học sinh thành công!",
//                            "Thông báo",
//                            JOptionPane.INFORMATION_MESSAGE);
//                    addStudentInput.setText("");
//                    mainPanel.reloaClassDetails(ClassDetailPanel.this, classid);
//                    return;
//                } else {
//                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
//                            "Xóa học sinh thất bại!",
//                            "Thông báo",
//                            JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//            }
//        });
//        editStudentButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                String studentUsername = addStudentInput.getText().trim();
//                if (studentUsername.isEmpty()) {
//                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
//                            "Vui lòng nhập tên đăng nhập của học sinh!",
//                            "Thông báo",
//                            JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//                EditStudent editStudent = new EditStudent(mainPanel);
//                // Tạo dialog chứa panel
//                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(ClassDetailPanel.this),
//                        "Sửa thông tin học sinh", // tiêu đề
//                        true);
//
//                dialog.setContentPane(editStudent);
//                dialog.pack();
//                dialog.setSize(600, 500);
//                dialog.setLocationRelativeTo(ClassDetailPanel.this);
//                dialog.setVisible(true);
//            }
//        });
        table.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = table.getTable().getSelectedRow() != -1;
                deleteStudentButton.setEnabled(hasSelection);
                editStudentButton.setEnabled(hasSelection);
            }
        });
        add(table);
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

        String username =  String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 0));
        if (username != null) {
            StudentDao.deleteStudentInClass(username, classid);
            mainPanel.reloaClassDetails(ClassDetailPanel.this, classid);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin học sinh!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editStudent() {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username =  String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 0));
        String attendence =  String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 2));
        String assignment = String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 3));
        String midterm = String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 4));
        String finalGrade =  String.valueOf( table.getTable().getModel().getValueAt(selectedRow, 5));
        if (username != null) {
            EditStudent editStudent = new EditStudent(mainPanel, username, classid, ClassDetailPanel.this, attendence
                    , assignment, midterm, finalGrade);
                // Tạo dialog chứa panel
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(ClassDetailPanel.this),
                        "Sửa thông tin học sinh", // tiêu đề
                        true);

                dialog.setContentPane(editStudent);
                dialog.pack();
                dialog.setSize(600, 500);
                dialog.setLocationRelativeTo(ClassDetailPanel.this);
                dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy học sinh cần sửa!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public JLabel createLabel(String title, String content) {
        JLabel label = new LabelComponent(title+": "+content, 15);
        return label;
    }
    public JPanel classDescriptionPanel(ClassDetailPanel detailPanel, String title, String content, int credit, int classid,
                                        int maxStudent) {
        JPanel classDescriptionPanel = new JPanel();
        
        JButton editclass = new JButton("Chỉnh sửa");
        editclass.setFont(new Font("Segoe UI", Font.BOLD, 13));
        editclass.setBackground(Color.WHITE);
        editclass.setForeground(new Color(0x2C3E50));
        editclass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        editclass.setFocusPainted(false);
        editclass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        JButton addStudent = new JButton("Thêm học sinh");
        classDescriptionPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(30, 200, 30, 200);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 0;
        classDescriptionPanel.add(createLabel("Tên môn", content),c);
        c.gridx = 1; c.gridy = 1;
        classDescriptionPanel.add(createLabel("Số tín chỉ", String.valueOf(credit)),c);
        c.gridx = 2; c.gridy = 0;
        classDescriptionPanel.add(createLabel("Số học sinh tối đa", String.valueOf(maxStudent)),c);
        c.gridx = 2; c.gridy = 1;
        classDescriptionPanel.add(createLabel("Nhóm", String.valueOf(classid)), c);
        // nút chỉnh sửa
        editclass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Tạo popup dialog chứa AddClass JPanel
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(ClassDetailPanel.this),
                        "Thêm học sinh",
                        true);

                EditClass ec = new EditClass(mainPanel,dialog, classid, detailPanel);
                dialog.setContentPane(ec);
                dialog.pack();
                dialog.setSize(500, 450);
                dialog.setLocationRelativeTo(ClassDetailPanel.this);
                dialog.setVisible(true);
            }

        });
        c.gridx = 3; c.gridy = 0;
        classDescriptionPanel.add(editclass, c);

//        classDescriptionPanel.add(addStudent);
        classDescriptionPanel.setMaximumSize(classDescriptionPanel.getPreferredSize());
        return classDescriptionPanel;
    }
}
