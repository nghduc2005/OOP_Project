package app.ui;

import app.dao.ClassDao;
import app.ui.component.HeaderComponent;
import app.ui.component.ButtonComponent;
import app.ui.component.TextFieldComponent;

import javax.swing.*;
import java.awt.*;

public class DeleteClass extends JPanel {
    private JTextField classIdInput;
    private JButton deleteButton;
    private MainPanel mainPanel;

    public DeleteClass(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== HEADER =====
        HeaderComponent headerComponent = new HeaderComponent(
                new String[]{"Trang chủ", "Chỉnh sửa lớp học", "Quay lại"},
                mainPanel
        );
        add(headerComponent, BorderLayout.NORTH);

        // ===== TIÊU ĐỀ =====
        JLabel titleLabel = new JLabel("Xóa lớp học");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 30, 10));

        // ===== NHÃN VÀ Ô NHẬP =====
        JLabel classIdLabel = new JLabel("Nhập mã lớp cần xóa:");
        classIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        classIdInput = new TextFieldComponent(15);

        // ===== NÚT =====
        deleteButton = new ButtonComponent("Xóa lớp");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // ===== FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(classIdLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(classIdInput, gbc);

        // ===== CONTAINER =====
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(titleLabel);
        containerPanel.add(formPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        containerPanel.add(deleteButton);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(Color.WHITE);
        outerPanel.add(containerPanel);

        add(outerPanel, BorderLayout.CENTER);

        // ===== SỰ KIỆN =====
        deleteButton.addActionListener(e -> handleDeleteClass());
    }

    private void handleDeleteClass() {
        String classId = classIdInput.getText().trim();

        if (classId.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập mã lớp cần xóa!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

         // gọi DAO để xóa lớp
         boolean success = ClassDao.deleteClass(classId);

         if (success) {
             JOptionPane.showMessageDialog(this, "Xóa lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
         } else {
             JOptionPane.showMessageDialog(this, "Có lỗi khi xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
         }

    }
}
