package app.ui.component;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Arrays;

public class TableComponent extends JPanel {

    private final DefaultTableModel model;
    private final JTable table;
    private final JScrollPane scrollPane;

    public TableComponent(String[] columnNames, int[] columnWidths) {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tạo model bảng
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho chỉnh sửa trực tiếp
            }
        };

        // tạo Jtable
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setBackground(new Color(250, 250, 250));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(new Color(220, 220, 220));

        // Style cho header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(51, 51, 51));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
        header.setReorderingAllowed(false);

        // Tạo độ rộng cho cột
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < Math.min(columnWidths.length, columnModel.getColumnCount()); i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Scroll Pane
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Shadow Panel
        JPanel shadowPanel = createShadowPanel(scrollPane);

        add(shadowPanel, BorderLayout.CENTER);
    }

    /** Tạo panel hiệu ứng đổ bóng */
    private JPanel createShadowPanel(JScrollPane scrollPane) {
        JPanel shadowPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int shadowSize = 8;
                g2.setPaint(new GradientPaint(
                        0, 0, new Color(0, 0, 0, 60),
                        shadowSize, shadowSize, new Color(0, 0, 0, 0)
                ));
                g2.fillRoundRect(shadowSize, shadowSize,
                        getWidth() - shadowSize * 2,
                        getHeight() - shadowSize * 2, 12, 12);
                g2.dispose();
            }
        };
        shadowPanel.setOpaque(false);
        shadowPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        shadowPanel.add(scrollPane, BorderLayout.CENTER);
        return shadowPanel;
    }

    /** Thêm 1 hàng không có dữ liệu */
    public void addRow() {
        int colCount = model.getColumnCount();
        Object[] emptyRow = new Object[colCount];
        Arrays.fill(emptyRow, "");
        model.addRow(emptyRow);
    }

    /** Thêm 1 hàng có dữ liệu */
    public void addRow(Object... rowData) {
        if (rowData.length != model.getColumnCount()) {
            JOptionPane.showMessageDialog(this, "Số lượng dữ liệu không khớp với số cột");
            return;
        }
        model.addRow(rowData);
    }

    /** Sửa hàng theo chỉ số */
    public void editRow(int index, Object... newData) {
        if (index < 0 || index >= model.getRowCount()) {
            JOptionPane.showMessageDialog(this, "Chỉ số không tồn tại/hợp lệ");
            return;
        }
        if (newData.length != model.getColumnCount()) {
            JOptionPane.showMessageDialog(this, "Số lượng dữ liệu không khớp với số cột");
            return;
        }
        for (int i = 0; i < newData.length; i++) {
            model.setValueAt(newData[i], index, i);
        }
    }

    /** Xoá hàng theo chỉ số */
    public boolean removeRow(int index) {
        if (index < 0 || index >= model.getRowCount()) return false;
        model.removeRow(index);
        return true;
    }

    /** Lấy JTable để tùy chỉnh thêm */
    public JTable getTable() {
        return table;
    }
}
