package app.ui.component;

import javax.swing.*;
import java.awt.*;

public class HeaderComponent extends JPanel {

    public HeaderComponent(String[] navItem) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(150, 150, 150)); // nền xám

        for(int i = 0; i< navItem.length; i++) {
            JLabel label = createLabel(navItem[i]);
            add(label);
        }
    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }
}
