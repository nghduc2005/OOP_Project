package app.ui;

import app.dao.ClassDao;
import app.dao.DatabaseConnection;
import app.model.Classes;
import app.ui.component.ButtonComponent;
import app.ui.component.TextFieldComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;

public class AddClass extends JPanel {
    private JTextField classIdInput, totalStudentInput, maxStudentInput, subjectNameInput;
    private JComboBox<String> subjectCombox;
    private JButton addButton;
    private MainPanel mainPanel;
    private JDialog dialog;
    public AddClass(MainPanel mainPanel, JDialog dialog) {
        this.mainPanel = mainPanel;
        this.dialog =dialog;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== TIÊU ĐỀ =====
        JLabel titleLabel = new JLabel("Thêm lớp học mới");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));

        // ===== CÁC NHÃN VÀ Ô NHẬP =====
        JLabel classIdLabel = new JLabel("Mã lớp học:");
        JLabel totalStudentLabel = new JLabel("Số học sinh hiện tại:");
        JLabel maxStudentLabel = new JLabel("Số học sinh tối đa:");
        JLabel subjectNameLabel = new JLabel("Tên môn học:");

        classIdInput = new TextFieldComponent(15);
        totalStudentInput = new TextFieldComponent(15);
        maxStudentInput = new TextFieldComponent(15);

        classIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalStudentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        maxStudentLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subjectNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Query subjects from DB
        subjectCombox = new JComboBox<>();

        String subjectQuery = "SELECT subject_id, name FROM subjects";
        List<HashMap<String, Object>> subjectData = DatabaseConnection.readTable(subjectQuery);
        for (HashMap<String, Object> row : subjectData) {
            String name = row.get("name").toString();
            int id = Integer.parseInt(row.get("subject_id").toString());
            subjectCombox.addItem(name);
        }

        // ===== NÚT =====
        addButton = new ButtonComponent("Thêm lớp");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // ===== PANEL FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(classIdLabel, gbc);
        gbc.gridx = 1; formPanel.add(classIdInput, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(totalStudentLabel, gbc);
        gbc.gridx = 1; formPanel.add(totalStudentInput, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(maxStudentLabel, gbc);
        gbc.gridx = 1; formPanel.add(maxStudentInput, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(subjectNameLabel, gbc);
        gbc.gridx = 1; formPanel.add(subjectCombox, gbc);

        // ===== GỘP TẤT CẢ VÀO MỘT CONTAINER =====
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(titleLabel);
        containerPanel.add(formPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        containerPanel.add(addButton);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(Color.WHITE);
        outerPanel.add(containerPanel);

        add(outerPanel, BorderLayout.CENTER);

        // ===== SỰ KIỆN =====
        addButton.addActionListener(e -> handleAddClass());
    }

    private void handleAddClass() {
        String classId = classIdInput.getText().trim();
        String totalStudent = totalStudentInput.getText().trim();
        String maxStudent = maxStudentInput.getText().trim();
        String subjectName = subjectNameInput.getText().trim();

        if (classId.isEmpty() || totalStudent.isEmpty() || maxStudent.isEmpty() || subjectName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin lớp học!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Classes cl = new Classes(Integer.parseInt(classId), Integer.parseInt(totalStudent), subjectName, Integer.parseInt(maxStudent));
            boolean success = ClassDao.CreateClass(cl);
            if(success) {
                JOptionPane.showMessageDialog(this, "Thêm lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                dialog.dispose(); // đóng popup

                // Reload dashboard panel
                mainPanel.show("Dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm lớp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
