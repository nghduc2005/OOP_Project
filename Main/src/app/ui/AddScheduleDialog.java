package app.ui;

import app.model.Schedule;
import app.service.ScheduleService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class AddScheduleDialog extends JDialog {
    private JTextField classroomField;
    private JComboBox<String> studyShiftComboBox;
    private JSpinner studyDateSpinner;
    private JSpinner totalSessionsSpinner;
    private JComboBox<String> learningMethodComboBox;
    private JSpinner startTimeSpinner;
    private JTextArea noteArea;
    private JSpinner subjectIdSpinner;
    private JSpinner classIdSpinner;
    
    private JButton saveButton;
    private JButton cancelButton;
    
    private boolean saved = false;

    public AddScheduleDialog(Frame parent) {
        super(parent, "Thêm Lịch Học Mới", true);
        initializeComponents();
        layoutComponents();
        addEventHandlers();
        
        setSize(550, 650);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initializeComponents() {
        // Classroom
        classroomField = new JTextField(20);
        
        // Study Shift
        String[] shifts = {"Sáng (7h-11h)", "Chiều (13h-17h)", "Tối (18h-21h)"};
        studyShiftComboBox = new JComboBox<>(shifts);
        
        // Study Date
        studyDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(studyDateSpinner, "dd/MM/yyyy");
        studyDateSpinner.setEditor(dateEditor);
        
        // Total Sessions
        totalSessionsSpinner = new JSpinner(new SpinnerNumberModel(15, 1, 100, 1));
        
        // Learning Method
        String[] methods = {"Offline", "Online", "Hybrid"};
        learningMethodComboBox = new JComboBox<>(methods);
        
        // Start Time
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTimeSpinner, "dd/MM/yyyy HH:mm");
        startTimeSpinner.setEditor(timeEditor);
        
        // Note
        noteArea = new JTextArea(4, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        
        // Subject ID
        subjectIdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        
        // Class ID
        classIdSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        
        // Buttons
        saveButton = new JButton("Lưu");
        saveButton.setBackground(new Color(76, 175, 80));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        
        cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
    }

    private void layoutComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        int row = 0;
        
        // Title
        JLabel titleLabel = new JLabel("THÊM LỊCH HỌC MỚI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Subject ID
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Mã môn học:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(subjectIdSpinner, gbc);
        row++;
        
        // Class ID
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(classIdSpinner, gbc);
        row++;
        
        // Study Date
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Ngày học:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(studyDateSpinner, gbc);
        row++;
        
        // Study Shift
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Ca học:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(studyShiftComboBox, gbc);
        row++;
        
        // Start Time
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Thời gian bắt đầu:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(startTimeSpinner, gbc);
        row++;
        
        // Total Sessions
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Tổng số buổi:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(totalSessionsSpinner, gbc);
        row++;
        
        // Classroom
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Phòng học:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(classroomField, gbc);
        row++;
        
        // Learning Method
        gbc.gridx = 0;
        gbc.gridy = row;
        mainPanel.add(new JLabel("Hình thức:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(learningMethodComboBox, gbc);
        row++;
        
        // Note
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(new JLabel("Ghi chú:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane noteScroll = new JScrollPane(noteArea);
        noteScroll.setPreferredSize(new Dimension(250, 100));
        mainPanel.add(noteScroll, gbc);
        row++;
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 8, 8, 8);
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }

    private void addEventHandlers() {
        saveButton.addActionListener(e -> saveSchedule());
        cancelButton.addActionListener(e -> dispose());
    }

    private void saveSchedule() {
        try {
            // Validate inputs
            if (classroomField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập phòng học!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get values
            Integer subjectId = (Integer) subjectIdSpinner.getValue();
            Integer classId = (Integer) classIdSpinner.getValue();
            
            Date dateValue = (Date) studyDateSpinner.getValue();
            LocalDate studyDate = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            String studyShift = (String) studyShiftComboBox.getSelectedItem();
            Integer totalSessions = (Integer) totalSessionsSpinner.getValue();
            String classroom = classroomField.getText().trim();
            String learningMethod = (String) learningMethodComboBox.getSelectedItem();
            
            Date startTimeValue = (Date) startTimeSpinner.getValue();
            LocalDateTime startTime = startTimeValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            
            String note = noteArea.getText().trim();
            
            // Create Schedule object (scheduleId will be auto-generated)
            Schedule schedule = new Schedule(null, studyDate, studyShift, totalSessions,
                learningMethod, classroom, note, subjectId, classId, startTime);
            
            // Save to database
            boolean success = ScheduleService.createSchedule(schedule);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Thêm lịch học thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm lịch học thất bại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
