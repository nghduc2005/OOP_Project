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

public class EditScheduleDialog extends JDialog {
    private Schedule schedule;
    
    private JLabel scheduleIdLabel;
    private JTextField classroomField;
    private JComboBox<String> studyShiftComboBox;
    private JSpinner studyDateSpinner;
    private JSpinner totalSessionsSpinner;
    private JComboBox<String> learningMethodComboBox;
    private JSpinner startTimeSpinner;
    private JTextArea noteArea;
    private JSpinner subjectIdSpinner;
    private JSpinner classIdSpinner;
    
    private JButton updateButton;
    private JButton deleteButton;
    private JButton cancelButton;
    
    private boolean updated = false;
    private boolean deleted = false;

    public EditScheduleDialog(Frame parent, Schedule schedule) {
        super(parent, "Chỉnh Sửa Lịch Học", true);
        this.schedule = schedule;
        
        initializeComponents();
        layoutComponents();
        loadScheduleData();
        addEventHandlers();
        
        setSize(550, 700);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void initializeComponents() {
        // Schedule ID (read-only)
        scheduleIdLabel = new JLabel();
        scheduleIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scheduleIdLabel.setForeground(new Color(25, 118, 210));
        
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
        updateButton = new JButton("Cập nhật");
        updateButton.setBackground(new Color(76, 175, 80));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        
        cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(158, 158, 158));
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
        JLabel titleLabel = new JLabel("CHỈNH SỬA LỊCH HỌC");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 118, 210));
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);
        
        // Schedule ID
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(scheduleIdLabel, gbc);
        row++;
        
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
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 8, 8, 8);
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }

    private void loadScheduleData() {
        if (schedule != null) {
            scheduleIdLabel.setText("Mã lịch: #" + schedule.getScheduleId());
            
            if (schedule.getSubjectId() != null) {
                subjectIdSpinner.setValue(schedule.getSubjectId());
            }
            
            if (schedule.getClassId() != null) {
                classIdSpinner.setValue(schedule.getClassId());
            }
            
            if (schedule.getStudyDate() != null) {
                Date date = Date.from(schedule.getStudyDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
                studyDateSpinner.setValue(date);
            }
            
            if (schedule.getStudyShift() != null) {
                studyShiftComboBox.setSelectedItem(schedule.getStudyShift());
            }
            
            if (schedule.getTotalSessions() != null) {
                totalSessionsSpinner.setValue(schedule.getTotalSessions());
            }
            
            if (schedule.getClassroom() != null) {
                classroomField.setText(schedule.getClassroom());
            }
            
            if (schedule.getLearningMethod() != null) {
                learningMethodComboBox.setSelectedItem(schedule.getLearningMethod());
            }
            
            if (schedule.getStartTime() != null) {
                Date startTime = Date.from(schedule.getStartTime().atZone(ZoneId.systemDefault()).toInstant());
                startTimeSpinner.setValue(startTime);
            }
            
            if (schedule.getNote() != null) {
                noteArea.setText(schedule.getNote());
            }
        }
    }

    private void addEventHandlers() {
        updateButton.addActionListener(e -> updateSchedule());
        deleteButton.addActionListener(e -> deleteSchedule());
        cancelButton.addActionListener(e -> dispose());
    }

    private void updateSchedule() {
        try {
            // Validate inputs
            if (classroomField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập phòng học!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update schedule object with new values
            schedule.setSubjectId((Integer) subjectIdSpinner.getValue());
            schedule.setClassId((Integer) classIdSpinner.getValue());
            
            Date dateValue = (Date) studyDateSpinner.getValue();
            schedule.setStudyDate(dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            
            schedule.setStudyShift((String) studyShiftComboBox.getSelectedItem());
            schedule.setTotalSessions((Integer) totalSessionsSpinner.getValue());
            schedule.setClassroom(classroomField.getText().trim());
            schedule.setLearningMethod((String) learningMethodComboBox.getSelectedItem());
            
            Date startTimeValue = (Date) startTimeSpinner.getValue();
            schedule.setStartTime(startTimeValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            
            schedule.setNote(noteArea.getText().trim());
            
            // Save to database
            boolean success = ScheduleService.updateSchedule(schedule);
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật lịch học thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                updated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật lịch học thất bại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void deleteSchedule() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa lịch học này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = ScheduleService.deleteSchedule(String.valueOf(schedule.getScheduleId()));
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa lịch học thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                deleted = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa lịch học thất bại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean isUpdated() {
        return updated;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
}
