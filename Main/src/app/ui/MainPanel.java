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
        add(new RoleSelectionPanel(this),"Role");
        add(new LoginPanelTeacher(this), "Log_t");
        add(new LoginPanelStudent(this), "Log_s");
        add(new DashboardPanel(this), "dashboard");
    }
    //Hiển thị panel ứng với name
    public void show(String title){
        cardLayout.show(this, title);
    }
}
