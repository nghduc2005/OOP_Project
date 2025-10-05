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
        add(new LoginPanel(), "login");
        add(new DashboardPanel(), "dashboard");
    }
    //Hiển thị panel ứng với name
    public void show(String title){
        cardLayout.show(this, title);
    }
}
