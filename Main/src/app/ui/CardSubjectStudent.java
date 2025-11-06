package app.ui;

import app.model.Subject;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CardSubjectStudent extends JPanel {
    private JLabel subjectNameLabel;
    private JLabel creditLabel;
    private JLabel teacherLabel;

    public CardSubjectStudent(Subject subject) {
        // Size, Background
        setPreferredSize(new Dimension(300, 150));
        setBackground(new Color(236, 240, 241)); // Màu xám sáng hiện đại
        setBorder(new CompoundBorder(
                new LineBorder(new Color(52, 152, 219), 2, true), // Viền xanh đẹp
                new EmptyBorder(10, 12, 10, 12)
        ));

        // Layout căn trái, cân giữa dọc
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;   // căn trái
        gbc.insets = new Insets(2, 0, 2, 0);

        // Label
        subjectNameLabel = createLabel(subject.getSubjectName(), Font.BOLD, 13);
        creditLabel = createLabel("Tín chỉ: " + subject.getCredit(), Font.PLAIN, 12);
        teacherLabel = createLabel("Giảng viên: " +
                (subject.getTeacherName().isEmpty() ? "Chưa phân công" : subject.getTeacherName()), Font.PLAIN, 12);

        gbc.gridy = 0; add(subjectNameLabel, gbc);
        gbc.gridy = 1; add(creditLabel, gbc);
        gbc.gridy = 2; add(teacherLabel, gbc);
    }

    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(new Color(44, 62, 80)); // Màu chữ xanh đậm
        return label;
    }
}
