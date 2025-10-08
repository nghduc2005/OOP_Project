package app.dto.response;

public class LoginResponse {
    public String message;
    public Boolean status;
    public LoginResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }
}
