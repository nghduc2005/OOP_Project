package app.ui;

import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;
import app.service.TeacherService;

import javax.swing.*;
import java.awt.*;

public class LoginPanelTeacher extends JPanel {
    JLabel usernameLabel, passwordLabel, titleLabel;
    JTextField username;
    JPasswordField password;
    JButton loginButton;
    MainPanel mainPanel;
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
        loginButton = new JButton("Login");

        //Set các size
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBounds(W/2-100,H/2-400,500,50);
        usernameLabel.setBounds(W/2-200,H/2-300,100,30);
        passwordLabel.setBounds(W/2-200,H/2-260,100,30);
        username.setBounds(W/2-100,H/2-300,230,30);
        password.setBounds(W/2-100,H/2-260,230,30);
        loginButton.setBounds(W/2-100,H/2-200,230,30);

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
        JLabel forgotPasswordLabel = new JLabel("<HTML><U>Quên mật khẩu?</U></HTML>");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        forgotPasswordLabel.setBounds(W/2-100+250,H/2-260,150,30);

        // Thêm sự kiện click
        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(
                        LoginPanelTeacher.this,
                        "Tính năng khôi phục mật khẩu đang được phát triển!",
                        "Quên mật khẩu",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // Thêm label vào panel
        add(forgotPasswordLabel);
    }
    public void loginSubmit(){
        String username = this.username.getText();
        String password = new String(this.password.getPassword());
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
