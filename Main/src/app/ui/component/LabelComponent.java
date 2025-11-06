package app.ui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class LabelComponent extends JLabel {
    public LabelComponent(String title, int size) {
        setFont(new Font("Segoe UI", Font.BOLD, size));
        setText(title);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        setForeground(new Color(44, 62, 80)); // Màu chữ xanh đậm
    }
}
