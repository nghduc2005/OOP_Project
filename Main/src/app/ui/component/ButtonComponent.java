package app.ui.component;

import javax.swing.*;
import java.awt.*;

public class ButtonComponent extends JButton {
    private JButton button;
    public ButtonComponent(String context) {
        setText(context);
        //Lấy width theo text
        FontMetrics fm = new JLabel().getFontMetrics(new Font("Open Sans", Font.BOLD, 14));
        int textWidth = fm.stringWidth(context);
        //Design button
        setBorderPainted(false);
        setFocusPainted(false);

// Tuỳ chỉnh margin (đệm)
        setBackground(new Color(252, 249, 234));
        setSize(textWidth+24, 30);
    }
}
