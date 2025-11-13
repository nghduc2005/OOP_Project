package app.ui;

import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;
import app.service.TeacherService;
import app.ui.component.ButtonComponent;
import app.session.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanelTeacher extends JPanel {
    JLabel usernameLabel, passwordLabel, titleLabel,forgotPasswordLabel, registerLabel;
    JTextField username;
    JPasswordField password;
    JButton loginButton,ShowPassword;
    ButtonComponent returnButton;
    MainPanel mainPanel;
    TeacherService  teacherService;
    char defaultEchoChar;
    int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    int H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public LoginPanelTeacher(MainPanel mainPanel) {
        //Khởi tạo
        this.mainPanel = mainPanel;
        setLayout(null);
        setBackground(new Color(245, 247, 250)); // Màu nền sáng
        //Set up label
        titleLabel = new JLabel("Teacher Login");
        usernameLabel = new JLabel("ID");
        passwordLabel = new JLabel("Password");
        //Set up input
        username = new JTextField();
        password = new JPasswordField();
        loginButton = new JButton("Đăng nhập");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(142, 68, 173)); // Màu tím đẹp cho giáo viên
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // chuyển thành bàn tay
        returnButton = new ButtonComponent("Quay lại");
        //Set up show password
        ShowPassword = new JButton("👁");
        defaultEchoChar = password.getEchoChar();
        //Set các size
        int y=10;
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBounds(W/2-100,H/2-200-y,500,50);
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(44, 62, 80));
        usernameLabel.setBounds(W/2-200,H/2-100-y,100,30);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(44, 62, 80));
        passwordLabel.setBounds(W/2-200,H/2-60-y,100,30);
        username.setBounds(W/2-100,H/2-100-y,230,30);
        password.setBounds(W/2-100,H/2-60-y,230,30);
        loginButton.setBounds(W/2-40,H/2-20-y,230-120,30);
        ShowPassword.setBounds(W/2 - 100 +240 , H/2-55-y, 50, 20);
//        forgotPasswordLabel.setBounds(W/2-100+160-90,H/2+10-y,100,30);
//        registerLabel.setBounds(W/2-100,H/2+10-y,150,30);


        //Add các action
        loginButton.addActionListener(e->loginSubmit()); //dùng lambda rồi truyền logic theo từng component
        ShowPassword.addActionListener(e -> show_Password());
        returnButton.addActionListener(e-> mainPanel.show("Role"));

        //Add vào panel
        add(titleLabel);
        add(usernameLabel);
        add(passwordLabel);
        add(username);
        add(password);
        add(loginButton);
        add(ShowPassword);
        add(returnButton);
    }
    public void loginSubmit(){
        String username = this.username.getText();
        String password = new String(this.password.getPassword());
        teacherService = new TeacherService();
        try {
            LoginResponse response = teacherService.loginRequestValidate(new LoginRequest(username, password));
            if(response.status) {
                Session.setUsername(username);
                Session.setRole("Teacher");
                this.username.setText("");
                this.password.setText("");
                mainPanel.add(new DashboardPanel(mainPanel), "teacher_dashboard");
                mainPanel.add(new ChangeProfilePanel(mainPanel), "ChangeProfile");
                mainPanel.add(new ChangePassword(mainPanel), "ChangePassword");
                mainPanel.show("teacher_dashboard");
            } else {
                JOptionPane.showMessageDialog(this, response.message, "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    public void show_Password(){
        if (this.password.getEchoChar() == (char) 0) {
            password.setEchoChar(defaultEchoChar); //ẩn
        } else {
            password.setEchoChar((char) 0);// hiển thị
        }
    }

}
