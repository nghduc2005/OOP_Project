package app.ui;

import app.model.Student;
import app.ui.component.ButtonComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddStudentForm extends JDialog {

    private JTextField txtFullName;
    private JTextField txtPhoneNumber;
    private JTextField txtEmail;
    private JTextField txtDateOfBirth;

    private JLabel lblGeneratedStudentId;
    private JLabel lblGeneratedUsername;

    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnReset;

    private Student result = null;
    private boolean saved = false;

    private String generatedStudentId;
    private String generatedUsername;
    private final String DEFAULT_PASSWORD = "123456";
    private int class_id;

    public AddStudentForm(JFrame parent) {
        super(parent, "Thêm học sinh mới", true);
//        this.class_id = class_id;
        generateStudentInfo();
        initializeComponents();
        setupLayout();
        setupEvents();
        setupDialog();
    }

    private void generateStudentInfo() {
        generatedStudentId = app.dao.AutoGenerationDao.generateStudentId();
        generatedUsername = generatedStudentId;
        System.out.println("Generated Student ID from DB: " + generatedStudentId);
    }

    private void initializeComponents() {
        txtFullName = new JTextField(25);
        txtFullName.setToolTipText("VD: Nguyễn Văn A");
        txtPhoneNumber = new JTextField(15);
        txtPhoneNumber.setToolTipText("VD: 0901234567 (9-11 chữ số)");
        txtEmail = new JTextField(20);
        txtEmail.setToolTipText("VD: nguyenvana@example.com");
        txtDateOfBirth = new JTextField(10);
        txtDateOfBirth.setToolTipText("Định dạng: dd/MM/yyyy (VD: 15/03/2000)");

        lblGeneratedStudentId = new JLabel(generatedStudentId);
        lblGeneratedUsername = new JLabel(generatedUsername);

        Font boldFont = new Font("Segoe UI", Font.BOLD, 13);
        lblGeneratedStudentId.setFont(boldFont);
        lblGeneratedUsername.setFont(boldFont);
        lblGeneratedStudentId.setForeground(new Color(0x3498DB));
        lblGeneratedUsername.setForeground(new Color(0x3498DB));

        btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(Color.WHITE);
        btnSave.setForeground(new Color(0x2C3E50));
        btnSave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
//        btnSave.setFocusPainted(false);
        btnSave.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCancel.setBackground(Color.WHITE);
        btnCancel.setForeground(new Color(0x2C3E50));
        btnCancel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnReset = new JButton("Reset");
        btnReset.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnReset.setBackground(Color.WHITE);
        btnReset.setForeground(new Color(0x2C3E50));
        btnReset.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        btnReset.setFocusPainted(false);
        btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        TitledBorder border = new TitledBorder("Thông tin học sinh");
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        border.setTitleColor(new Color(0x2C3E50));
        panel.setBorder(border);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMaHS = new JLabel("Mã học sinh:");
        lblMaHS.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMaHS.setForeground(new Color(0x2C3E50));
        panel.add(lblMaHS, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        lblGeneratedStudentId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGeneratedStudentId.setForeground(new Color(0x3498DB));
        panel.add(lblGeneratedStudentId, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblTenDN = new JLabel("Tên đăng nhập:");
        lblTenDN.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTenDN.setForeground(new Color(0x2C3E50));
        panel.add(lblTenDN, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        lblGeneratedUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGeneratedUsername.setForeground(new Color(0x3498DB));
        panel.add(lblGeneratedUsername, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblMatKhau = new JLabel("Mật khẩu mặc định:");
        lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMatKhau.setForeground(new Color(0x2C3E50));
        panel.add(lblMatKhau, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JLabel lblPassword = new JLabel(DEFAULT_PASSWORD);
        lblPassword.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblPassword.setForeground(new Color(0x7F8C8D));
        panel.add(lblPassword, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel lblHoTen = new JLabel("Họ tên *:");
        lblHoTen.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHoTen.setForeground(new Color(0x2C3E50));
        panel.add(lblHoTen, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        txtFullName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(txtFullName, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtPhoneNumber, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtEmail, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtDateOfBirth, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(new EmptyBorder(15, 0, 0, 0));
        panel.add(btnReset);
        panel.add(btnCancel);
        panel.add(btnSave);
        return panel;
    }

    private void setupEvents() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAndSave()) {
                    saved = true;
                    dispose();
                }
            }
        });

        btnCancel.addActionListener(e -> {
            saved = false;
            dispose();
        });

        btnReset.addActionListener(e -> clearForm());
        SwingUtilities.invokeLater(() -> txtFullName.requestFocus());
    }

    private boolean validateAndSave() {
        try {
            String fullName = txtFullName.getText().trim();
            String phoneNumber = txtPhoneNumber.getText().trim();
            String email = txtEmail.getText().trim();

            if (fullName.isEmpty()) {
                showError("Vui lòng điền họ tên!");
                txtFullName.requestFocus();
                return false;
            }
            if(!fullName.matches("^\\p{L}+(?:\\s+\\p{L}+)*$")) {
                showError("Tên chỉ chứa chữ cái!");
                txtFullName.requestFocus();
                return false;
            }
            if (!phoneNumber.isEmpty() && !phoneNumber.matches("\\d{9,11}")) {
                showError("Số điện thoại không hợp lệ! Vui lòng nhập 9-11 chữ số.");
                txtPhoneNumber.requestFocus();
                return false;
            }

            if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showError("Email không hợp lệ! Vui lòng nhập đúng định dạng email.");
                txtEmail.requestFocus();
                return false;
            }

            LocalDate dateOfBirth = null;
            String dateStr = txtDateOfBirth.getText().trim();
            if (!dateStr.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    dateOfBirth = LocalDate.parse(dateStr, formatter);
                    if (dateOfBirth.isAfter(LocalDate.now())) {
                        showError("Ngày sinh không thể là ngày trong tương lai!");
                        txtDateOfBirth.requestFocus();
                        return false;
                    }
                } catch (DateTimeParseException ex) {
                    showError("Định dạng ngày sinh không hợp lệ! Sử dụng dd/MM/yyyy");
                    txtDateOfBirth.requestFocus();
                    return false;
                }
            }

            String[] nameParts = fullName.split("\\s+", 2);
            String lastName = nameParts.length > 1 ? nameParts[0] : "";
            String firstName = nameParts.length > 1 ? nameParts[1] : fullName;
            int id = Integer.parseInt(generatedStudentId.replaceAll("\\D+", ""));
            result = new Student(
                    id,  // Let database auto-generate student_id
                    lastName,
                    firstName,
                    txtFullName.getText().trim(),
                    generatedUsername,
                    DEFAULT_PASSWORD,
                    phoneNumber,
                    email,
                    dateOfBirth
            );

            showInfo("Tạo thành công!\n" +
                    "Mã học sinh: " + generatedStudentId + "\n" +
                    "Tên đăng nhập: " + generatedUsername + "\n" +
                    "Mật khẩu mặc định: " + DEFAULT_PASSWORD);
            boolean success = app.dao.StudentDao.createStudent(result);

            if (success) {
                showInfo("Tạo thành công!\n" +
                        "Mã học sinh: (Tự động)\n" +
                        "Tên đăng nhập: " + generatedUsername + "\n" +
                        "Mật khẩu mặc định: " + DEFAULT_PASSWORD);

                return true;
            } else {
                showError("Không thể lưu sinh viên vào CSDL! \n(Có thể Tên đăng nhập đã tồn tại)");
                return false;
            }
        } catch (Exception ex) {
            showError("Có lỗi xảy ra: " + ex.getMessage());
            return false;
        }
    }

    private void clearForm() {
        txtFullName.setText("");
        txtPhoneNumber.setText("");
        txtEmail.setText("");
        txtDateOfBirth.setText("");
        generateStudentInfo();
        lblGeneratedStudentId.setText(generatedStudentId);
        lblGeneratedUsername.setText(generatedUsername);
        txtFullName.requestFocus();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupDialog() {
        setSize(500, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public boolean isSaved() { return saved; }
    public Student getStudent() { return result; }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public Student showAddStudentDialog(JFrame parent) {
        AddStudentForm dialog = new AddStudentForm(parent);
        dialog.setVisible(true);
        return dialog.isSaved() ? dialog.getStudent() : null;
    }
}
