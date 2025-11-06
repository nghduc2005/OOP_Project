package app.ui;

import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JSeparator;
import javax.swing.JTextField;

import app.model.Grade;
import app.service.GradeService;

public class EditGradeDialog extends JDialog {
    private Grade grade;
    private JTextField studentIdField;
    private JTextField subjectIdField;
    private JTextField classIdField;
    private JTextField attendanceField;
    private JTextField assignmentField;
    private JTextField midtermField;
    private JTextField finalField;
    private JTextField semesterField;
    private JTextField academicYearField;
    private JLabel totalScoreLabel;
    private JLabel letterGradeLabel;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton calculateButton;
    private boolean success = false;

    public EditGradeDialog(Frame parent, Grade grade) {
        super(parent, "Sửa điểm", true);
        this.grade = grade;
        initializeComponents();
        layoutComponents();
        addEventHandlers();
        loadGradeData();
        setSize(550, 650);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        studentIdField = new JTextField(20);
        studentIdField.setEditable(false);
        studentIdField.setBackground(new Color(240, 240, 240));

        subjectIdField = new JTextField(20);
        subjectIdField.setEditable(false);
        subjectIdField.setBackground(new Color(240, 240, 240));

        classIdField = new JTextField(20);
        classIdField.setEditable(false);
        classIdField.setBackground(new Color(240, 240, 240));

        attendanceField = new JTextField("0.0", 20);
        assignmentField = new JTextField("0.0", 20);
        midtermField = new JTextField("0.0", 20);
        finalField = new JTextField("0.0", 20);
        semesterField = new JTextField("HK1", 20);
        academicYearField = new JTextField("2024-2025", 20);

        totalScoreLabel = new JLabel("0.00");
        totalScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalScoreLabel.setForeground(new Color(0x27AE60));

        letterGradeLabel = new JLabel("F");
        letterGradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        letterGradeLabel.setForeground(new Color(0x3498DB));

        calculateButton = new JButton("Tính điểm");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        calculateButton.setBackground(Color.WHITE);
        calculateButton.setForeground(new Color(0x2C3E50));
        calculateButton.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        calculateButton.setFocusPainted(false);
        calculateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        saveButton = new JButton("Lưu");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(new Color(0x2C3E50));
        saveButton.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(0x2C3E50));
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("SỬA ĐIỂM");
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

        // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        // Attendance Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm chuyên cần (10%):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(attendanceField, gbc);

        // Assignment Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm bài tập (20%):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(assignmentField, gbc);

        // Midterm Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm giữa kỳ (20%):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(midtermField, gbc);

        // Final Score
        gbc.gridy++;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Điểm cuối kỳ (50%):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(finalField, gbc);

        // Calculate button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(calculateButton, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Separator
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1;

        // Total Score
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel totalLabel = new JLabel("Điểm tổng kết:");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(new Color(0x2C3E50));
        mainPanel.add(totalLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(totalScoreLabel, gbc);

        // Letter Grade
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel letterLabel = new JLabel("Xếp loại:");
        letterLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        letterLabel.setForeground(new Color(0x2C3E50));
        mainPanel.add(letterLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(letterGradeLabel, gbc);

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
        calculateButton.addActionListener(e -> calculateScore());
        saveButton.addActionListener(e -> saveGrade());
        cancelButton.addActionListener(e -> dispose());

        // Enter key to save
        getRootPane().setDefaultButton(saveButton);

        // Auto-calculate when score fields change
        attendanceField.addActionListener(e -> calculateScore());
        assignmentField.addActionListener(e -> calculateScore());
        midtermField.addActionListener(e -> calculateScore());
        finalField.addActionListener(e -> calculateScore());
    }

    private void loadGradeData() {
        studentIdField.setText(grade.getStudentId());
        subjectIdField.setText(grade.getSubjectId());
        classIdField.setText(grade.getClassId());
        attendanceField.setText(String.valueOf(grade.getAttendanceScore()));
        assignmentField.setText(String.valueOf(grade.getAssignmentScore()));
        midtermField.setText(String.valueOf(grade.getMidtermScore()));
        finalField.setText(String.valueOf(grade.getFinalScore()));
        semesterField.setText(grade.getSemester());
        academicYearField.setText(grade.getAcademicYear());
        totalScoreLabel.setText(String.format("%.2f", grade.getTotalScore()));
        letterGradeLabel.setText(grade.getLetterGrade());
    }

    private void calculateScore() {
        try {
            Double attendance = Double.parseDouble(attendanceField.getText().trim());
            Double assignment = Double.parseDouble(assignmentField.getText().trim());
            Double midterm = Double.parseDouble(midtermField.getText().trim());
            Double finalScore = Double.parseDouble(finalField.getText().trim());

            grade.setAttendanceScore(attendance);
            grade.setAssignmentScore(assignment);
            grade.setMidtermScore(midterm);
            grade.setFinalScore(finalScore);

            totalScoreLabel.setText(String.format("%.2f", grade.getTotalScore()));
            letterGradeLabel.setText(grade.getLetterGrade());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Điểm phải là số hợp lệ (0-10)!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveGrade() {
        try {
            Double attendance = Double.parseDouble(attendanceField.getText().trim());
            Double assignment = Double.parseDouble(assignmentField.getText().trim());
            Double midterm = Double.parseDouble(midtermField.getText().trim());
            Double finalScore = Double.parseDouble(finalField.getText().trim());
            String semester = semesterField.getText().trim();
            String academicYear = academicYearField.getText().trim();

            grade.setAttendanceScore(attendance);
            grade.setAssignmentScore(assignment);
            grade.setMidtermScore(midterm);
            grade.setFinalScore(finalScore);
            grade.setSemester(semester);
            grade.setAcademicYear(academicYear);

            if (GradeService.updateGrade(grade)) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật điểm thành công!\nĐiểm tổng kết: " + String.format("%.2f", grade.getTotalScore()) +
                                " (" + grade.getLetterGrade() + ")",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                success = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật điểm thất bại!",
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
