package app.ui;

import app.dao.SubjectDao;
import app.model.Classes;
import app.model.Subject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class SubjectPopup extends JDialog {


    private static final Color BG_WINDOW = Color.WHITE;
    private static final Color BG_CARD   = new Color(248, 249, 250);
    private static final Color OUTLINE   = new Color(220, 223, 228);
    private static final Color ACCENT    = new Color(59, 130, 246);   // xanh nhấn
    private static final Color TEXT_MAIN = new Color(33, 37, 41);
    private static final Color TEXT_SUB  = new Color(90, 96, 102);
    private static final Color ROW_ALT   = new Color(250, 251, 252);
    private static final Color ROW_SEL   = new Color(232, 242, 254);
    private static final Color FINAL_BG  = new Color(255, 249, 230);

    private static final String[] GRADE_TYPES = {
            "Điểm chuyên cần", "Điểm giữa kỳ", "Điểm thực hành", "Điểm cuối kỳ", "Điểm tổng kết"
    };
    private static final String[] GRADE_WEIGHTS = {"10%", "20%", "20%", "50%", "100%"};

    private Subject subject;
    private String studentId;  // Thêm studentId để lấy điểm
    private JTable gradesTable;
    private DefaultTableModel tableModel;
    private HashMap<String, Object> studentClassDetail;
    public SubjectPopup(JFrame parent, HashMap<String, Object> studentClassDetail, String studentId) {
        super(parent, "Thông tin môn học", true);
        this.studentClassDetail = studentClassDetail;

        this.studentId = studentId;
        initializeComponents();
        setupLayout();
        setupDialog();
    }

    private void initializeComponents() {
        String[] columnNames = {"STT", "Loại điểm", "Điểm số", "Hệ số"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        BigDecimal cc = new BigDecimal(studentClassDetail.get("attendence").toString());
        BigDecimal bt = new BigDecimal(studentClassDetail.get("assignment").toString());
        BigDecimal gk = new BigDecimal(studentClassDetail.get("midterm").toString());
        BigDecimal ck = new BigDecimal(studentClassDetail.get("final").toString());

        tableModel.addRow(new Object[]{1, "Chuyên cần", cc, "10%"});
        tableModel.addRow(new Object[]{2, "Bài tập", bt, "10%"});
        tableModel.addRow(new Object[]{3, "Giữa kỳ", gk, "20%"});
        tableModel.addRow(new Object[]{4, "Cuối kỳ", ck, "60%"});

        BigDecimal total =
                cc.add(bt)
                        .add(gk.multiply(new BigDecimal(2)))
                        .add(ck.multiply(new BigDecimal(6)))
                        .divide(new BigDecimal(10), RoundingMode.HALF_UP);
        tableModel.addRow(new Object[]{5, "Tổng kết", String.format("%.2f", total), "-"});

        gradesTable = new JTable(tableModel);
        setupTable();
    }

    private void setupTable() {
        gradesTable.setRowHeight(36);
        gradesTable.setFont(new Font("Inter", Font.PLAIN, 13));
        gradesTable.setShowGrid(false);
        gradesTable.setFillsViewportHeight(true);
        gradesTable.setSelectionBackground(ROW_SEL);
        gradesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradesTable.setAutoCreateRowSorter(true);

        TableColumnModel cm = gradesTable.getColumnModel();
        cm.getColumn(0).setPreferredWidth(56);
        cm.getColumn(1).setPreferredWidth(180);
        cm.getColumn(2).setPreferredWidth(90);
        cm.getColumn(3).setPreferredWidth(80);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        cm.getColumn(0).setCellRenderer(center);
        cm.getColumn(2).setCellRenderer(center);
        cm.getColumn(3).setCellRenderer(center);

        JTableHeader header = gradesTable.getTableHeader();
        header.setFont(new Font("Inter", Font.BOLD, 13));
        header.setBackground(BG_CARD);
        header.setForeground(TEXT_MAIN);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, OUTLINE));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 44));


        gradesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : ROW_ALT);
                }

                if (row == GRADE_TYPES.length - 1) {
                    c.setFont(getFont().deriveFont(Font.BOLD));
                    if (!isSelected) c.setBackground(FINAL_BG);
                } else {
                    c.setFont(getFont().deriveFont(Font.PLAIN));
                }

                if (c instanceof JLabel lbl) {
                    lbl.setForeground(TEXT_MAIN);
                    lbl.setHorizontalAlignment((column == 0 || column == 2 || column == 3)
                            ? JLabel.CENTER : JLabel.LEFT);
                    lbl.setBorder(new EmptyBorder(0, 10, 0, 10));
                }
                return c;
            }
        });
    }


    private void setupLayout() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        root.add(createHeaderPanel(), BorderLayout.NORTH);
        root.add(createTablePanel(),  BorderLayout.CENTER);
        root.add(createButtonPanel(),  BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 20, 8, 20));
        panel.setBackground(new Color(0xF5F7FA));


        JLabel titleLabel = new JLabel("THÔNG TIN CHI TIẾT MÔN HỌC");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0x2C3E50));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 8, 0));


        JPanel details = new JPanel(new GridBagLayout());
        details.setBackground(new Color(0xF5F7FA));
        details.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(2, 0, 6, 12);

        JLabel l1 = new JLabel("Tên môn học:");
        JLabel l2 = new JLabel("Số tín chỉ:");
        JLabel l3 = new JLabel("Nhóm:");
        for (JLabel l : new JLabel[]{l1, l2, l3}) {
            l.setFont(new Font("Segoe UI", Font.BOLD, 13));
            l.setForeground(new Color(0x2C3E50));
        }

        JLabel v1 = new JLabel(String.valueOf(studentClassDetail.get("subject_name")));
        JLabel v2 = new JLabel(String.valueOf(studentClassDetail.get("credit")));
        JLabel v3 = new JLabel(String.valueOf(studentClassDetail.get("class_id")));
        for (JLabel v : new JLabel[]{v1, v2, v3}) {
            v.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            v.setForeground(new Color(0x34495E));
        }

        details.add(l1, c);
        c.gridx = 1; details.add(v1, c);

        c.gridx = 0; c.gridy = 1; details.add(l2, c);
        c.gridx = 1; details.add(v2, c);

        c.gridx = 0; c.gridy = 2; details.add(l3, c);
        c.gridx = 1; details.add(v3, c);

        panel.add(titleLabel);
        panel.add(details);
        return panel;
    }


    private String nonNull(String s, String fallback) {
        return (s == null || s.isEmpty()) ? fallback : s;
    }

    private void addInfoRow(JPanel parent, String label, String value) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Inter", Font.BOLD, 13));
        l.setForeground(TEXT_SUB);

        JLabel v = new JLabel(value);
        v.setFont(new Font("Inter", Font.PLAIN, 13));
        v.setForeground(TEXT_MAIN);

        parent.add(l);
        parent.add(v);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(8, 20, 8, 20));
        panel.setBackground(Color.WHITE);

        JLabel tableTitle = new JLabel("Kết quả học tập");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableTitle.setForeground(new Color(0x2C3E50));
        tableTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        JScrollPane scrollPane = new JScrollPane(gradesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(tableTitle, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private JComponent createButtonPanel() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(BG_WINDOW);
        wrap.setBorder(new EmptyBorder(6, 20, 16, 20));

        JButton close = createRoundedButton("Đóng");
        close.addActionListener(e -> dispose());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(close);

        wrap.add(right, BorderLayout.EAST);
        return wrap;
    }

    private JButton createRoundedButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Inter", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(10, 16, 10, 16));

        b.addChangeListener(e -> {
            ButtonModel m = b.getModel();
            b.setBackground(m.isRollover() ? ACCENT.darker() : ACCENT);
        });

        b.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(b.getBackground());
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 12, 12);
                super.paint(g2, c);
                g2.dispose();
            }
        });
        return b;
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, OUTLINE),
                new EmptyBorder(16, 16, 16, 16)
        ));
        JPanel outer = new JPanel(new BorderLayout());
        outer.setOpaque(false);
        outer.add(card, BorderLayout.CENTER);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(12, 20, 0, 20));
        wrapper.add(card);
        return wrapper;
    }


    private void setupDialog() {
        setSize(560, 520);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


//    public static void showSubjectDetails(JFrame parent, Subject subject, String studentId) {
//        SwingUtilities.invokeLater(() -> new SubjectPopup(parent, subject, studentId).setVisible(true));
//    }


    public void updateSubject(Subject newSubject) {
        this.subject = newSubject;
        for (int i = 0; i < GRADE_TYPES.length; i++) {
            tableModel.setValueAt(String.format("%.1f", subject.getGrade(i)), i, 2);
        }
        tableModel.fireTableDataChanged();
        revalidate();
        repaint();
    }
}
