package app.ui.component;

import javax.swing.*;
import java.awt.*;

public class LabelComponent extends JLabel {
    public LabelComponent(String title, int size) {
        setFont(new Font("Arial", Font.BOLD, size));
        setText(title);
        setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    }
}
