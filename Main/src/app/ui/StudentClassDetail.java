//package app.ui;
//
//import app.dao.DatabaseConnection;
//import app.dao.StudentDao;
//import app.session.Session;
//import app.ui.component.HeaderComponent;
//import app.ui.component.LabelComponent;
//import app.ui.component.TableComponent;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.HashMap;
//import java.util.List;
//
//import app.dao.DatabaseConnection;
//import app.dao.StudentDao;
//import app.model.Classes;
//import app.model.Student;
//import app.ui.component.HeaderComponent;
//import app.ui.component.LabelComponent;
//import app.ui.component.TableComponent;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.HashMap;
//import java.util.List;
//public class StudentClassDetail extends JPanel {
//        JLabel titleLabel, groupLabel, creditLabel, totalStudentLabel;
//        private MainPanel mainPanel;
//        TableComponent table;
//    StudentClassDetail(MainPanel mainPanel,int classid) {
//            this.mainPanel = mainPanel;
//            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            String query = "select * from classes where class_id="+classid;
//            List<HashMap<String, Object>> results = DatabaseConnection.readTable(query);
//            int credit = 0, subjectId = 0, maxStudent = 0;
//            String subjectName = "";
//            for (HashMap<String, Object> row : results) {
//                subjectId= (int) row.get("subject_id");
//                subjectName = row.get("subject_name").toString();
//                credit = (int) row.get("credit"); // có thể NULL
//                maxStudent = (int) row.get("maxnumberstudent");
//            }
//            HeaderComponent headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Lịch học học sinh", "Th" +
//                    "ông " +
//                    "tin cá" +
//                    " " +
//                    "nhân",
//                    "Đổi mật" +
//                    " khẩu", "Đăng xuất",
//                    "Quay lại"},
//                    mainPanel);
//            add(headerComponent);
//            add(classDescriptionPanel(this,"Tên môn học", subjectName, credit, classid, maxStudent));
//            table = new TableComponent(
//                    new String[]{"Tên đăng nhập", "Họ và tên", "Điểm chuyên cần", "Điểm bài tập", "Điểm giữa kì", "Điểm " +
//                            "Cuối kì"},
//                    new int[]{300, 200,200, 200, 200}
//            );
//            String queryTable = "select * from students s join student_class sc on s.username = sc.username and sc" +
//                    ".class_id = " + classid + "where username = " + Session.getUsername();
//            List<HashMap<String, Object>> studentList = DatabaseConnection.readTable(queryTable);
//            for (HashMap<String, Object> row : studentList) {
//                table.addRow(
//                        String.valueOf(row.get("username")),
//                        String.valueOf(row.get("fullname")),
//                        String.valueOf(row.get("attendence")),
//                        String.valueOf(row.get("assignment")),
//                        String.valueOf(row.get("midterm")),
//                        String.valueOf(row.get("final"))
//                );
//            }
//
//            add(table);
//        }
//        public JLabel createLabel(String title, String content) {
//            JLabel label = new LabelComponent(title+": "+content, 15);
//            return label;
//        }
//        public JPanel classDescriptionPanel(app.ui.StudentClassDetail detailPanel, String title, String content, int credit, int classid,
//                                            int maxStudent) {
//            JPanel classDescriptionPanel = new JPanel();
//            JButton editclass = new JButton("Chỉnh sửa");
////        JButton addStudent = new JButton("Thêm học sinh");
//
//            classDescriptionPanel.setLayout(new GridBagLayout());
//            GridBagConstraints c = new GridBagConstraints();
//            c.insets = new Insets(30, 200, 30, 200);
//            c.anchor = GridBagConstraints.WEST;
//            c.gridx = 1; c.gridy = 0;
//            classDescriptionPanel.add(createLabel("Tên môn", content),c);
//            c.gridx = 1; c.gridy = 1;
//            classDescriptionPanel.add(createLabel("Số tín chỉ", String.valueOf(credit)),c);
//            c.gridx = 2; c.gridy = 0;
//            classDescriptionPanel.add(createLabel("Số học sinh tối đa", String.valueOf(maxStudent)),c);
//            c.gridx = 2; c.gridy = 1;
//            classDescriptionPanel.add(createLabel("Nhóm", String.valueOf(classid)), c);
//            c.gridx = 1; c.gridy = 0;
//            classDescriptionPanel.add(creditLabel("Chuyên cần"), String.valueOf(),c);
//            c.gridx = 2; c.gridy = 0;
//            classDescriptionPanel.add(creditLabel("Bài tập"), String.valueOf(), c);
//            c.gridx = 3; c.gridy = 0;
//            classDescriptionPanel.add(creditLabel("Giữa kì"), String.valueOf(), c);
//            c.gridx = 4; c.gridy = 0;
//            classDescriptionPanel.add(creditLabel("Cuối kì"), String.valueOf(), c);
////        classDescriptionPanel.add(addStudent);
//            classDescriptionPanel.setMaximumSize(classDescriptionPanel.getPreferredSize());
//            return classDescriptionPanel;
//        }
//
//}
