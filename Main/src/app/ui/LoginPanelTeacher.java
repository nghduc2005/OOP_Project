package app.ui;

import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;
import app.service.TeacherService;
import app.ui.component.ButtonComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanelTeacher extends JPanel {
    JLabel usernameLabel, passwordLabel, titleLabel,forgotPasswordLabel, registerLabel;
    JTextField username;
    JPasswordField password;
    JButton loginButton,ShowPassword;
    MainPanel mainPanel;
    char defaultEchoChar;
    int W = Toolkit.getDefaultToolkit().getScreenSize().width;
    int H = Toolkit.getDefaultToolkit().getScreenSize().height;

    public LoginPanelTeacher(MainPanel mainPanel) {
        //Khởi tạo
        this.mainPanel = mainPanel;
        setLayout(null);
        titleLabel = new JLabel("Teacher Login");
        usernameLabel = new JLabel("ID or email");
        passwordLabel = new JLabel("Password");
        username = new JTextField();
        password = new JPasswordField();
        loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(Color.BLUE);
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // chuyển thành bàn tay
        forgotPasswordLabel = new JLabel("<HTML><U>Quên mật khẩu?</U></HTML>");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel = new JLabel("<HTML><U>Đăng ký</U></HTML>");
        registerLabel.setForeground(Color.GREEN);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ShowPassword = new JButton("👁");
        defaultEchoChar = password.getEchoChar();
        //Set các size
        int y=10;
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBounds(W/2-100,H/2-200-y,500,50);
        usernameLabel.setBounds(W/2-200,H/2-100-y,100,30);
        passwordLabel.setBounds(W/2-200,H/2-60-y,100,30);
        username.setBounds(W/2-100,H/2-100-y,230,30);
        password.setBounds(W/2-100,H/2-60-y,230,30);
        loginButton.setBounds(W/2-40,H/2-20-y,230-120,30);
        ShowPassword.setBounds(W/2 - 100 +240 , H/2-55-y, 50, 20);
        forgotPasswordLabel.setBounds(W/2-100+160-90,H/2+10-y,100,30);
        registerLabel.setBounds(W/2-100,H/2+10-y,150,30);
        ButtonComponent returnButton = new ButtonComponent("Back");

        //Add các action
        loginButton.addActionListener(e->loginSubmit()); //dùng lambda rồi truyền logic theo từng component
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateLater();
            }
        });
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateLater();
            }
        });
        ShowPassword.addActionListener(e -> show_Password());
        returnButton.addActionListener(e-> mainPanel.show("Role"));

        //Add vào panel
        add(titleLabel);
        add(usernameLabel);
        add(passwordLabel);
        add(username);
        add(password);
        add(loginButton);
        add(forgotPasswordLabel);
        add(ShowPassword);
        add(returnButton);
//        add(registerLabel);
        //Set các thiết lập cho panel



    }
    public void loginSubmit(){
        String username = this.username.getText();
        String password = new String(this.password.getPassword());
        TeacherService  teacherService = new TeacherService();
        try {
            LoginResponse response = teacherService.loginRequestValidate(new LoginRequest(username, password));
            if(response.status) {
                mainPanel.show("dashboard");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    public void updateLater(){
        JOptionPane.showMessageDialog(
                LoginPanelTeacher.this,
                "Tính năng này đang được phát triển!",
                "Chú ý",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public void show_Password(){
        if (this.password.getEchoChar() == (char) 0) {
            password.setEchoChar(defaultEchoChar); //ẩn
        } else {
            password.setEchoChar((char) 0);// hiển thị
        }
    }

}
