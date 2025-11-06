package app.ui.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class ButtonComponent extends JButton {
    private JButton button;
    public ButtonComponent(String context) {
        setText(context);
        //Lấy width theo text
        FontMetrics fm = new JLabel().getFontMetrics(new Font("Segoe UI", Font.BOLD, 14));
        int textWidth = fm.stringWidth(context);
        //Design button
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Màu gradient đẹp cho button
        setBackground(new Color(52, 152, 219)); // Xanh dương
        setForeground(Color.WHITE);
        setSize(textWidth+50, 40);
        
        // Hiệu ứng hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(41, 128, 185)); // Xanh đậm hơn khi hover
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(52, 152, 219));
            }
        });
    }
}
