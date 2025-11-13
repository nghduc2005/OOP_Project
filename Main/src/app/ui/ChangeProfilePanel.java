package app.ui;

import app.dao.*;
import app.model.Student;
import app.session.Session;
import app.ui.component.ButtonComponent;
import app.ui.component.HeaderComponent;
import app.ui.component.TextFieldComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class ChangeProfilePanel extends JPanel {
    MainPanel mainPanel;
    JLabel titleLabel, lastNameLabel, firstNameLabel, dateOfBirthLabel, emailLabel, phoneNumberLabel,
            exampleLastNameLabel,
            exampleFirstNameLabel, exampleDateOfBirthLabel, exampleEmailLabel, examplePhoneNumberLabel;
    JTextField lastNameInput, firstNameInput, dateOfBirthInput, emailInput, phoneNumberInput;
    JButton changeProfileButton, saveProfileButton;

    public ChangeProfilePanel(MainPanel mainPanel) {
        setLayout(new BorderLayout());
        this.mainPanel = mainPanel;
        setBackground(new Color(245, 247, 250)); // Màu nền sáng
        
        titleLabel = new JLabel("Change Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lastNameLabel = new JLabel("Last Name:");
        firstNameLabel = new JLabel("First Name:");
        dateOfBirthLabel = new JLabel("Date of Birth:");
        emailLabel = new JLabel("Email:");
        phoneNumberLabel = new JLabel("Phone Number:");
        
        lastNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        firstNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateOfBirthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        phoneNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        lastNameLabel.setForeground(new Color(44, 62, 80));
        firstNameLabel.setForeground(new Color(44, 62, 80));
        dateOfBirthLabel.setForeground(new Color(44, 62, 80));
        emailLabel.setForeground(new Color(44, 62, 80));
        phoneNumberLabel.setForeground(new Color(44, 62, 80));

        exampleLastNameLabel = new JLabel("Ví dụ: Nguyễn Văn");
        exampleFirstNameLabel = new JLabel("Ví dụ: A");
        exampleDateOfBirthLabel = new JLabel("Ví dụ: 1999-12-20");
        exampleEmailLabel = new JLabel("Ví dụ: abc@gmail.com");
        examplePhoneNumberLabel = new JLabel("Ví dụ: 0987654321");
        
        exampleLastNameLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        exampleFirstNameLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        exampleDateOfBirthLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        exampleEmailLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        examplePhoneNumberLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        
        exampleLastNameLabel.setForeground(new Color(127, 140, 141));
        exampleFirstNameLabel.setForeground(new Color(127, 140, 141));
        exampleDateOfBirthLabel.setForeground(new Color(127, 140, 141));
        exampleEmailLabel.setForeground(new Color(127, 140, 141));
        examplePhoneNumberLabel.setForeground(new Color(127, 140, 141));

        lastNameInput = new TextFieldComponent(15);
        firstNameInput = new TextFieldComponent(15);
        dateOfBirthInput = new TextFieldComponent(15);
        emailInput = new TextFieldComponent(15);
        phoneNumberInput = new TextFieldComponent(15);

        String query = "select * from teachers";
        List<HashMap<String,Object>> list = DatabaseConnection.readTable(query);
        for(HashMap<String,Object> map : list){
            lastNameInput.setText(map.get("last_name").toString());
            firstNameInput.setText(map.get("first_name").toString());
            dateOfBirthInput.setText(map.get("dateOfBirth").toString());
            emailInput.setText(map.get("email").toString());
            phoneNumberInput.setText(map.get("phone").toString());
        }


        changeProfileButton = new ButtonComponent("Change Profile");
        changeProfileButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        changeProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveProfileButton = new ButtonComponent("Save");
        saveProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveProfileButton.setBorder(BorderFactory.createEmptyBorder(10, 38, 10, 38));

        JPanel containerFormPanel = new JPanel();
        containerFormPanel.setBackground(Color.WHITE);
        containerFormPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        containerFormPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbMain = new GridBagConstraints();
        gbMain.anchor = GridBagConstraints.NORTHWEST;
        gbMain.fill = GridBagConstraints.HORIZONTAL;
        gbMain.ipadx = 50;
        gbMain.ipady = 50;
        gbMain.gridx = 0; gbMain.gridy = 0;

//        LeftForm
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        GridBagConstraints gbLeft = new GridBagConstraints();
        gbLeft.insets = new Insets(10, 10, 10, 10);
        gbLeft.gridx = 0; gbLeft.gridy = 0; leftPanel.add(lastNameLabel, gbLeft);
        gbLeft.gridx = 1; leftPanel.add(lastNameInput, gbLeft);
        gbLeft.gridx = 0; gbLeft.gridy=1; leftPanel.add(firstNameLabel, gbLeft);
        gbLeft.gridx = 1; leftPanel.add(firstNameInput, gbLeft);
        gbLeft.gridx = 0; gbLeft.gridy = 2; leftPanel.add(dateOfBirthLabel, gbLeft);
        gbLeft.gridx = 1; leftPanel.add(dateOfBirthInput, gbLeft);

//        RightForm
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbRight = new GridBagConstraints();
        gbRight.insets = new Insets(10, 10, 10, 10);
        gbRight.gridx = 0; gbRight.gridy = 0; rightPanel.add(phoneNumberLabel, gbRight);
        gbRight.gridx = 1; rightPanel.add(phoneNumberInput, gbRight);
        gbRight.gridx = 0; gbRight.gridy=1; rightPanel.add(emailLabel, gbRight);
        gbRight.gridx = 1; rightPanel.add(emailInput, gbRight);

        containerFormPanel.add(leftPanel, gbMain);
        gbMain.gridx = 1;
        containerFormPanel.add(rightPanel, gbMain);

        JPanel containerPanel = new JPanel();
        containerPanel.setBackground(new Color(245, 247, 250));
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);
        containerPanel.add(titleLabel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPanel.add(containerFormPanel);
//        containerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
//        containerPanel.add(saveProfileButton);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPanel.add(changeProfileButton);
        containerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        containerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.add(containerPanel, new GridBagConstraints());

        HeaderComponent headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Lịch học", "Thông tin cá " +
                "nhân", "Đổi mật khẩu",
                "Đăng " +
                "xuất", "Quay lại"},
                mainPanel);
        add(headerComponent, BorderLayout.NORTH);
        add(outerPanel, BorderLayout.CENTER);

        // Action ChangeProfile
        changeProfileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Change_profile();
            }
        });
    }
    public void Change_profile(){
        String fullname = firstNameInput.getText().trim() +" "+ lastNameInput.getText().trim();
        String birthday = dateOfBirthInput.getText().trim();
        String phone = phoneNumberInput.getText().trim();
        String email = emailInput.getText().trim();
        if (fullname.isBlank() || birthday.isBlank() || phone.isBlank() || email.isBlank()){
            JOptionPane.showMessageDialog(
                    ChangeProfilePanel.this,
                    "Vui lòng nhâp đầy đủ",
                    "Chú ý",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }else{
            if(!fullname.matches("^\\p{L}+(?:\\s+\\p{L}+)*$")) {
                JOptionPane.showMessageDialog(this, "Tên chỉ chứa chữ cái!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!phone.isEmpty() && !phone.matches("\\d{9,11}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ! Vui lòng nhập 9-11 chữ số.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ! Vui lòng nhập đúng định dạng email.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate dateOfBirth = null;
            if (!birthday.isEmpty()) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dateOfBirth = LocalDate.parse(birthday, formatter);
                    if (dateOfBirth.isAfter(LocalDate.now())) {
                        JOptionPane.showMessageDialog(this, "Ngày sinh không thể là ngày trong tương lai!", "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày sinh không hợp lệ! Sử dụng yyyy-MM-dd", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            boolean result;
            if(Session.getRole().equals("Teacher")){
                result = TeacherDao.updateTeacher(fullname,birthday,phone,email);
            }else{
                result = StudentDao.updateStudent(fullname,birthday,phone,email);
            }
            if ( result ){
                JOptionPane.showMessageDialog(
                        ChangeProfilePanel.this,
                        "Cập nhật thành công",
                        "Chú ý",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }

    }
}
