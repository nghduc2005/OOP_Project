package app.ui;

import app.model.Grade;
import app.service.GradeService;
import app.ui.component.HeaderComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GradeManagementPanel extends JPanel {
    private MainPanel mainPanel;
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JButton searchButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JLabel statusLabel;
    private String currentClassId;

    public GradeManagementPanel(MainPanel mainPanel) {
        this(mainPanel, null);
    }

    public GradeManagementPanel(MainPanel mainPanel, String classId) {
        this.mainPanel = mainPanel;
        this.currentClassId = classId;
        initializeComponents();
        layoutComponents();
        addEventHandlers();
        loadGrades();
    }

    private void initializeComponents() {
        // Table
        String[] columnNames = {"Mã điểm", "Mã SV", "Môn học", "Lớp", "Điểm CC", "Điểm BT", "Điểm GK", "Điểm CK", "Tổng kết", "Xếp loại"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        gradeTable = new JTable(tableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTable.setRowHeight(30);
        gradeTable.getTableHeader().setReorderingAllowed(false);
        gradeTable.setAutoCreateRowSorter(true);

        // Search components
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchButton.setFocusPainted(false);

        // Filter
        String[] filterOptions = {"Tất cả", "Theo lớp", "Theo sinh viên", "Theo môn học"};
        filterComboBox = new JComboBox<>(filterOptions);

        // Action buttons
        addButton = new JButton("Thêm điểm");
        addButton.setBackground(new Color(46, 125, 50));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        editButton = new JButton("Sửa điểm");
        editButton.setBackground(new Color(25, 118, 210));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setEnabled(false);

        deleteButton = new JButton("Xóa điểm");
        deleteButton.setBackground(new Color(211, 47, 47));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setEnabled(false);

        refreshButton = new JButton("Làm mới");
        refreshButton.setFocusPainted(false);

        // Status label
        statusLabel = new JLabel("Tổng số điểm: 0");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Header
        HeaderComponent headerComponent = new HeaderComponent(
                new String[]{"Trang chủ", "Quản lý điểm", "Đăng xuất", "Quay lại"},
                mainPanel
        );
        add(headerComponent, BorderLayout.NORTH);

        // Title and search panel
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Quản Lý Điểm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 33, 33));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Lọc:"));
        searchPanel.add(filterComboBox);
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        // Combine top panels
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(Color.WHITE);
        northPanel.add(topPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.CENTER);

        // Table
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Bottom panel with status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.add(statusLabel, BorderLayout.WEST);

        // Add all to main panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(northPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void addEventHandlers() {
        // Table selection
        gradeTable.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = gradeTable.getSelectedRow() >= 0;
            editButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });

        // Search
        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());

        // Filter
        filterComboBox.addActionListener(e -> {
            String selected = (String) filterComboBox.getSelectedItem();
            if ("Tất cả".equals(selected)) {
                loadGrades();
            }
        });

        // Buttons
        addButton.addActionListener(e -> openAddGradeDialog());
        editButton.addActionListener(e -> openEditGradeDialog());
        deleteButton.addActionListener(e -> deleteSelectedGrade());
        refreshButton.addActionListener(e -> loadGrades());
    }

    private void loadGrades() {
        tableModel.setRowCount(0);
        List<Grade> grades;

        if (currentClassId != null && !currentClassId.isEmpty()) {
            grades = GradeService.getGradesByClassId(currentClassId);
        } else {
            // Load all grades - you might want to limit this
            grades = GradeService.getGradesByClassId(""); // This needs to be implemented in service
        }

        for (Grade grade : grades) {
            Object[] row = {
                    grade.getGradeId(),
                    grade.getStudentId(),
                    grade.getSubjectId(),
                    grade.getClassId(),
                    String.format("%.2f", grade.getAttendanceScore()),
                    String.format("%.2f", grade.getAssignmentScore()),
                    String.format("%.2f", grade.getMidtermScore()),
                    String.format("%.2f", grade.getFinalScore()),
                    String.format("%.2f", grade.getTotalScore()),
                    grade.getLetterGrade()
            };
            tableModel.addRow(row);
        }

        statusLabel.setText("Tổng số điểm: " + grades.size());
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadGrades();
            return;
        }

        tableModel.setRowCount(0);
        List<Grade> grades = GradeService.searchGrades(searchTerm);

        for (Grade grade : grades) {
            Object[] row = {
                    grade.getGradeId(),
                    grade.getStudentId(),
                    grade.getSubjectId(),
                    grade.getClassId(),
                    String.format("%.2f", grade.getAttendanceScore()),
                    String.format("%.2f", grade.getAssignmentScore()),
                    String.format("%.2f", grade.getMidtermScore()),
                    String.format("%.2f", grade.getFinalScore()),
                    String.format("%.2f", grade.getTotalScore()),
                    grade.getLetterGrade()
            };
            tableModel.addRow(row);
        }

        statusLabel.setText("Tìm thấy: " + grades.size() + " kết quả");
    }

    private void openAddGradeDialog() {
        AddGradeDialog dialog = new AddGradeDialog((Frame) SwingUtilities.getWindowAncestor(this), currentClassId);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            loadGrades();
        }
    }

    private void openEditGradeDialog() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow < 0) return;

        String gradeId = tableModel.getValueAt(selectedRow, 0).toString();
        Grade grade = GradeService.getGradeById(gradeId);

        if (grade != null) {
            EditGradeDialog dialog = new EditGradeDialog((Frame) SwingUtilities.getWindowAncestor(this), grade);
            dialog.setVisible(true);
            if (dialog.isSuccess()) {
                loadGrades();
            }
        }
    }

    private void deleteSelectedGrade() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow < 0) return;

        String gradeId = tableModel.getValueAt(selectedRow, 0).toString();
        String studentId = tableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa điểm của sinh viên " + studentId + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (GradeService.deleteGrade(gradeId)) {
                JOptionPane.showMessageDialog(this, "Xóa điểm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadGrades();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa điểm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
