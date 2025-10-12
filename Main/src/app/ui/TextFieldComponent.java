package app.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TextFieldComponent extends JTextField {
    public TextFieldComponent(int columns) {
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206,212,218), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5,5)
        ));
        setColumns(columns);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 2, true),
                        BorderFactory.createEmptyBorder(5, 5, 5,5)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(206,212,218), 1, true),
                        BorderFactory.createEmptyBorder(5, 5, 5,5)
                ));
            }
        });
    }
}
