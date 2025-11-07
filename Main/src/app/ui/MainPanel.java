package app.ui;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import javax.swing.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private DashboardPanel dashboardPanel;
    private StudentDashboard studentDashboard;
    private ClassDetailPanel classDetailPanel;
    Set<String> unShowBackCard = Set.of("", "Role", "Log_t", "Log_s");
    Deque<String> deque = new ArrayDeque<>();
    public MainPanel(){
        //Set cardLayout
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        // Test add panel
        dashboardPanel = new DashboardPanel(this);
        studentDashboard = new StudentDashboard(this);
        add(new RoleSelectionPanel(this),"Role");
        add(new LoginPanelTeacher(this), "Log_t");
        add(new LoginPanelStudent(this), "Log_s");
        add(dashboardPanel, "teacher_dashboard");
        add(studentDashboard, "student_dashboard");
        add(new ScheduleDisplayPanel(this), "Schedule");
        add(new ChangeProfilePanel(this), "ChangeProfile");
        add(new ChangeProfileStudentPanel(this), "ChangeProfileStudent");
//        add(new ChangePassword(this), "ChangePassword");
        add(new ScheduleDisplayPanel(this), "ScheduleDisplay");
        add(new StudentSchedule(this), "StudentSchedule");
        add(new GradeManagementPanel(this), "GradeManagement");
    }
    public void reloadDashboard() {
        remove(dashboardPanel);
        DashboardPanel newPanel = new DashboardPanel(this);
        add(newPanel, "teacher_dashboard");
        cardLayout.show(this, "teacher_dashboard");
    }

    public void reloadStudentDashboard() {
        remove(studentDashboard);
        StudentDashboard newPanel = new StudentDashboard(this);
        add(newPanel, "student_dashboard");
        cardLayout.show(this, "student_dashboard");
    }
    public void reloaClassDetails(ClassDetailPanel classDetailPanel, int classId) {
        ClassDetailPanel newPanel = new ClassDetailPanel(this, classId);
        add(newPanel, "ClassDetailPanel_"+String.valueOf(classId));
        cardLayout.show(this, "ClassDetailPanel_"+String.valueOf(classId));
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
        if(previous=="teacher_dashboard"){
            reloadDashboard();
        } else if(previous=="student_dashboard"){
            reloadStudentDashboard();
        }
        else {
            cardLayout.show(this, previous);
        }
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
