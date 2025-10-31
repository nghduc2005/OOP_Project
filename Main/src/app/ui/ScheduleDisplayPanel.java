package app.ui;

import app.model.Schedule;
import app.service.ScheduleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScheduleDisplayPanel extends JPanel {
    private MainPanel mainPanel;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton refreshButton;
    private JButton todayButton;
    private JButton weekButton;
    private JButton monthButton;
    private JButton allButton;
    private JLabel statusLabel;
    
    // Advanced search components
    private JComboBox<String> searchTypeComboBox;
    private JButton advancedSearchButton;
    private JTextField subjectField;
    private JTextField classField;
    private JTextField classroomField;
    private JTextField dateField;
    private JDialog advancedSearchDialog;

    public ScheduleDisplayPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        initializeComponents();
        layoutComponents();
        addEventHandlers();
        loadAllSchedules();
    }

    private void initializeComponents() {
        // Table
        String[] columnNames = {"Mã lịch", "Môn học", "Lớp", "Phòng", "Ca học",
                               "Ngày", "Giờ bắt đầu", "Số buổi", "Hình thức", "Ghi chú"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        scheduleTable = new JTable(tableModel);
        scheduleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scheduleTable.setRowHeight(30);
        scheduleTable.getTableHeader().setReorderingAllowed(false);
        scheduleTable.setAutoCreateRowSorter(true);

        // Search components
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchButton.setFocusPainted(false);
        
        // Advanced search components
        searchTypeComboBox = new JComboBox<>(new String[]{
            "Tìm kiếm chung", "Theo môn học", "Theo lớp", "Theo phòng", "Theo ngày"
        });
        advancedSearchButton = new JButton("Tìm kiếm nâng cao");
        advancedSearchButton.setFocusPainted(false);

        // Filter buttons
        todayButton = new JButton("Hôm nay");
        weekButton = new JButton("Tuần này");
        monthButton = new JButton("Tháng này");
        allButton = new JButton("Tất cả");

        // Action buttons
        addButton = new JButton("Thêm lịch");
        addButton.setBackground(new Color(46, 125, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        editButton = new JButton("Sửa lịch");
        editButton.setBackground(new Color(25, 118, 210));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setEnabled(false);

        refreshButton = new JButton("Làm mới");
        refreshButton.setFocusPainted(false);

        // Status label
        statusLabel = new JLabel("Tổng số lịch: 0");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Quản Lý Lịch Học");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 33, 33));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Loại tìm kiếm:"));
        searchPanel.add(searchTypeComboBox);
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(advancedSearchButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(new JLabel("Lọc theo:"));
        filterPanel.add(todayButton);
        filterPanel.add(weekButton);
        filterPanel.add(monthButton);
        filterPanel.add(allButton);

        // Table Panel
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(refreshButton);

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(statusLabel);

        bottomPanel.add(actionPanel, BorderLayout.WEST);
        bottomPanel.add(statusPanel, BorderLayout.EAST);

        // Add all panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addEventHandlers() {
        // Table selection listener
        scheduleTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                editButton.setEnabled(scheduleTable.getSelectedRow() != -1);
            }
        });

        // Double click to edit
        scheduleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedSchedule();
                }
            }
        });

        // Search button
        searchButton.addActionListener(e -> searchSchedules());

        // Search field enter key
        searchField.addActionListener(e -> searchSchedules());
        
        // Advanced search button
        advancedSearchButton.addActionListener(e -> openAdvancedSearchDialog());

        // Filter buttons
        todayButton.addActionListener(e -> loadTodaySchedules());
        weekButton.addActionListener(e -> loadWeekSchedules());
        monthButton.addActionListener(e -> loadMonthSchedules());
        allButton.addActionListener(e -> loadAllSchedules());

        // Action buttons
        addButton.addActionListener(e -> openAddScheduleDialog());

        editButton.addActionListener(e -> editSelectedSchedule());

        refreshButton.addActionListener(e -> loadAllSchedules());
    }

    private void loadSchedules(List<Schedule> schedules) {
        tableModel.setRowCount(0);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Schedule schedule : schedules) {
            Object[] rowData = {
                schedule.getScheduleId(),
                schedule.getSubjectId() != null ? "Môn #" + schedule.getSubjectId() : "",
                schedule.getClassId() != null ? "Lớp #" + schedule.getClassId() : "",
                schedule.getClassroom() != null ? schedule.getClassroom() : "",
                schedule.getStudyShift() != null ? schedule.getStudyShift() : "",
                schedule.getStudyDate() != null ? schedule.getStudyDate().format(dateFormatter) : "",
                schedule.getStartTime() != null ? schedule.getStartTime().format(timeFormatter) : "",
                schedule.getTotalSessions() != null ? schedule.getTotalSessions() + " buổi" : "",
                schedule.getLearningMethod() != null ? schedule.getLearningMethod() : "",
                schedule.getNote() != null ? schedule.getNote() : ""
            };
            tableModel.addRow(rowData);
        }

        updateStatusLabel(schedules.size());
    }

    private void loadAllSchedules() {
        try {
            List<Schedule> schedules = ScheduleService.getAllSchedules();
            loadSchedules(schedules);
            highlightButton(allButton);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Không thể tải danh sách lịch học: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTodaySchedules() {
        try {
            List<Schedule> schedules = ScheduleService.getTodaySchedules();
            loadSchedules(schedules);
            highlightButton(todayButton);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Không thể tải lịch học hôm nay: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadWeekSchedules() {
        try {
            List<Schedule> schedules = ScheduleService.getThisWeekSchedules();
            loadSchedules(schedules);
            highlightButton(weekButton);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Không thể tải lịch học tuần này: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadMonthSchedules() {
        try {
            List<Schedule> schedules = ScheduleService.getThisMonthSchedules();
            loadSchedules(schedules);
            highlightButton(monthButton);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Không thể tải lịch học tháng này: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchSchedules() {
        String searchTerm = searchField.getText().trim();
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        
        try {
            List<Schedule> schedules;
            
            if (searchTerm.isEmpty()) {
                schedules = ScheduleService.getAllSchedules();
            } else {
                switch (searchType) {
                    case "Theo môn học":
                        schedules = ScheduleService.searchSchedulesBySubject(searchTerm);
                        break;
                    case "Theo lớp":
                        schedules = ScheduleService.searchSchedulesByClass(searchTerm);
                        break;
                    case "Theo phòng":
                        schedules = ScheduleService.searchSchedules(searchTerm); // Tìm theo phòng (logic cũ)
                        break;
                    case "Theo ngày":
                        schedules = ScheduleService.searchSchedulesByDate(searchTerm);
                        break;
                    default: // "Tìm kiếm chung"
                        schedules = ScheduleService.searchSchedules(searchTerm);
                        break;
                }
            }
            
            loadSchedules(schedules);
            resetButtonHighlight();
            updateStatusLabel(schedules.size());
        } catch (Exception e) {
            System.err.println("Search error: " + e.getMessage());
            e.printStackTrace();
            
            // Show user-friendly error message
            String errorMsg = "Không thể tìm kiếm lịch học";
            if (e.getMessage() != null && e.getMessage().contains("SQL")) {
                errorMsg += ". Có vấn đề với cơ sở dữ liệu.";
            } else {
                errorMsg += ": " + e.getMessage();
            }
            
            JOptionPane.showMessageDialog(this, errorMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);
            
            // Fallback: load all schedules
            try {
                List<Schedule> allSchedules = ScheduleService.getAllSchedules();
                loadSchedules(allSchedules);
                resetButtonHighlight();
            } catch (Exception fallbackError) {
                System.err.println("Fallback also failed: " + fallbackError.getMessage());
            }
        }
    }

    private void openAddScheduleDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        AddScheduleDialog dialog = new AddScheduleDialog(parentFrame);
        dialog.setVisible(true);
        
        // Refresh table if schedule was saved
        if (dialog.isSaved()) {
            loadAllSchedules();
        }
    }
    
    private void editSelectedSchedule() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn lịch học cần sửa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer scheduleId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Schedule schedule = ScheduleService.getScheduleById(scheduleId);

        if (schedule != null) {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            EditScheduleDialog dialog = new EditScheduleDialog(parentFrame, schedule);
            dialog.setVisible(true);
            
            // Refresh table if schedule was updated or deleted
            if (dialog.isUpdated() || dialog.isDeleted()) {
                loadAllSchedules();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin lịch học!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStatusLabel(int count) {
        statusLabel.setText("Tổng số lịch: " + count);
    }

    private void highlightButton(JButton button) {
        resetButtonHighlight();
        button.setBackground(new Color(63, 81, 181));
        button.setForeground(Color.WHITE);
    }

    private void resetButtonHighlight() {
        Color defaultBg = UIManager.getColor("Button.background");
        Color defaultFg = UIManager.getColor("Button.foreground");
        
        todayButton.setBackground(defaultBg);
        todayButton.setForeground(defaultFg);
        weekButton.setBackground(defaultBg);
        weekButton.setForeground(defaultFg);
        monthButton.setBackground(defaultBg);
        monthButton.setForeground(defaultFg);
        allButton.setBackground(defaultBg);
        allButton.setForeground(defaultFg);
    }

    public void refreshTable() {
        loadAllSchedules();
    }
    
    /**
     * Mở dialog tìm kiếm nâng cao
     */
    private void openAdvancedSearchDialog() {
        if (advancedSearchDialog == null) {
            createAdvancedSearchDialog();
        }
        
        // Reset fields
        subjectField.setText("");
        classField.setText("");
        classroomField.setText("");
        dateField.setText("");
        
        advancedSearchDialog.setVisible(true);
    }
    
    /**
     * Tạo dialog tìm kiếm nâng cao
     */
    private void createAdvancedSearchDialog() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        advancedSearchDialog = new JDialog(parentFrame, "Tìm kiếm nâng cao", true);
        advancedSearchDialog.setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Subject field
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Tên môn học:"), gbc);
        gbc.gridx = 1;
        subjectField = new JTextField(20);
        mainPanel.add(subjectField, gbc);
        
        // Class field
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1;
        classField = new JTextField(20);
        mainPanel.add(classField, gbc);
        
        // Classroom field
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Phòng học:"), gbc);
        gbc.gridx = 1;
        classroomField = new JTextField(20);
        mainPanel.add(classroomField, gbc);
        
        // Date field
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Ngày (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(20);
        mainPanel.add(dateField, gbc);
        
        // Help text
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JLabel helpLabel = new JLabel("<html><i>Gợi ý: Có thể để trống các trường không cần tìm.<br>" +
                "Ngày có thể tìm theo năm (2024), tháng-năm (10-2024), hoặc đầy đủ (2024-10-31)</i></html>");
        helpLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        helpLabel.setForeground(Color.GRAY);
        mainPanel.add(helpLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton searchBtn = new JButton("Tìm kiếm");
        JButton clearBtn = new JButton("Xóa");
        JButton cancelBtn = new JButton("Hủy");
        
        searchBtn.addActionListener(e -> performAdvancedSearch());
        clearBtn.addActionListener(e -> {
            subjectField.setText("");
            classField.setText("");
            classroomField.setText("");
            dateField.setText("");
        });
        cancelBtn.addActionListener(e -> advancedSearchDialog.setVisible(false));
        
        buttonPanel.add(searchBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(cancelBtn);
        
        advancedSearchDialog.add(mainPanel, BorderLayout.CENTER);
        advancedSearchDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        advancedSearchDialog.setSize(400, 300);
        advancedSearchDialog.setLocationRelativeTo(this);
    }
    
    /**
     * Thực hiện tìm kiếm nâng cao
     */
    private void performAdvancedSearch() {
        try {
            String subject = subjectField.getText().trim();
            String classId = classField.getText().trim();
            String classroom = classroomField.getText().trim();
            String date = dateField.getText().trim();
            
            // Validate at least one field is filled
            if (subject.isEmpty() && classId.isEmpty() && classroom.isEmpty() && date.isEmpty()) {
                JOptionPane.showMessageDialog(advancedSearchDialog,
                        "Vui lòng nhập ít nhất một tiêu chí tìm kiếm!",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validate date format if provided
            if (!date.isEmpty()) {
                if (!isValidDateFormat(date)) {
                    JOptionPane.showMessageDialog(advancedSearchDialog,
                            "Định dạng ngày không hợp lệ! Sử dụng: YYYY, MM-YYYY, hoặc YYYY-MM-DD",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            List<Schedule> schedules = ScheduleService.advancedSearch(
                subject.isEmpty() ? null : subject,
                classId.isEmpty() ? null : classId,
                classroom.isEmpty() ? null : classroom,
                date.isEmpty() ? null : date
            );
            
            loadSchedules(schedules);
            resetButtonHighlight();
            updateStatusLabel(schedules.size());
            
            advancedSearchDialog.setVisible(false);
            
            String message = schedules.size() > 0 
                ? "Tìm thấy " + schedules.size() + " kết quả phù hợp!"
                : "Không tìm thấy kết quả nào phù hợp với tiêu chí tìm kiếm.";
                
            JOptionPane.showMessageDialog(this, message, "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
                    
        } catch (Exception e) {
            System.err.println("Advanced search error: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(advancedSearchDialog,
                    "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Validate date format
     */
    private boolean isValidDateFormat(String date) {
        // Accept formats: YYYY, MM-YYYY, YYYY-MM-DD
        return date.matches("^\\d{4}$") ||                    // 2024
               date.matches("^\\d{1,2}-\\d{4}$") ||           // 12-2024
               date.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$");    // 2024-12-31
    }
}
