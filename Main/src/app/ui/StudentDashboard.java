package app.ui;

import app.dao.DatabaseConnection;
import app.model.*;
import app.session.Session;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import app.model.Student;
import app.model.Subject;
import app.ui.component.HeaderComponent;
import app.ui.component.LabelComponent;
import app.ui.component.TableComponent;
import app.dao.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
public class StudentDashboard extends JPanel {
        JPanel header;
        HeaderComponent headerComponent;
        CardSubjectTeacher cardSubjectTeachers;
        LabelComponent titleLabel;
        MainPanel mainPanel;
        JButton addStudent, deleteStudent;
        //Trang chủ
        public StudentDashboard(MainPanel mainPanel) {
            this.mainPanel = mainPanel;
            setLayout(new BorderLayout());
            headerComponent = new HeaderComponent(new String[]{
                    "Trang chủ",
                    "Lịch học",
                    "Thông tin cá nhân",
                    "Đổi mật khẩu",
                    "Đăng xuất",
                    "Quay lại"},
                    mainPanel);
            add(headerComponent, BorderLayout.NORTH);
            JPanel centerPanel = centerPanel();

            add(centerPanel, BorderLayout.CENTER);
        }
        public JPanel centerPanel() {
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            // TitleLabel
            titleLabel = new LabelComponent("Trang chủ", 50);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerPanel.add(titleLabel);
            centerPanel.add(combinePanel());

            return centerPanel;
        }
        public JPanel combinePanel() {
            JPanel combinePanel = new JPanel();
            combinePanel.setLayout(new BorderLayout());
            JScrollPane scrollPanel = new JScrollPane(cardListPanel(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            combinePanel.add(scrollPanel, BorderLayout.CENTER);
//        combinePanel.add(tableStudent(), BorderLayout.CENTER);
            return combinePanel;
        }
        public JPanel cardListPanel() {
            JPanel cardListPanel = new JPanel();
            cardListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // căn giữa + spacing
            cardListPanel.setOpaque(false);
            String query = "select * from classes c join student_class cl on c.class_id = cl.class_id join students s" +
                    " on s.username = cl.username where s.username = '" + Session.getUsername() +"'";
            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
            for (HashMap<String, Object> row : results) {
                int classId = Integer.parseInt(row.get("class_id").toString());
                int subjectId = (int) row.get("subject_id");
                String subjectName = row.get("subject_name").toString();
                int credit =(int) row.get("credit"); // có thể NULL
                int maxStudent = (int) row.get("maxnumberstudent");
                CardSubjectTeacher card = new CardSubjectTeacher(new Subject(subjectId , subjectName, (int) row.get(
                        "credit")), Integer.toString(classId));
                card.setName(String.format("%s", classId));
                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(StudentDashboard.this);
                        SwingUtilities.invokeLater(() -> {
                            SubjectPopup dialog = new SubjectPopup(parentFrame, row, Session.getUsername()); // Passing the parent
                            // JFrame
                            // to the
                            // dialog
                            dialog.setVisible(true); // Show the dialog
                        });
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                });
                cardListPanel.add(card);
            }

            int height = 300 * results.size()/6;
            cardListPanel.setPreferredSize(new Dimension(600, height));
            cardListPanel.setMaximumSize(cardListPanel.getPreferredSize());
            cardListPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            return cardListPanel;
        }


}
