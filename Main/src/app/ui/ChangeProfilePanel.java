package app.ui;

import app.ui.component.ButtonComponent;
import app.ui.component.HeaderComponent;
import app.ui.component.TextFieldComponent;

import javax.swing.*;
import java.awt.*;

public class ChangeProfilePanel extends JPanel {
    MainPanel mainPanel;
    JLabel titleLabel, lastNameLabel, firstNameLabel, dateOfBirthLabel, emailLabel, phoneNumberLabel,
            exampleLastNameLabel,
            exampleFirstNameLabel, exampleDateOfBirthLabel, exampleEmailLabel, examplePhoneNumberLabel;
    JTextField lastNameInput, firstNameInput, dateOfBirthInput, emailInput, phoneNumberInput;
    JButton changeProfileButton, saveProfileButton;

    public ChangeProfilePanel(MainPanel mainPanel) {
        setLayout(new BorderLayout());
        this.mainPanel = mainPanel;
        titleLabel = new JLabel("Change Profile");
        titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), Font.BOLD, 32));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        lastNameLabel = new JLabel("Last Name:");
        firstNameLabel = new JLabel("First Name:");
        dateOfBirthLabel = new JLabel("Date of Birth:");
        emailLabel = new JLabel("Email:");
        phoneNumberLabel = new JLabel("Phone Number:");

        exampleLastNameLabel = new JLabel("Ví dụ: Nguyễn Văn");
        exampleFirstNameLabel = new JLabel("Ví dụ: A");
        exampleDateOfBirthLabel = new JLabel("Ví dụ: 1999-12-20");
        exampleEmailLabel = new JLabel("Ví dụ: abc@gmail.com");
        examplePhoneNumberLabel = new JLabel("Ví dụ: 0987654321");

        lastNameInput = new TextFieldComponent(15);
        firstNameInput = new TextFieldComponent(15);
        dateOfBirthInput = new TextFieldComponent(15);
        emailInput = new TextFieldComponent(15);
        phoneNumberInput = new TextFieldComponent(15);

        changeProfileButton = new ButtonComponent("Change Profile");
        changeProfileButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        changeProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveProfileButton = new ButtonComponent("Save");
        saveProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveProfileButton.setBorder(BorderFactory.createEmptyBorder(10, 38, 10, 38));

        JPanel containerFormPanel = new JPanel();
        containerFormPanel.setBackground(Color.lightGray);
        containerFormPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbMain = new GridBagConstraints();
        gbMain.anchor = GridBagConstraints.NORTHWEST;
        gbMain.fill = GridBagConstraints.HORIZONTAL;
        gbMain.ipadx = 50;
        gbMain.ipady = 50;
        gbMain.gridx = 0; gbMain.gridy = 0;

//        LeftForm
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.lightGray);
        GridBagConstraints gbLeft = new GridBagConstraints();
        gbLeft.insets = new Insets(10, 10, 10, 10);
        gbLeft.gridx = 0; gbLeft.gridy = 0; leftPanel.add(lastNameLabel, gbLeft);
        gbLeft.gridx = 1; leftPanel.add(lastNameInput, gbLeft);
        gbLeft.gridx = 0; gbLeft.gridy=1; leftPanel.add(firstNameLabel, gbLeft);
        gbLeft.gridx = 1; leftPanel.add(firstNameInput, gbLeft);
        gbLeft.gridx = 0; gbLeft.gridy = 2; leftPanel.add(dateOfBirthLabel, gbLeft);
        gbLeft.gridx = 1; leftPanel.add(dateOfBirthInput, gbLeft);

//        RightForm
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.lightGray);
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbRight = new GridBagConstraints();
        gbRight.insets = new Insets(10, 10, 10, 10);
        gbRight.gridx = 0; gbRight.gridy = 0; rightPanel.add(phoneNumberLabel, gbRight);
        gbRight.gridx = 1; rightPanel.add(phoneNumberInput, gbRight);
        gbRight.gridx = 0; gbRight.gridy=1; rightPanel.add(emailLabel, gbRight);
        gbRight.gridx = 1; rightPanel.add(emailInput, gbRight);

        containerFormPanel.add(leftPanel, gbMain);
        gbMain.gridx = 1;
        containerFormPanel.add(rightPanel, gbMain);

        JPanel containerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
        containerPanel.setLayout(boxLayout);
        containerPanel.add(titleLabel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPanel.add(containerFormPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        containerPanel.add(saveProfileButton);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        containerPanel.add(changeProfileButton);
        containerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        containerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.add(containerPanel, new GridBagConstraints());

        HeaderComponent headerComponent = new HeaderComponent(new String[]{"Trang chủ", "Thông tin cá nhân", "Quay " +
                "lại"},
                mainPanel);
        add(headerComponent, BorderLayout.NORTH);
        add(outerPanel, BorderLayout.CENTER);
    }
}
