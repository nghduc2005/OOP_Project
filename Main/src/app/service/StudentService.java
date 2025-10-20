package app.service;


import app.Constant;
import app.dao.DatabaseConnection;
import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;
import app.model.LoginInterface;
import app.dao.StudentDao;

import java.util.HashMap;
import java.util.List;

public class StudentService implements LoginInterface {
    @Override
    public LoginResponse login(LoginRequest req) {
        List<HashMap<String, Object>> results = DatabaseConnection.readTable(
                "select username, password from students where username=" + String.format("'%s'", req.getUsername())
        );
        if(results==null || results.isEmpty()){
            return new LoginResponse("Không tồn tại tài khoản trong CSDL!", false);
        }
        System.out.println(results.get(0).get("username"));
        System.out.println(results.get(0).get("password"));
        String username = (String) results.get(0).get("username");
        String password = (String) results.get(0).get("password");
        if(username.equals(req.getUsername()) && password.equals(req.getPassword())) {
            return new LoginResponse("Đăng nhập thành công!", true);
        }
        return new LoginResponse("Sai tài khoản hoặc mật khẩu!", false);
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
        // STU****** với * thuộc [0-9]
        if(userName.length()!=9 || !userName.matches(Constant.STU_USERNAME_PATTERN)){
            throw new IllegalArgumentException("Tên đăng nhập không hợp lệ!");
        }
        // tối thiểu 6 ký tự, tối đa 20 ký tự, gồm chữ cái, chữ số, ít nhất 1 chữ in hoa
        if(password.length()<6 ||  password.length()>20 || !password.matches(Constant.STU_PASSWORD_PATTERN)){
            throw new IllegalArgumentException("Tên mật khẩu không hợp lệ!");
        }
        // Xác thực mặt dữ liệu
        LoginResponse response = login(new LoginRequest(userName, password));
        if(response==null) {
            throw new Error("Tài khoản hoặc mật khẩu không hợp lệ!");
        }
        return response;
    }

    /**
     * Lấy ds lớp học và điểm cho SV
     * @param studentId ID của sinh viên.
     * @return Dữ liệu lớp học và điểm
     */
    public List<HashMap<String, Object>> getClassAndScoreForStudent(int studentId) {
        StudentDao studentDao = new StudentDao();
        List<HashMap<String, Object>> data = studentDao.findClassAndScoreByStudentId(studentId);
        return data;
    }
}
