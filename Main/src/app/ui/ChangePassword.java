package app.ui;

import app.dao.*;
import app.dto.request.ChangePasswordRequest;
import app.session.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangePassword extends JPanel {
    JLabel OldPasswordLabel, NewPasswordLabel, Re_enterPasswordLabel, ChangePasswordLabel;
    JPasswordField OldPassword, NewPassword, Re_enterPassword;
    JButton ContinueButton;
    MainPanel mainPanel;
    public ChangePassword(MainPanel mainPanel){
        // Khai báo
        setLayout(null);
        this.mainPanel=mainPanel;
        ChangePasswordLabel = new JLabel("Đổi mật khẩu");
        OldPasswordLabel = new JLabel("Mật khẩu cũ");
        NewPasswordLabel = new JLabel("Mật khẩu mới");
        Re_enterPasswordLabel = new JLabel("Nhập lại mật khẩu");
        ContinueButton = new JButton("Tiếp tục");
        OldPassword = new JPasswordField();
        NewPassword = new JPasswordField();
        Re_enterPassword = new JPasswordField();

        // set vị trí , size

        int W = Toolkit.getDefaultToolkit().getScreenSize().width;
        int H = Toolkit.getDefaultToolkit().getScreenSize().height;
        int y=10;

        ChangePasswordLabel.setFont(new Font("Arial", Font.BOLD, 32));
        ChangePasswordLabel.setBounds(W/2-100,H/2-200-y,500,50);

        OldPasswordLabel.setBounds(W/2-230,H/2-100-y,100,30);
        NewPasswordLabel.setBounds(W/2-230,H/2-60-y,100,30);
        Re_enterPasswordLabel.setBounds(W/2-230,H/2-20-y,100,30);

        OldPassword.setBounds(W/2-100,H/2-100-y,230,30);
        NewPassword.setBounds(W/2-100,H/2-60-y,230,30);
        Re_enterPassword.setBounds(W/2-100,H/2-20-y,230,30);

        ContinueButton.setForeground(Color.WHITE);
        ContinueButton.setBackground(Color.BLUE);
        ContinueButton.setBounds(W/2-100+230-100,H/2-20-y+40,100,30);
        // action
        ContinueButton.addActionListener(e->Change());

        // add
        add(ChangePasswordLabel);
        add(OldPassword);
        add(OldPasswordLabel);
        add(NewPassword);
        add(NewPasswordLabel);
        add(Re_enterPassword);
        add(Re_enterPasswordLabel);
        add(ContinueButton);
    }
    public void Change(){
        String pw =new String(this.OldPassword.getPassword());
        String check_pw = new String(this.Re_enterPassword.getPassword());
        String new_pw = new String(this.NewPassword.getPassword());
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
