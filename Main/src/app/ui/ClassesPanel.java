package app.ui;

import app.ui.component.HeaderComponent;

import javax.swing.*;
import java.awt.*;

public class ClassesPanel extends JPanel {
    private JButton addButton, editButton, deleteButton;
    private MainPanel mainPanel;

    public ClassesPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ====== HEADER ======
        HeaderComponent headerComponent = new HeaderComponent(
                new String[]{"Trang chủ", "Quản lý lớp học", "Đăng xuất"},
                mainPanel
        );
        add(headerComponent, BorderLayout.NORTH);

        // ====== TIÊU ĐỀ ======
        JLabel titleLabel = new JLabel("Lớp học");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ====== NÚT CHỨC NĂNG ======
        addButton = new JButton("Thêm lớp");
        editButton = new JButton("Sửa lớp");
        deleteButton = new JButton("Xóa lớp");

        JButton[] buttons = {addButton, editButton, deleteButton};
        for (JButton btn : buttons) {
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(new Color(66, 133, 244));
            btn.setForeground(Color.WHITE);
            btn.setPreferredSize(new Dimension(150, 45));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // ====== TỔ CHỨC TOÀN BỘ PANEL ======
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(buttonPanel);

        add(centerPanel, BorderLayout.CENTER);

        // ====== SỰ KIỆN ======
        addButton.addActionListener(e -> openAddClass());
        editButton.addActionListener(e -> openEditClass());
        deleteButton.addActionListener(e -> openDeleteClass());
    }
    private void openAddClass() {
        mainPanel.add(new AddClass(mainPanel), "add_class");
        mainPanel.show("add_class");
    }
    private void openEditClass(){
        mainPanel.add(new EditClass(mainPanel),"edit_class");
        mainPanel.show("edit_class");
    }
    private void openDeleteClass() {
        mainPanel.add(new DeleteClass(mainPanel), "delete_class");
        mainPanel.show("delete_class");
    }
}
