package app.ui;

import app.model.Schedule;
import app.service.ScheduleService;
import app.dao.SubjectDao;
import app.dao.TeacherDao;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class EditScheduleForm extends JPanel {
    private MainPanel mainPanel;
    private Schedule schedule;
    
    // Form components
    private JLabel scheduleIdLabel;
    private JComboBox<String> subjectComboBox;
    private JComboBox<String> teacherComboBox;
    private JTextField roomField;
    private JComboBox<String> buildingComboBox;
    private JSpinner dateSpinner;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JCheckBox repeatCheckBox;
    private JComboBox<String> repeatTypeComboBox;
    private JComboBox<String> formatComboBox;
    private JTextArea noteArea;
    private JTextField groupIdField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton cancelButton;

    public EditScheduleForm(MainPanel mainPanel, Schedule schedule) {
        this.mainPanel = mainPanel;
        this.schedule = schedule;
        initializeComponents();
        layoutComponents();
        loadScheduleData();
        addEventHandlers();
    }

    private void initializeComponents() {
        // Schedule ID Label (read-only)
        scheduleIdLabel = new JLabel();
        scheduleIdLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Subject ComboBox
        subjectComboBox = new JComboBox<>();
        loadSubjects();

        // Teacher ComboBox
        teacherComboBox = new JComboBox<>();
        loadTeachers();

        // Room field
        roomField = new JTextField(15);

        // Building ComboBox
        String[] buildings = {"Tòa A1", "Tòa A2", "Tòa A3", "Tòa B1", "Tòa B2", 
                             "Sân B1", "Sân B5", "Phòng thực hành"};
        buildingComboBox = new JComboBox<>(buildings);

        // Date Spinner
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);

        // Time Spinners
        startTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);

        endTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);

        // Repeat options
        repeatCheckBox = new JCheckBox("Lặp lại");
        String[] repeatOptions = {"Không lặp", "Hàng tuần", "Hàng ngày", "Hàng tháng"};
        repeatTypeComboBox = new JComboBox<>(repeatOptions);
        repeatTypeComboBox.setEnabled(false);

        // Format ComboBox
        String[] formats = {"Offline", "Online", "Tự học"};
        formatComboBox = new JComboBox<>(formats);

        // Note area
        noteArea = new JTextArea(4, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);

        // Group ID field
        groupIdField = new JTextField(15);

        // Buttons
        updateButton = new JButton("Cập nhật");
        updateButton.setBackground(new Color(25, 118, 210));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        deleteButton = new JButton("Xóa lịch");
        deleteButton.setBackground(new Color(211, 47, 47));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(158, 158, 158));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Chỉnh Sửa Lịch Học");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 33, 33));
        
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(Color.WHITE);
        idPanel.add(new JLabel("Mã lịch: "));
        idPanel.add(scheduleIdLabel);
        
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(idPanel, BorderLayout.SOUTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Row 1: Subject and Teacher
        addFormRow(formPanel, gbc, row++, "Môn học:", subjectComboBox, "Giảng viên:", teacherComboBox);

        // Row 2: Room and Building
        addFormRow(formPanel, gbc, row++, "Phòng học:", roomField, "Tòa nhà:", buildingComboBox);

        // Row 3: Date and Start Time
        addFormRow(formPanel, gbc, row++, "Ngày học:", dateSpinner, "Bắt đầu:", startTimeSpinner);

        // Row 4: Repeat and End Time
        JPanel repeatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        repeatPanel.setBackground(Color.WHITE);
        repeatPanel.add(repeatCheckBox);
        repeatPanel.add(repeatTypeComboBox);
        addFormRow(formPanel, gbc, row++, "", repeatPanel, "Kết thúc:", endTimeSpinner);

        // Row 5: Format and Group ID
        addFormRow(formPanel, gbc, row++, "Hình thức:", formatComboBox, "Mã nhóm:", groupIdField);

        // Row 6: Note (spanning full width)
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Ghi chú:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        noteScrollPane.setPreferredSize(new Dimension(400, 100));
        formPanel.add(noteScrollPane, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        // Add all panels to main layout
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row,
                           String label1, Component comp1, String label2, Component comp2) {
        gbc.gridy = row;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // First label
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lbl1 = new JLabel(label1);
        lbl1.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lbl1, gbc);

        // First component
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(comp1, gbc);

        // Second label
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JLabel lbl2 = new JLabel(label2);
        lbl2.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lbl2, gbc);

        // Second component
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        panel.add(comp2, gbc);
    }

    private void loadScheduleData() {
        if (schedule == null) return;

        scheduleIdLabel.setText(schedule.getScheduleId());
        
        // Set subject
        subjectComboBox.setSelectedItem(schedule.getSubjectName());
        
        // Set teacher
        teacherComboBox.setSelectedItem(schedule.getTeacherName());
        
        // Set room and building
        roomField.setText(schedule.getRoom());
        buildingComboBox.setSelectedItem(schedule.getBuilding());
        
        // Set date
        LocalDate scheduleDate = schedule.getScheduleDate();
        Date date = Date.from(scheduleDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateSpinner.setValue(date);
        
        // Set times
        LocalTime startTime = schedule.getStartTime();
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, startTime.getHour());
        startCal.set(Calendar.MINUTE, startTime.getMinute());
        startTimeSpinner.setValue(startCal.getTime());
        
        LocalTime endTime = schedule.getEndTime();
        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.HOUR_OF_DAY, endTime.getHour());
        endCal.set(Calendar.MINUTE, endTime.getMinute());
        endTimeSpinner.setValue(endCal.getTime());
        
        // Set repeat type
        String repeatType = schedule.getRepeatType();
        if (repeatType != null && !repeatType.equals("Không lặp")) {
            repeatCheckBox.setSelected(true);
            repeatTypeComboBox.setEnabled(true);
            repeatTypeComboBox.setSelectedItem(repeatType);
        }
        
        // Set format
        formatComboBox.setSelectedItem(schedule.getFormat());
        
        // Set note
        if (schedule.getNote() != null) {
            noteArea.setText(schedule.getNote());
        }
        
        // Set group ID
        if (schedule.getGroupId() != null) {
            groupIdField.setText(schedule.getGroupId());
        }
    }

    private void addEventHandlers() {
        // Repeat checkbox handler
        repeatCheckBox.addActionListener(e -> {
            repeatTypeComboBox.setEnabled(repeatCheckBox.isSelected());
            if (!repeatCheckBox.isSelected()) {
                repeatTypeComboBox.setSelectedIndex(0);
            }
        });

        // Update button handler
        updateButton.addActionListener(e -> updateSchedule());

        // Delete button handler
        deleteButton.addActionListener(e -> deleteSchedule());

        // Cancel button handler
        cancelButton.addActionListener(e -> {
            if (mainPanel != null) {
                mainPanel.back();
            }
        });
    }

    private void loadSubjects() {
        try {
            var subjects = SubjectDao.getAllSubjects();
            subjectComboBox.removeAllItems();
            for (var subject : subjects) {
                subjectComboBox.addItem(subject.getSubjectName());
            }
        } catch (Exception e) {
            System.out.println("Không thể tải danh sách môn học: " + e.getMessage());
            subjectComboBox.addItem("Lập trình hướng đối tượng");
            subjectComboBox.addItem("Cấu trúc dữ liệu và giải thuật");
            subjectComboBox.addItem("Cơ sở dữ liệu");
        }
    }

    private void loadTeachers() {
        try {
            var teachers = TeacherDao.getAllTeachers();
            teacherComboBox.removeAllItems();
            for (var teacher : teachers) {
                String fullName = teacher.getLastName() + " " + teacher.getFirstName();
                teacherComboBox.addItem(fullName);
            }
        } catch (Exception e) {
            System.out.println("Không thể tải danh sách giảng viên: " + e.getMessage());
            teacherComboBox.addItem("Nguyễn Văn A");
            teacherComboBox.addItem("Trần Thị B");
            teacherComboBox.addItem("Lê Văn C");
        }
    }

    private void updateSchedule() {
        try {
            // Get data from form
            String subjectName = (String) subjectComboBox.getSelectedItem();
            String teacherName = (String) teacherComboBox.getSelectedItem();
            String room = roomField.getText().trim();
            String building = (String) buildingComboBox.getSelectedItem();
            
            // Convert Date to LocalDate
            Date dateValue = (Date) dateSpinner.getValue();
            LocalDate scheduleDate = dateValue.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Convert Date to LocalTime
            Date startTimeValue = (Date) startTimeSpinner.getValue();
            LocalTime startTime = startTimeValue.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();

            Date endTimeValue = (Date) endTimeSpinner.getValue();
            LocalTime endTime = endTimeValue.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();

            String repeatType = repeatCheckBox.isSelected() ? 
                    (String) repeatTypeComboBox.getSelectedItem() : "Không lặp";
            String format = (String) formatComboBox.getSelectedItem();
            String note = noteArea.getText().trim();
            String groupId = groupIdField.getText().trim();
            if (groupId.isEmpty()) {
                groupId = null;
            }

            // Validate input
            if (!ScheduleService.validateScheduleData(subjectName, teacherName, room, building,
                    scheduleDate, startTime, endTime)) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng kiểm tra lại thông tin đã nhập!",
                        "Lỗi dữ liệu",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update schedule object
            schedule.setSubjectName(subjectName);
            schedule.setTeacherName(teacherName);
            schedule.setRoom(room);
            schedule.setBuilding(building);
            schedule.setScheduleDate(scheduleDate);
            schedule.setStartTime(startTime);
            schedule.setEndTime(endTime);
            schedule.setRepeatType(repeatType);
            schedule.setFormat(format);
            schedule.setNote(note);
            schedule.setGroupId(groupId);

            // Check for conflicts
            if (ScheduleService.checkScheduleConflict(schedule)) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "Phòng học bị trùng lịch!\nBạn có muốn tiếp tục cập nhật lịch?",
                        "Cảnh báo xung đột",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Update in database
            boolean success = ScheduleService.updateSchedule(schedule);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật lịch học thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                if (mainPanel != null) {
                    mainPanel.back();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể cập nhật lịch học. Vui lòng thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSchedule() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa lịch học này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            boolean success = ScheduleService.deleteSchedule(schedule.getScheduleId());

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa lịch học thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                if (mainPanel != null) {
                    mainPanel.back();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa lịch học. Vui lòng thử lại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
