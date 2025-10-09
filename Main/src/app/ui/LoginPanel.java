package app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    public LoginPanel(MainPanel mainPanel) {
        setLayout(null);
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(750,150,100,50);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(600,200,100,30);
        JLabel passwordLabel = new JLabel("password");
        passwordLabel.setBounds(600,250,100,30);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(700,200,150,30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(700,250,150,30);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(700,300,150,30);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.show("dashboard");
            }
        });
        add(titleLabel, BorderLayout.NORTH);
        add(usernameLabel, BorderLayout.NORTH);
        add(passwordLabel);
        add(usernameField);
        add(passwordField);
        add(loginButton);
    }
}
