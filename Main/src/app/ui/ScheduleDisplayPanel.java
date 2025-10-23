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

    public ScheduleDisplayPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        initializeComponents();
        layoutComponents();
        addEventHandlers();
        loadAllSchedules();
    }

    private void initializeComponents() {
        // Table
        String[] columnNames = {"Mã lịch", "Môn học", "Giảng viên", "Phòng", "Tòa",
                               "Ngày", "Giờ bắt đầu", "Giờ kết thúc", "Hình thức", "Lặp lại"};
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
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

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

        // Filter buttons
        todayButton.addActionListener(e -> loadTodaySchedules());
        weekButton.addActionListener(e -> loadWeekSchedules());
        monthButton.addActionListener(e -> loadMonthSchedules());
        allButton.addActionListener(e -> loadAllSchedules());

        // Action buttons
        addButton.addActionListener(e -> {
            if (mainPanel != null) {
                mainPanel.add(new AddScheduleForm(mainPanel), "AddSchedule");
                mainPanel.show("AddSchedule");
            }
        });

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
                schedule.getSubjectName(),
                schedule.getTeacherName(),
                schedule.getRoom(),
                schedule.getBuilding(),
                schedule.getScheduleDate().format(dateFormatter),
                schedule.getStartTime().format(timeFormatter),
                schedule.getEndTime().format(timeFormatter),
                schedule.getFormat(),
                schedule.getRepeatType()
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

        String scheduleId = (String) tableModel.getValueAt(selectedRow, 0);
        Schedule schedule = ScheduleService.getScheduleById(scheduleId);

        if (schedule != null && mainPanel != null) {
            mainPanel.add(new EditScheduleForm(mainPanel, schedule), "EditSchedule");
            mainPanel.show("EditSchedule");
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
}
