package app.ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import app.ui.MainFrame;

public class AddSchedule extends JPanel {
    MainPanel mainPanel;
    JButton MakeSchedule = new JButton("Tạo lịch");
    public AddSchedule() {

        // Style button
        MakeSchedule.setFont(new Font("Segoe UI", Font.BOLD, 14));
        MakeSchedule.setBackground(Color.WHITE);
        MakeSchedule.setForeground(new Color(0x2C3E50));
        MakeSchedule.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
        MakeSchedule.setFocusPainted(false);
        MakeSchedule.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Sử dụng GridBagLayout để có sự linh hoạt trong việc sắp xếp
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL; // Các thành phần sẽ mở rộng theo chiều ngang
        // --- Hàng 1: Môn học và Phòng ---
        // Nhãn Môn học
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Môn học:" ), gbc);

        // ComboBox Môn học
        gbc.gridx = 1;
        // Có thể lấy từ database
        // String[] subjects = {"Lập trình hướng đối tượng", "Cấu trúc dữ liệu", "Cơ sở dữ liệu"};
        // add(new JComboBox<>(subjects), gcb)
        JTextField subject =  new JTextField();
        subject.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        add(subject , gbc);

        // Nhãn Phòng
        gbc.gridx = 2;
        add(new JLabel("Phòng học:"), gbc);

        // Nhập Phòng
        gbc.gridx = 3;
        add(new JTextField(), gbc);

        // --- Hàng 2: Giảng viên và Loại lịch ---
        // Nhãn Giảng viên
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Giảng viên:"), gbc);

        // ComboBox Giảng viên
        gbc.gridx = 1;
        // Lấy trong database
        String[] lecturers = {"Nguyễn Văn A", "Trần Thị B", "Lê Văn C"};
        add(new JComboBox<>(lecturers), gbc);

        // Nhãn tòa
        gbc.gridx = 2;
        add(new JLabel("Nơi học:"), gbc);

        // Chọn tòa
        gbc.gridx = 3;
        String[] building = {"Tòa A1", "Tòa A2", "Tòa A3", "Sân B1","Sân B5"};
        add(new JComboBox<>(building), gbc);
        // --- Hàng 3: Ngày và Thời gian bắt đầu ---
        // Nhãn Ngày
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Ngày:"), gbc);

        // Spinner chọn ngày
        gbc.gridx = 1;
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        add(dateSpinner, gbc);

        // Nhãn Thời gian bắt đầu
        gbc.gridx = 2;
        add(new JLabel("Bắt đầu:"), gbc);

        // Spinner chọn giờ bắt đầu
        gbc.gridx = 3;
        JSpinner startTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        startTimeSpinner.setEditor(new JSpinner.DateEditor(startTimeSpinner, "HH:mm"));
        add(startTimeSpinner, gbc);


        // --- Hàng 4: Lặp lại và Thời gian kết thúc ---
        // Checkbox Lặp lại
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JCheckBox("Lặp lại"), gbc);

        // ComboBox tần suất lặp lại
        gbc.gridx = 1;
        String[] repeatOptions = {"Hàng tuần", "Hàng ngày" , "Hàng tháng", "Hàng quý", "Hàng năm"};
        add(new JComboBox<>(repeatOptions), gbc);

        // Nhãn Thời gian kết thúc
        gbc.gridx = 2;
        add(new JLabel("Kết thúc:"), gbc);

        // Spinner chọn giờ kết thúc
        gbc.gridx = 3;
        JSpinner endTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        endTimeSpinner.setEditor(new JSpinner.DateEditor(endTimeSpinner, "HH:mm"));
        add(endTimeSpinner, gbc);

        // --- Hàng 5: Hình thức ---
        // Nhãn Hình thức
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Hình thức:"), gbc);

        // ComboBox Hình thức
        gbc.gridx = 1;
        gbc.gridwidth = 4;
        String[] formats = {"Offline", "Online", "Tự học"};
        add(new JComboBox<>(formats), gbc);

        // --- Hàng 6: Ghi chú ---
        // Nhãn Ghi chú
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.NORTH; // Căn lề trên cho nhãn "Ghi chú"
        add(new JLabel("Ghi chú:"), gbc);

        // Text Area cho Ghi chú
        gbc.gridx = 1;
        gbc.gridwidth = 3;  // Kéo dài qua 3 cột
        gbc.gridheight = 2; // Kéo dài qua 2 hàng
        gbc.fill = GridBagConstraints.BOTH; // Mở rộng cả ngang và dọc
        JTextArea noteArea = new JTextArea(4, 20);
        add(new JScrollPane(noteArea), gbc);

        // --- Hàng cuối: Nút Tạo ---
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 4; // Kéo dài qua 4 cột
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE; // Không co giãn nút
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa nút
        add(MakeSchedule, gbc);

        //

        MakeSchedule.addActionListener(e->{
            JOptionPane.showMessageDialog(
                    AddSchedule.this,
                    "Tạo lịch thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

//    public static void main(String[] args) {
//        MainFrame mainFrame = new MainFrame("Tạo lịch");
//        mainFrame.add(new AddSchedule());
//        int W = Toolkit.getDefaultToolkit().getScreenSize().width;
//        int H = Toolkit.getDefaultToolkit().getScreenSize().height;

//        mainFrame.setVisible(true);
//    }

}