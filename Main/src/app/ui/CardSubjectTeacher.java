package app.ui;

import app.model.Subject;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CardSubjectTeacher extends JPanel {
    private JLabel subjectNameLabel;
    private JLabel creditLabel;
    private JLabel groupLabel;

    public CardSubjectTeacher(Subject subject, String groupName) {
        setPreferredSize(new Dimension(200, 100));
        setBackground(new Color(150, 150, 150));
        setBorder(new CompoundBorder(
                new LineBorder(new Color(130, 130, 130), 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));

        setLayout(new BorderLayout()); // đổi sang border layout

        // --- Nút X ---
        JButton btnClose = new JButton("×");
        btnClose.setPreferredSize(new Dimension(20, 20));
        btnClose.setFont(new Font("Arial", Font.BOLD, 14));
        btnClose.setFocusable(false);
        btnClose.setMargin(new Insets(0,0,0,0));
        btnClose.setForeground(Color.WHITE);
        btnClose.setBackground(new Color(150,150,150));
        btnClose.setBorder(null);

        btnClose.addActionListener(e -> {
            Container parent = this.getParent();
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        });

        // Panel chứa nút X (đẩy sang phải)
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topRightPanel.setOpaque(false);
        topRightPanel.add(btnClose);

        add(topRightPanel, BorderLayout.NORTH); //  góc phải trên

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 0, 2, 0);

        subjectNameLabel = createLabel(subject.getSubjectName(), Font.BOLD, 13);
        creditLabel      = createLabel("Tín chỉ: " + subject.getCredit(), Font.PLAIN, 12);
        groupLabel       = createLabel("Nhóm: " + groupName, Font.PLAIN, 12);

        gbc.gridy = 0; content.add(subjectNameLabel, gbc);
        gbc.gridy = 1; content.add(creditLabel, gbc);
        gbc.gridy = 2; content.add(groupLabel, gbc);

        add(content, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(Color.WHITE);
        return label;
    }
}
