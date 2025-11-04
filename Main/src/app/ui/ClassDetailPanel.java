package app.ui;

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
    TableComponent table;
    ClassDetailPanel(MainPanel mainPanel,int classid) {
        this.mainPanel = mainPanel;
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
        }
        HeaderComponent headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Thông tin cá nhân", "Đổi mật" +
                " khẩu", "Đăng xuất",
                "Quay lại"},
                mainPanel);
        add(headerComponent);
        add(classDescriptionPanel(this,"Tên môn học", subjectName, credit, classid, maxStudent));
         table = new TableComponent(
                new String[]{"Tên đăng nhập", "Họ và tên", "Điểm chuyên cần", "Điểm bài tập", "Điểm giữa kì", "Điểm " +
                        "Cuối kì"},
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
                    String.valueOf(row.get("final"))
            );
        }
        //nút thêm học sinh
        JTextField addStudentInput = new JTextField();
        addStudentInput.setColumns(20);
        JButton addStudentButton = new JButton("Thêm học sinh");
        JButton deleteStudentButton = new JButton("Xóa học sinh");
        JButton editStudentButton = new JButton("Sửa thông tin học sinh");
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
                String username = addStudentInput.getText().trim();
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
        deleteStudentButton.addMouseListener(new MouseAdapter() {
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
                String username = addStudentInput.getText().trim();
                if(StudentDao.deleteStudentInClass(username,classid)) {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Xóa học sinh thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    addStudentInput.setText("");
                    mainPanel.reloaClassDetails(ClassDetailPanel.this, classid);
                    return;
                } else {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Xóa học sinh thất bại!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        });
        editStudentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String studentUsername = addStudentInput.getText().trim();
                if (studentUsername.isEmpty()) {
                    JOptionPane.showMessageDialog(ClassDetailPanel.this,
                            "Vui lòng nhập tên đăng nhập của học sinh!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditStudent editStudent = new EditStudent(mainPanel);
                // Tạo dialog chứa panel
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(ClassDetailPanel.this),
                        "Sửa thông tin học sinh", // tiêu đề
                        true);

                dialog.setContentPane(editStudent);
                dialog.pack();
                dialog.setSize(600, 500);
                dialog.setLocationRelativeTo(ClassDetailPanel.this);
                dialog.setVisible(true);
            }
        });
        add(table);
    }
    public JLabel createLabel(String title, String content) {
        JLabel label = new LabelComponent(title+": "+content, 15);
        return label;
    }
    public JPanel classDescriptionPanel(ClassDetailPanel detailPanel, String title, String content, int credit, int classid,
                                        int maxStudent) {
        JPanel classDescriptionPanel = new JPanel();
        JButton editclass = new JButton("Chỉnh sửa");
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
