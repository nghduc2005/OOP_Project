package app.ui;

import app.dao.StudentDao;
import app.ui.component.ButtonComponent;
import app.ui.component.TextFieldComponent;

import javax.swing.*;
import java.awt.*;

public class DeleteStudent extends JPanel {
    private JTextField studentIdInput;
    private JButton deleteButton;
    private MainPanel mainPanel;

    public DeleteStudent(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));

        // Label + ô nhập
        JLabel studentIdLabel = new JLabel("Nhập mã học sinh (username) cần xóa:");
        studentIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        studentIdLabel.setForeground(new Color(0x2C3E50));

        studentIdInput = new TextFieldComponent(15);

        // nút
        deleteButton = new ButtonComponent("Xóa học sinh");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(studentIdLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(studentIdInput, gbc);

        // container
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(formPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPanel.add(deleteButton);

        // Panel (dùng để căn)
        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(Color.WHITE);
        outerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        outerPanel.add(containerPanel);

        add(outerPanel, BorderLayout.CENTER);

        // event
        deleteButton.addActionListener(e -> handleDeleteStudent());
    }

    private void handleDeleteStudent() {
        String studentId = studentIdInput.getText().trim();

        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập mã học sinh cần xóa!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean success = StudentDao.deleteStudent(studentId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa học sinh thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            studentIdInput.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi khi xóa, hoặc mã học sinh không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}