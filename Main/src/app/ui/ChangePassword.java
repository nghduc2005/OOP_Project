package app.ui;

import app.dao.StudentDao;
import app.dao.TeacherDao;
import app.dto.request.ChangePasswordRequest;
import app.session.Session;
import app.ui.component.HeaderComponent;

import javax.swing.*;
import java.awt.*;

public class ChangePassword extends JPanel {
    private JLabel oldPasswordLabel, newPasswordLabel, reEnterPasswordLabel, changePasswordLabel;
    private JPasswordField oldPasswordField, newPasswordField, reEnterPasswordField;
    private JButton continueButton;
    private MainPanel mainPanel;

    public ChangePassword(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // ===== HEADER =====
        String[] navItems = {"Trang chủ", "Đăng xuất"};
        HeaderComponent header = new HeaderComponent(navItems, mainPanel);
        add(header, BorderLayout.NORTH);

        // ===== FORM =====
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(new Color(245, 245, 245));

        int W = Toolkit.getDefaultToolkit().getScreenSize().width;
        int H = Toolkit.getDefaultToolkit().getScreenSize().height;
        int y = 10;

        changePasswordLabel = new JLabel("Đổi mật khẩu");
        changePasswordLabel.setFont(new Font("Arial", Font.BOLD, 32));
        changePasswordLabel.setBounds(W / 2 - 100, H / 2 - 200 - y, 400, 50);

        oldPasswordLabel = new JLabel("Mật khẩu cũ");
        newPasswordLabel = new JLabel("Mật khẩu mới");
        reEnterPasswordLabel = new JLabel("Nhập lại mật khẩu");

        oldPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        reEnterPasswordField = new JPasswordField();

        oldPasswordLabel.setBounds(W / 2 - 230, H / 2 - 100 - y, 150, 30);
        newPasswordLabel.setBounds(W / 2 - 230, H / 2 - 60 - y, 150, 30);
        reEnterPasswordLabel.setBounds(W / 2 - 230, H / 2 - 20 - y, 150, 30);

        oldPasswordField.setBounds(W / 2 - 100, H / 2 - 100 - y, 230, 30);
        newPasswordField.setBounds(W / 2 - 100, H / 2 - 60 - y, 230, 30);
        reEnterPasswordField.setBounds(W / 2 - 100, H / 2 - 20 - y, 230, 30);

        continueButton = new JButton("Tiếp tục");
        continueButton.setForeground(Color.WHITE);
        continueButton.setBackground(new Color(66, 133, 244));
        continueButton.setBounds(W / 2 + 30, H / 2 + 20 - y, 100, 35);
        continueButton.addActionListener(e -> Change());

        // ===== Add to formPanel =====
        formPanel.add(changePasswordLabel);
        formPanel.add(oldPasswordLabel);
        formPanel.add(newPasswordLabel);
        formPanel.add(reEnterPasswordLabel);
        formPanel.add(oldPasswordField);
        formPanel.add(newPasswordField);
        formPanel.add(reEnterPasswordField);
        formPanel.add(continueButton);

        add(formPanel, BorderLayout.CENTER);
    }
    public void Change(){
        String pw =new String(this.oldPasswordField.getPassword());
        String check_pw = new String(this.reEnterPasswordField.getPassword());
        String new_pw = new String(this.newPasswordField.getPassword());
        String user_name = Session.getUsername();
        String correct_pw = "";
        if(Session.getRole().equals("Teacher")) correct_pw = TeacherDao.getPasswordByUsername(user_name);
        else correct_pw = StudentDao.getPasswordByUsername(user_name);
        // Chưa nhập đầy đủ
        if (pw.isEmpty() || check_pw.isEmpty() || new_pw.isEmpty()){
            JOptionPane.showMessageDialog(
                    ChangePassword.this,
                    "Vui lòng nhập đầy đủ ",
                    "Chú ý",
                    JOptionPane.WARNING_MESSAGE
            );
        }        // Mk nhập lại sai
        else if (!new_pw.equals(check_pw)){
            JOptionPane.showMessageDialog(
                    ChangePassword.this,
                    "Mật khẩu nhập lại khác ",
                    "Chú ý",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        else if (correct_pw.equals(pw)) {

            if (ChangePasswordRequest.updatePassword(user_name,new_pw)){
                JOptionPane.showMessageDialog(
                        ChangePassword.this,
                        "Đổi mật khẩu thành công ",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                        ChangePassword.this,
                        "Đã có lỗi xảy ra, vui lòng thử lại ",
                        "Thông báo",
                        JOptionPane.WARNING_MESSAGE
                );
            }


        } else{
            JOptionPane.showMessageDialog(
                    ChangePassword.this,
                    "Bạn đã nhập sai mật khẩu",
                    "Chú ý",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
//    public static void main(String[] args) {
//        MainFrame mainFrame = new MainFrame();
//        mainFrame.add(new ChangePassword(new MainPanel()));
//        int W = Toolkit.getDefaultToolkit().getScreenSize().width;
//        int H = Toolkit.getDefaultToolkit().getScreenSize().height;
//
//        mainFrame.setVisible(true);
//    }


}
