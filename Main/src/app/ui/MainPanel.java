package app.ui;

import javax.swing.*;
import java.awt.*;
public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    public MainPanel(){
        //Set cardLayout
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        // Test add panel
        add(new ChangeProfilePanel(this), "change-profile");
        add(new LoginPanel(this), "login");
        add(new DashboardPanel(this), "dashboard");
    }
    //Hiển thị panel ứng với name
    public void show(String title){
        cardLayout.show(this, title);
    }
}
