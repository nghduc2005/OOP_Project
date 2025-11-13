package app.ui;

import app.dao.StudentDao;
import app.session.Session;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class EditStudent extends JPanel {
    private JTextField studentIdField, fullNameField, birthdayField, phoneField, emailField, attendenceInput,
            assignmentInput, midtermInput, finalInput;
    private JButton updateButton;
    private MainPanel mainPanel;
    private String useranme;
    private int classid;
    private ClassDetailPanel classDetailPanel;
    public EditStudent(MainPanel mainPanel, String username, int classid, ClassDetailPanel classDetailPanel,
                       String attendence
            , String assignment,String midterm,String finalGrade) {
        this.mainPanel = mainPanel;
        this.useranme = username;
        this.classid = classid;
        this.classDetailPanel = classDetailPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // Màu nền sáng

        // Main panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Chỉnh sửa thông tin học sinh");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Label + ô nhập
        JLabel idLabel = new JLabel("Điểm chuyên cần:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idLabel.setForeground(new Color(44, 62, 80));
        attendenceInput = new JTextField(20);
        attendenceInput.setToolTipText("Nhập điểm chuyên cần");

        JLabel nameLabel = new JLabel("Điểm bài tập:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(44, 62, 80));
        assignmentInput = new JTextField(20);

        JLabel birthdayLabel = new JLabel("Điểm giữa kì:");
        birthdayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        birthdayLabel.setForeground(new Color(44, 62, 80));
        midtermInput = new JTextField(20);
//        birthdayField.setToolTipText("Định dạng YYYY-MM-DD, VD: 2000-03-15");

        JLabel phoneLabel = new JLabel("Điểm cuối kì:");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneLabel.setForeground(new Color(44, 62, 80));
        finalInput = new JTextField(20);

        attendenceInput.setText(attendence);
        assignmentInput.setText(assignment);
        midtermInput.setText(midterm);
        finalInput.setText(finalGrade);
//        JLabel emailLabel = new JLabel("Email:");
//        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//        emailLabel.setForeground(new Color(44, 62, 80));
//        emailField = new JTextField(20);

        gbc.gridy = 1; gbc.gridx = 0; formPanel.add(idLabel, gbc);
        gbc.gridx = 1; formPanel.add(attendenceInput, gbc);

        gbc.gridy = 2; gbc.gridx = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(assignmentInput, gbc);

        gbc.gridy = 3; gbc.gridx = 0; formPanel.add(birthdayLabel, gbc);
        gbc.gridx = 1; formPanel.add(midtermInput, gbc);

        gbc.gridy = 4; gbc.gridx = 0; formPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; formPanel.add(finalInput, gbc);
//
//        gbc.gridy = 5; gbc.gridx = 0; formPanel.add(emailLabel, gbc);
//        gbc.gridx = 1; formPanel.add(emailField, gbc);

        // nút update
        updateButton = new JButton("Cập nhật");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateButton.setBackground(new Color(52, 152, 219)); // Xanh đẹp
        updateButton.setForeground(Color.WHITE);
        updateButton.setBorderPainted(false);
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
        try {
            BigDecimal attendence =  new BigDecimal(attendenceInput.getText().trim());
            BigDecimal assignment =  new BigDecimal(assignmentInput.getText().trim());
            BigDecimal midterm =  new BigDecimal(midtermInput.getText().trim());
            BigDecimal finalGrade =  new BigDecimal(finalInput.getText().trim());

            if (attendenceInput.getText().trim().isEmpty() || assignmentInput.getText().trim().isEmpty() || midtermInput.getText().trim().isEmpty() || finalInput.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Boolean cmpAttendence = attendence.compareTo(BigDecimal.ZERO) >= 0 && attendence.compareTo(new BigDecimal(10)) <= 0;
            Boolean cmpAssignment =
                    assignment.compareTo(BigDecimal.ZERO) >= 0 && assignment.compareTo(new BigDecimal(10)) <= 0;
            Boolean cmpMidterm = midterm.compareTo(BigDecimal.ZERO) >= 0 && midterm.compareTo(new BigDecimal(10)) <= 0;
            Boolean cmpFinalGrade =
                    finalGrade.compareTo(BigDecimal.ZERO) >= 0 && finalGrade.compareTo(new BigDecimal(10)) <= 0;
            if(!cmpAttendence || !cmpAssignment || !cmpMidterm || !cmpFinalGrade) {
                JOptionPane.showMessageDialog(this, "Mọi đầu điểm phải nằm trong khoảng từ [0; 10]");
                return;
            }
            // Gọi hàm
            boolean success = StudentDao.updateStudentByUsername(useranme, attendence, assignment, midterm,
                    finalGrade, classid);

            if(success) {
                JOptionPane.showMessageDialog(this, "Sửa thông tin học sinh thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                mainPanel.reloaClassDetails(classDetailPanel, classid);
            }
            else
                JOptionPane.showMessageDialog(this, "Có lỗi khi sửa, hoặc mã học sinh không tồn tại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là một số thực!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}