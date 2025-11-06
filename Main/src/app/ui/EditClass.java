package app.ui;

import app.dao.ClassDao;
import app.dao.DatabaseConnection;
import app.dto.request.SubjectItem;
import app.model.Classes;
import app.session.Session;
import app.ui.component.HeaderComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class EditClass extends JPanel {
    private JTextField classIdInput, totalStudentInput, maxStudentInput, creditInput, attendenceInput,
    assignmentInput, midtermInput, finalInput;
    ;
    private JComboBox<SubjectItem> subjectCombox;
    private JTextField   studentCountField, maxStudentField;
    private JButton updateButton;
    private MainPanel mainPanel;
    private Dialog dialog;
    private ClassDetailPanel classDetailPanel;
    private JPanel description;
    private int classid;
    public EditClass(MainPanel mainPanel, Dialog dialog,int classid, ClassDetailPanel classDetailPanel) {
        this.classid = classid;
        this.mainPanel = mainPanel;
        this.dialog = dialog;
        this.classDetailPanel = classDetailPanel;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // Màu nền sáng
        // ====== PANEL CHÍNH ======
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ====== TIÊU ĐỀ ======
        JLabel titleLabel = new JLabel("Chỉnh sửa thông tin lớp học");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // ====== NHÃN + Ô NHẬP ======

        JLabel nameLabel = new JLabel("Tên môn học:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(44, 62, 80));
        
        subjectCombox = new JComboBox<>();

        String subjectQuery = "SELECT subject_id, name FROM subjects";
        List<HashMap<String, Object>> subjectData = DatabaseConnection.readTable(subjectQuery);
        for (HashMap<String, Object> row : subjectData) {
            String name = row.get("name").toString();
            int id = Integer.parseInt(row.get("subject_id").toString());
            subjectCombox.addItem(new SubjectItem(id, name));
        }


        JLabel creditLabel = new JLabel("Số tín chỉ:");
        creditLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        creditLabel.setForeground(new Color(44, 62, 80));
        creditInput = new JTextField(20);

        JLabel maxStudentLabel = new JLabel("Số học sinh tối đa:");
        maxStudentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        maxStudentLabel.setForeground(new Color(44, 62, 80));
        maxStudentField = new JTextField(20);

        gbc.gridy = 2; gbc.gridx = 0; formPanel.add(nameLabel, gbc);
        gbc.gridx = 1; formPanel.add(subjectCombox, gbc);

        gbc.gridy = 3; gbc.gridx = 0; formPanel.add(creditLabel, gbc);
        gbc.gridx = 1; formPanel.add(creditInput, gbc);

        gbc.gridy = 4; gbc.gridx = 0; formPanel.add(maxStudentLabel, gbc);
        gbc.gridx = 1; formPanel.add(maxStudentField, gbc);

        // ====== NÚT CẬP NHẬT ======
        updateButton = new JButton("Cập nhật");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateButton.setBackground(new Color(52, 152, 219)); // Xanh đẹp
        updateButton.setForeground(Color.WHITE);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(updateButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ====== SỰ KIỆN ======
        updateButton.addActionListener(e -> updateClass());
    }

    private void updateClass() {
        String id = String.valueOf(classid);
        String name = subjectCombox.getSelectedItem().toString().trim();
        String max = maxStudentField.getText().trim();
        String credit = creditInput.getText().trim();
        SubjectItem subjectItem = (SubjectItem) subjectCombox.getSelectedItem();
        String subjectId =Integer.toString(subjectItem.getId());
        System.out.println(id + " " + name + " " +  max + " " + credit + " " + subjectId);
        if ( name.isEmpty() || max.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {

            Classes cl = new Classes(name, Integer.parseInt(max), Integer.parseInt(subjectId),Integer.parseInt(credit));
            cl.setClassId(Integer.parseInt(id));
            boolean success = ClassDao.updateClass(cl);
            if(success) {
                JOptionPane.showMessageDialog(this, "Sửa lớp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                mainPanel.reloaClassDetails(classDetailPanel, classid);
                dialog.dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Có lỗi khi sửa lớp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                new Throwable().printStackTrace();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
