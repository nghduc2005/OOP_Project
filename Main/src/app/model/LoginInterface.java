package app.model;

import app.dto.request.LoginRequest;
import app.dto.response.LoginResponse;


public interface LoginInterface {
    LoginResponse login(LoginRequest req);
    LoginResponse loginRequestValidate(LoginRequest req);
}


