package app.ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TextFieldComponent extends JTextField {
    public TextFieldComponent(int columns) {
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setForeground(new Color(44, 62, 80));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        setColumns(columns);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(52, 152, 219), 2, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                setBackground(new Color(250, 252, 255));
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(189, 195, 199), 1, true),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
                setBackground(Color.WHITE);
            }
        });
    }
}
