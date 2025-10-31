package app.ui;

import app.dao.ClassDao;
import app.model.Classes;
import app.ui.component.HeaderComponent;

import javax.swing.*;
import java.awt.*;

public class EditClass extends JPanel {
    private JTextField classIdField, classNameField, studentCountField, maxStudentField;
    private JButton updateButton;
    private MainPanel mainPanel;

    public EditClass(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ====== HEADER ======
        HeaderComponent headerComponent = new HeaderComponent(
                new String[]{"Trang chủ", "Chỉnh sửa lớp học", "Quay lại"},
                mainPanel
        );
        add(headerComponent, BorderLayout.NORTH);

        // ====== PANEL CHÍNH ======
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ====== TIÊU ĐỀ ======
        JLabel titleLabel = new JLabel("Chỉnh sửa thông tin lớp học");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // ====== NHÃN + Ô NHẬP ======
        JLabel idLabel = new JLabel("Mã lớp học:");
        classIdField = new JTextField(20);

        JLabel nameLabel = new JLabel("Tên môn học:");
        classNameField = new JTextField(20);

        JLabel studentCountLabel = new JLabel("Số học sinh hiện tại:");
        studentCountField = new JTextField(20);

        JLabel maxStudentLabel = new JLabel("Số học sinh tối đa:");
        maxStudentField = new JTextField(20);

        gbc.gridy = 1; gbc.gridx = 0; formPanel.add(idLabel, gbc);
        gbc.gridx = 1; formPanel.add(classIdField, gbc);

        gbc.gridy = 2; gbc.gridx = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(classNameField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; formPanel.add(studentCountLabel, gbc);
        gbc.gridx = 1; formPanel.add(studentCountField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; formPanel.add(maxStudentLabel, gbc);
        gbc.gridx = 1; formPanel.add(maxStudentField, gbc);

        // ====== NÚT CẬP NHẬT ======
        updateButton = new JButton("Cập nhật");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(new Color(66, 133, 244));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ====== SỰ KIỆN ======
        updateButton.addActionListener(e -> updateClass());
    }

    private void updateClass() {
        String id = classIdField.getText().trim();
        String name = classNameField.getText().trim();
        String count = studentCountField.getText().trim();
        String max = maxStudentField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || count.isEmpty() || max.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Classes cl = new Classes(Integer.parseInt(id), Integer.parseInt(count), name, Integer.parseInt(max));
            boolean success = ClassDao.updateClass(cl);
            if(success)
                JOptionPane.showMessageDialog(this, "Sửa lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Có lỗi khi sửa lớp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
