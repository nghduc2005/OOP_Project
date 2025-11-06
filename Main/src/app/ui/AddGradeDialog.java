package app.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.model.Grade;
import app.service.GradeService;

public class AddGradeDialog extends JDialog {
    private JTextField studentIdField;
    private JTextField subjectIdField;
    private JTextField classIdField;
    private JTextField attendanceField;
    private JTextField assignmentField;
    private JTextField midtermField;
    private JTextField finalField;
    private JTextField semesterField;
    private JTextField academicYearField;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean success = false;

    public AddGradeDialog(Frame parent, String defaultClassId) {
        super(parent, "Thêm điểm mới", true);
        initializeComponents(defaultClassId);
        layoutComponents();
        addEventHandlers();
        setSize(500, 550);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents(String defaultClassId) {
        studentIdField = new JTextField(20);
        subjectIdField = new JTextField(20);
        classIdField = new JTextField(20);
        if (defaultClassId != null && !defaultClassId.isEmpty()) {
            classIdField.setText(defaultClassId);
        }
        attendanceField = new JTextField("0.0", 20);
        assignmentField = new JTextField("0.0", 20);
        midtermField = new JTextField("0.0", 20);
        finalField = new JTextField("0.0", 20);
        semesterField = new JTextField("HK1", 20);
        academicYearField = new JTextField("2024-2025", 20);

        saveButton = new JButton("Lưu");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(new Color(0x2C3E50));
        saveButton.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        saveButton.setFocusPainted(false);

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(0x2C3E50));
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        cancelButton.setFocusPainted(false);
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("THÊM ĐIỂM MỚI");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x2C3E50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Student ID
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Mã sinh viên:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(studentIdField, gbc);

        // Subject ID
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Mã môn học:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(subjectIdField, gbc);

        // Class ID
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(classIdField, gbc);

        // Attendance Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm chuyên cần (CC):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(attendanceField, gbc);

        // Assignment Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm bài tập (BT):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(assignmentField, gbc);

        // Midterm Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm giữa kỳ (GK):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(midtermField, gbc);

        // Final Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm cuối kỳ (CK):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(finalField, gbc);

        // Semester
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Học kỳ:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(semesterField, gbc);

        // Academic Year
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Năm học:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(academicYearField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void addEventHandlers() {
        saveButton.addActionListener(e -> saveGrade());
        cancelButton.addActionListener(e -> dispose());

        // Enter key to save
        getRootPane().setDefaultButton(saveButton);
    }

    private void saveGrade() {
        try {
            String studentId = studentIdField.getText().trim();
            String subjectId = subjectIdField.getText().trim();
            String classId = classIdField.getText().trim();
            Double attendance = Double.parseDouble(attendanceField.getText().trim());
            Double assignment = Double.parseDouble(assignmentField.getText().trim());
            Double midterm = Double.parseDouble(midtermField.getText().trim());
            Double finalScore = Double.parseDouble(finalField.getText().trim());
            String semester = semesterField.getText().trim();
            String academicYear = academicYearField.getText().trim();

            if (studentId.isEmpty() || subjectId.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Mã sinh viên và mã môn học không được để trống!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Grade grade = new Grade(null, studentId, subjectId, classId,
                    attendance, assignment, midterm, finalScore,
                    semester, academicYear);

            if (GradeService.createGrade(grade)) {
                JOptionPane.showMessageDialog(this,
                        "Thêm điểm thành công!\nĐiểm tổng kết: " + String.format("%.2f", grade.getTotalScore()) +
                                " (" + grade.getLetterGrade() + ")",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                success = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thêm điểm thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Điểm phải là số hợp lệ (0-10)!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}
