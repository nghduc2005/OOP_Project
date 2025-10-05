package app.ui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(){
        JButton loginButton = new JButton("Login");
        add(loginButton, BorderLayout.NORTH);
    }
}
