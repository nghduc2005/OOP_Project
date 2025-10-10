package app.ui;

import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;
import app.service.TeacherService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    JLabel usernameLabel, passwordLabel, titleLabel;
    JTextField username;
    JPasswordField password;
    JButton loginButton;
    MainPanel mainPanel;
    public LoginPanel(MainPanel mainPanel) {
        //Khởi tạo
        this.mainPanel = mainPanel;
        setLayout(null);
        titleLabel = new JLabel("Login");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("password");
        username = new JTextField();
        password = new JPasswordField();
        loginButton = new JButton("Login");

        //Set các size
        titleLabel.setBounds(750,150,100,50);
        usernameLabel.setBounds(600,200,100,30);
        passwordLabel.setBounds(600,250,100,30);
        username.setBounds(700,200,150,30);
        password.setBounds(700,250,150,30);
        loginButton.setBounds(700,300,150,30);

        //Add các action
        loginButton.addActionListener(e->loginSubmit()); //dùng lambda rồi truyền logic theo từng component

        //Add vào panel
        add(titleLabel);
        add(usernameLabel);
        add(passwordLabel);
        add(username);
        add(password);
        add(loginButton);

        //Set các thiết lập cho panel
    }
    public void loginSubmit(){
        String username = this.username.getText();
        String password = this.password.getPassword().toString();
        TeacherService  teacherService = new TeacherService();
        try {
            LoginResponse response = teacherService.loginRequestValidate(new LoginRequest(username, password));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        mainPanel.show("dashboard");
    }
}
