package app.util;

import org.mindrot.jbcrypt.BCrypt;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public final class StringUtil {
    //Chỉ dùng method không tạo instance
    private StringUtil() {}

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static LocalDate checkDate (String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            System.out.println("Định dạng ngày bạn nhập không chính xác!");
            return null;
        }
    }
}
