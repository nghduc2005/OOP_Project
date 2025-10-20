package app.ui;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.util.*;

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
        add(new ClassDetailPanel(this), "ClassDetail");
        add(new ChangeProfilePanel(this), "ChangeProfile");
        add(new ChangePassword(this), "ChangePassword");
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
}
