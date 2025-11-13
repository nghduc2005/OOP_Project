package app.ui;

import app.dao.ScheduleDao;
import app.model.Schedule;
import app.service.ScheduleService;
import app.ui.component.HeaderComponent;

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
    private JButton deleteButton;
    private JButton editButton;
    private JButton refreshButton;
    private JButton todayButton;
    private JButton weekButton;
    private JButton monthButton;
    private JButton allButton;
    private JLabel statusLabel;

    public ScheduleDisplayPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        initializeComponents();
        layoutComponents();
        addEventHandlers();
        loadAllSchedules();
    }

    private void initializeComponents() {
        // Table
        String[] columnNames = {"Mã lịch", "Môn học", "Phòng",
                               "Ngày", "Giờ bắt đầu", "Số tiết" ,"Hình thức", "Ghi chú"};
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
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK,
                        1), BorderFactory.createEmptyBorder(5, 0, 5, 0)));
        searchButton = new JButton("Tìm kiếm");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(new Color(0x2C3E50));
        searchButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        searchButton.setFocusPainted(false);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Filter buttons
        todayButton = new JButton("Hôm nay");
        todayButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        todayButton.setBackground(Color.WHITE);
        todayButton.setForeground(new Color(0x2C3E50));
        todayButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        todayButton.setFocusPainted(false);
        todayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        weekButton = new JButton("Tuần này");
        weekButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        weekButton.setBackground(Color.WHITE);
        weekButton.setForeground(new Color(0x2C3E50));
        weekButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        weekButton.setFocusPainted(false);
        weekButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        monthButton = new JButton("Tháng này");
        monthButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        monthButton.setBackground(Color.WHITE);
        monthButton.setForeground(new Color(0x2C3E50));
        monthButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        monthButton.setFocusPainted(false);
        monthButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        allButton = new JButton("Tất cả");
        allButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        allButton.setBackground(Color.WHITE);
        allButton.setForeground(new Color(0x2C3E50));
        allButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        allButton.setFocusPainted(false);
        allButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Action buttons
        addButton = new JButton("Thêm lịch");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(new Color(0x2C3E50));
        addButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        addButton.setFocusPainted(false);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        editButton = new JButton("Sửa lịch");
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(new Color(0x2C3E50));
        editButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        editButton.setFocusPainted(false);
        editButton.setEnabled(false);
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        deleteButton = new JButton("Xóa lịch");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(new Color(0x2C3E50));
        deleteButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        deleteButton.setFocusPainted(false);
        deleteButton.setEnabled(false);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        refreshButton = new JButton("Làm mới");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(new Color(0x2C3E50));
        refreshButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7),
                        1), BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Status label
        statusLabel = new JLabel("Tổng số lịch: 0");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(0x2C3E50));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
//        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        HeaderComponent headerComponent = new HeaderComponent(new String[]{
                "Trang chủ",
                "Lịch học",
                "Thông tin cá nhân",
                "Đổi mật khẩu",
                "Đăng xuất",
                "Quay lại"},
                mainPanel);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Quản Lý Lịch Học");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x2C3E50));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
//        searchPanel.add(new JLabel("Tìm kiếm:"));
//        searchPanel.add(searchField);
//        searchPanel.add(searchButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.WHITE);
//        filterPanel.add(new JLabel("Lọc theo:"));
//        filterPanel.add(todayButton);
//        filterPanel.add(weekButton);
//        filterPanel.add(monthButton);
//        filterPanel.add(allButton);

        // Table Panel
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
//        scrollPane.setBorder());
        scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(200, 200,
                200))));
        scrollPane.setBackground(Color.WHITE);
        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(Color.WHITE);
        actionPanel.add(addButton);
        actionPanel.add(editButton);
        actionPanel.add(refreshButton);
        actionPanel.add(deleteButton);

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(statusLabel);

        bottomPanel.add(actionPanel, BorderLayout.WEST);
        bottomPanel.add(statusPanel, BorderLayout.EAST);

        // Add all panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(headerComponent, BorderLayout.NORTH);
        topPanel.add(headerPanel, BorderLayout.CENTER);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addEventHandlers() {
        // Table selection listener
        scheduleTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = scheduleTable.getSelectedRow() != -1;
                editButton.setEnabled(hasSelection);
                deleteButton.setEnabled(hasSelection);
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

        // Filter buttons
        todayButton.addActionListener(e -> loadTodaySchedules());
        weekButton.addActionListener(e -> loadWeekSchedules());
        monthButton.addActionListener(e -> loadMonthSchedules());
        allButton.addActionListener(e -> loadAllSchedules());

        // Action buttons
        addButton.addActionListener(e -> {
            // Lấy frame cha của panel hiện tại
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

            AddScheduleDialog dialog = new AddScheduleDialog(frame);
            dialog.setVisible(true);

            // Sau khi dialog đóng lại, kiểm tra kết quả
            if (dialog.isSaved()) {
                // Cập nhật lại bảng, load dữ liệu mới, v.v.
                System.out.println("Lịch học mới đã được thêm!");
                // ví dụ: refreshScheduleTable();
            }
        });

        editButton.addActionListener(e -> editSelectedSchedule());

        refreshButton.addActionListener(e -> loadAllSchedules());

        deleteButton.addActionListener(e -> deleteSchedules());
    }

    private void loadSchedules(List<Schedule> schedules) {
        tableModel.setRowCount(0);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Schedule schedule : schedules) {
            Object[] rowData = {
                schedule.getScheduleId(),
                schedule.getSubjectName(),
                schedule.getClassroom(),
                schedule.getStudyDate().format(dateFormatter),
                schedule.getStartTime().format(timeFormatter),
                schedule.getTotalSessions(),
                schedule.getLearningMethod(),
                schedule.getNote()
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
            System.out.println(e.getMessage());
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
        try {
            List<Schedule> schedules = ScheduleService.searchSchedules(searchTerm);
            loadSchedules(schedules);
            resetButtonHighlight();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Không thể tìm kiếm lịch học: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
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

        String scheduleId =  String.valueOf( tableModel.getValueAt(selectedRow, 0));
        Schedule schedule = ScheduleService.getScheduleById(Integer.parseInt(scheduleId));

        if (schedule != null && mainPanel != null) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ScheduleDisplayPanel.this);
            EditScheduleDialog dialog = new EditScheduleDialog(frame, schedule);
            dialog.setVisible(true);
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

    private void deleteSchedules() {
        int selectedRow = scheduleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn lịch học cần sửa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String scheduleId =  String.valueOf( tableModel.getValueAt(selectedRow, 0));
        Schedule schedule = ScheduleService.getScheduleById(Integer.parseInt(scheduleId));

        if (schedule != null) {
            ScheduleDao.deleteSchedule(scheduleId);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin lịch học!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
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
}
