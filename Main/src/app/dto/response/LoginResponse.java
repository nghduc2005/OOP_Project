package app.dto.response;

public class LoginResponse {
    public String userName;
    public String password;
    public LoginResponse(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
