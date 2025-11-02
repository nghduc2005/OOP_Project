package app.ui;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import javax.swing.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    Set<String> unShowBackCard = Set.of("", "Role", "Log_t", "Log_s");
    Deque<String> deque = new ArrayDeque<>();
    public MainPanel(){
        //Set cardLayout
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        // Test add panel
        add(new RoleSelectionPanel(this),"Role");
        add(new LoginPanelTeacher(this), "Log_t");
        add(new LoginPanelStudent(this), "Log_s");
        add(new DashboardPanel(this), "dashboard");
        add(new ChangeProfilePanel(this), "ChangeProfile");
        add(new ChangePassword(this), "ChangePassword");
        add(new ScheduleDisplayPanel(this), "ScheduleDisplay");
        add(new GradeManagementPanel(this), "GradeManagement");
    }

    //Hiển thị panel ứng với name
    public void show(String title){
        if (deque.isEmpty() || (!deque.peek().equals(title) && !unShowBackCard.contains(title))) {
            deque.push(title);
        }
        cardLayout.show(this, title);
    }
    //Quay lại card trước đó
    public void back(){
        if (deque.isEmpty()) return;
        deque.pop();
        if (deque.isEmpty()) return;
        String previous = deque.peek();
        if (unShowBackCard.contains(previous)) return;
        cardLayout.show(this, previous);
    }
    public boolean hasCard(String name) {
        for (Component comp : getComponents()) {
            if (name.equals(this.getLayout().toString())) {
                return true;
            }
        }
        return false;
    }



}
