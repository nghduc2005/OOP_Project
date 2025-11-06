package app.ui;


import app.model.Group;
import app.service.GroupService;
import app.dao.AutoGenerationDao;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupForm extends JDialog {

    private JComboBox<String> cmbSubject;
    private JSpinner spnMaxStudents;
    private JLabel lblGeneratedGroupId;
    private JButton btnCreate;
    private JButton btnCancel;
    private JButton btnReset;
    private Group result = null;
    private boolean created = false;
    private String generatedGroupId;
    private final String DEFAULT_TEACHER = "Nguyễn Văn A";

    public CreateGroupForm(JFrame parent) {
        super(parent, "Tạo lớp học mới", true);
        generateGroupInfo();
        initializeComponents();
        setupLayout();
        setupEvents();
        setupDialog();
    }

    private void generateGroupInfo() {
        generatedGroupId = app.dao.AutoGenerationDao.getUniqueGroupId();
        System.out.println("Generated Group ID from DB: " + generatedGroupId);
    }

    private void initializeComponents() {
        spnMaxStudents = new JSpinner(new SpinnerNumberModel(40, 10, 80, 1));
        cmbSubject = new JComboBox<>();
        loadSubjects();
        lblGeneratedGroupId = new JLabel(generatedGroupId);
        lblGeneratedGroupId.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblGeneratedGroupId.setForeground(new Color(0x3498DB));
        
        // Buttons
        btnCreate = new JButton("Tạo nhóm");
        btnCreate.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCreate.setBackground(Color.WHITE);
        btnCreate.setForeground(new Color(0x2C3E50));
        btnCreate.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        btnCreate.setFocusPainted(false);
        btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(new Color(0x2C3E50));
        btnCancel.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnReset = new JButton("Làm mới");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnReset.setBackground(Color.WHITE);
        btnReset.setForeground(new Color(0x2C3E50));
        btnReset.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        btnReset.setFocusPainted(false);
        btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void loadSubjects() {
        String[] subjects = {
                "Lập trình hướng đối tượng",
                "Cơ sở dữ liệu",
                "Mạng máy tính",
                "Hệ điều hành",
                "Cấu trúc dữ liệu và giải thuật",
                "Kỹ thuật phần mềm",
                "Trí tuệ nhân tạo",
                "Học máy",
                "An toàn thông tin",
                "Phát triển ứng dụng web"
        };
        cmbSubject.addItem("-- Chọn môn học --");
        for (String subject : subjects) {
            cmbSubject.addItem(subject);
        }
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xF5F7FA));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel formPanel = createFormPanel();
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        TitledBorder border = new TitledBorder("Thông tin lớp học");
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        border.setTitleColor(new Color(0x2C3E50));
        panel.setBorder(border);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMaLop = new JLabel("Mã lớp:");
        lblMaLop.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMaLop.setForeground(new Color(0x2C3E50));
        panel.add(lblMaLop, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        lblGeneratedGroupId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGeneratedGroupId.setForeground(new Color(0x3498DB));
        panel.add(lblGeneratedGroupId, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblGiangVien = new JLabel("Giảng viên:");
        lblGiangVien.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblGiangVien.setForeground(new Color(0x2C3E50));
        panel.add(lblGiangVien, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JLabel lblTeacher = new JLabel(DEFAULT_TEACHER);
        lblTeacher.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblTeacher.setForeground(new Color(0x7F8C8D));
        panel.add(lblTeacher, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMonHoc = new JLabel("Môn học *:");
        lblMonHoc.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMonHoc.setForeground(new Color(0x2C3E50));
        panel.add(lblMonHoc, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        cmbSubject.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(cmbSubject, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblSiSo = new JLabel("Sĩ số tối đa *:");
        lblSiSo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSiSo.setForeground(new Color(0x2C3E50));
        panel.add(lblSiSo, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JPanel spinnerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        spinnerPanel.setBackground(Color.WHITE);
        spnMaxStudents.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        spinnerPanel.add(spnMaxStudents);
        JLabel lblHocSinh = new JLabel(" học sinh");
        lblHocSinh.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblHocSinh.setForeground(new Color(0x2C3E50));
        spinnerPanel.add(lblHocSinh);
        panel.add(spinnerPanel, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(0xF5F7FA));
        panel.setBorder(new EmptyBorder(15, 0, 0, 0));
        panel.add(btnReset);
        panel.add(btnCancel);
        panel.add(btnCreate);
        return panel;
    }

    private void setupEvents() {
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAndCreate()) {
                    created = true;
                    dispose();
                }
            }
        });
        btnCancel.addActionListener(e -> {
            created = false;
            dispose();
        });
        btnReset.addActionListener(e -> clearForm());
        SwingUtilities.invokeLater(() -> cmbSubject.requestFocus());
    }

    private boolean validateAndCreate() {
        try {
            String subjectName = (String) cmbSubject.getSelectedItem();
            int maxStudents = (Integer) spnMaxStudents.getValue();
            if (cmbSubject.getSelectedIndex() == 0) {
                showError("Vui lòng chọn môn học!");
                return false;
            }
            String groupName = subjectName;
            if (maxStudents < 10 || maxStudents > 80) {
                showError("Sĩ số phải từ 10 đến 80 học sinh!");
                return false;
            }
            result = new Group(
                    generatedGroupId,
                    groupName,
                    subjectName,
                    DEFAULT_TEACHER,
                    maxStudents
            );
            showInfo("Tạo lớp thành công!\n" +
                    "Mã lớp: " + generatedGroupId + "\n" +
                    "Tên lớp: " + groupName + "\n" +
                    "Môn học: " + subjectName + "\n" +
                    "Giảng viên: " + DEFAULT_TEACHER);
            return true;
        } catch (Exception ex) {
            showError("Có lỗi xảy ra: " + ex.getMessage());
            return false;
        }
    }

    private void clearForm() {
        cmbSubject.setSelectedIndex(0);
        spnMaxStudents.setValue(40);
        generateGroupInfo();
        lblGeneratedGroupId.setText(generatedGroupId);
        cmbSubject.requestFocus();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupDialog() {
        setSize(450, 380);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public boolean isCreated() { return created; }
    public Group getGroup() { return result; }

    public static Group showCreateGroupDialog(JFrame parent) {
        CreateGroupForm form = new CreateGroupForm(parent);
        form.setVisible(true);
        return form.isCreated() ? form.getGroup() : null;
    }
}
