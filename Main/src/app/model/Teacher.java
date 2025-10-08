package app.model;

import app.dto.request.LoginRequest;

public class Teacher extends User{
    private String teacherId;
    Teacher(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }
}
