package app.service;

import app.Constant;
import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;
import app.model.LoginInterface;

public class TeacherService implements LoginInterface {
    @Override
    public LoginResponse login(LoginRequest req) {
        return null;
    }

    @Override
    public LoginResponse loginRequestValidate(LoginRequest req) { //Xác thực mặt cấu trúc
        String userName = req.getUsername();
        String password = req.getPassword();
        if(userName.equals("")){
            throw new IllegalArgumentException("Tên đăng nhập là bắt buộc!");
        }
        if(password.equals("")){
            throw new IllegalArgumentException("Mật khẩu là bắt buộc!");
        }
        // TC****** với * thuộc [0-9]
        if(userName.length()!=8 || !userName.matches(Constant.TC_USERNAME_PATTERN)){
            throw new IllegalArgumentException("Tên đăng nhập không hợp lệ!");
        }
        // tối thiểu 6 ký tự, tối đa 20 ký tự, gồm chữ cái, chữ số, ít nhất 1 chữ in hoa
        if(password.length()<6 ||  password.length()>20 || !password.matches(Constant.TC_PASSWORD_PATTERN)){
            throw new IllegalArgumentException("Tên mật khẩu không hợp lệ!");
        }
        // Xác thực mặt dữ liệu
        LoginResponse response = login(new LoginRequest(userName, password));
        if(response==null) {
            throw new Error("Tài khoản hoặc mật khẩu không hợp lệ!");
        }
        return response;
    }
}
