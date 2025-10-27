package app.ui;

import app.dao.StudentDao;

import javax.swing.*;
import java.awt.*;

public class EditStudent extends JPanel {
    private JTextField studentIdField, fullNameField, birthdayField, phoneField, emailField;
    private JButton updateButton;
    private MainPanel mainPanel;

    public EditStudent(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Main panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Chỉnh sửa thông tin học sinh");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Label + ô nhập
        JLabel idLabel = new JLabel("Mã học sinh (Username):");
        studentIdField = new JTextField(20);
        studentIdField.setToolTipText("Nhập mã học sinh bạn muốn sửa");

        JLabel nameLabel = new JLabel("Họ tên đầy đủ:");
        fullNameField = new JTextField(20);

        JLabel birthdayLabel = new JLabel("Ngày sinh (yyyy-mm-dd):");
        birthdayField = new JTextField(20);
        birthdayField.setToolTipText("Định dạng YYYY-MM-DD, VD: 2000-03-15");

        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        gbc.gridy = 1; gbc.gridx = 0; formPanel.add(idLabel, gbc);
        gbc.gridx = 1; formPanel.add(studentIdField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(fullNameField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; formPanel.add(birthdayLabel, gbc);
        gbc.gridx = 1; formPanel.add(birthdayField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; formPanel.add(phoneField, gbc);

        gbc.gridy = 5; gbc.gridx = 0; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; formPanel.add(emailField, gbc);

        // nút update
        updateButton = new JButton("Cập nhật");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(new Color(66, 133, 244));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Event
        updateButton.addActionListener(e -> updateStudent());
    }

    private void updateStudent() {
        String id = studentIdField.getText().trim();
        String name = fullNameField.getText().trim();
        String birthday = birthdayField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã học sinh!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Gọi hàm
        boolean success = StudentDao.updateStudentByUsername(id, name, birthday, phone, email);

        if(success)
            JOptionPane.showMessageDialog(this, "Sửa thông tin học sinh thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, "Có lỗi khi sửa, hoặc mã học sinh không tồn tại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
    }
}