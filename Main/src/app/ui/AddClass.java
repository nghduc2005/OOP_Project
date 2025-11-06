package app.ui;

import app.dao.ClassDao;
import app.dao.DatabaseConnection;
import app.dto.request.SubjectItem;
import app.model.Classes;
import app.ui.component.ButtonComponent;
import app.ui.component.TextFieldComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;

public class AddClass extends JPanel {
    private JTextField classIdInput, totalStudentInput, maxStudentInput, creditInput;
    private JComboBox<SubjectItem> subjectCombox;

    private JButton addButton;
    private MainPanel mainPanel;
    private JDialog dialog;
    public AddClass(MainPanel mainPanel, JDialog dialog) {
        this.mainPanel = mainPanel;
        this.dialog =dialog;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // Màu nền xanh nhạt hiện đại

        // ===== TIÊU ĐỀ =====
        JLabel titleLabel = new JLabel("Thêm lớp học mới");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));

        // ===== CÁC NHÃN VÀ Ô NHẬP =====
        JLabel classIdLabel = new JLabel("Mã lớp học:");
        JLabel totalStudentLabel = new JLabel("Số học sinh hiện tại:");
        JLabel maxStudentLabel = new JLabel("Số học sinh tối đa:");
        JLabel subjectNameLabel = new JLabel("Tên môn học:");
        JLabel creditLabel = new JLabel("Số tín chỉ:");

        classIdInput = new TextFieldComponent(15);
        totalStudentInput = new TextFieldComponent(15);
        maxStudentInput = new TextFieldComponent(15);
        creditInput = new TextFieldComponent(15);

        classIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        totalStudentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        maxStudentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subjectNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        creditLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        classIdLabel.setForeground(new Color(44, 62, 80));
        totalStudentLabel.setForeground(new Color(44, 62, 80));
        maxStudentLabel.setForeground(new Color(44, 62, 80));
        subjectNameLabel.setForeground(new Color(44, 62, 80));
        creditLabel.setForeground(new Color(44, 62, 80));

        // Query subjects from DB
        subjectCombox = new JComboBox<>();

        String subjectQuery = "SELECT subject_id, name FROM subjects";
        List<HashMap<String, Object>> subjectData = DatabaseConnection.readTable(subjectQuery);
        for (HashMap<String, Object> row : subjectData) {
            String name = row.get("name").toString();
            int id = Integer.parseInt(row.get("subject_id").toString());
            subjectCombox.addItem(new SubjectItem(id, name));
        }

        // ===== NÚT =====
        addButton = new ButtonComponent("Thêm lớp");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // ===== PANEL FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

//        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(classIdLabel, gbc);
//        gbc.gridx = 1; formPanel.add(classIdInput, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(creditLabel, gbc);
        gbc.gridx = 1; formPanel.add(creditInput, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(maxStudentLabel, gbc);
        gbc.gridx = 1; formPanel.add(maxStudentInput, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(subjectNameLabel, gbc);
        gbc.gridx = 1; formPanel.add(subjectCombox, gbc);

        // ===== GỘP TẤT CẢ VÀO MỘT CONTAINER =====
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(new Color(245, 247, 250));
        containerPanel.add(titleLabel);
        containerPanel.add(formPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        containerPanel.add(addButton);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(new Color(245, 247, 250));
        outerPanel.add(containerPanel);

        add(outerPanel, BorderLayout.CENTER);

        // ===== SỰ KIỆN =====
        addButton.addActionListener(e -> handleAddClass());
    }

    private void handleAddClass() {
        String classId = classIdInput.getText().trim();
        String totalStudent = totalStudentInput.getText().trim();
        String maxStudent = maxStudentInput.getText().trim();
        String credit = creditInput.getText().trim();
        SubjectItem subjectItem = (SubjectItem) subjectCombox.getSelectedItem();
        String subjectId =Integer.toString(subjectItem.getId());
        String subjectName =subjectItem.getName();
        System.out.println(maxStudent + " "+ credit+" "+ subjectName +" "+ subjectId);
        if (maxStudent.isEmpty() || subjectName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin lớp học!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Classes cl = new Classes(subjectName, Integer.parseInt(maxStudent), Integer.parseInt(subjectId),
                    Integer.parseInt(credit));
            boolean success = ClassDao.CreateClass(cl);
            if(success) {
                JOptionPane.showMessageDialog(this, "Thêm lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                dialog.dispose(); // đóng popup

                // Reload dashboard panel
                mainPanel.reloadDashboard();

            } else {
                JOptionPane.showMessageDialog(this, "Thêm lớp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
