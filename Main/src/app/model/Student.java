package app.model;

import app.dto.request.LoginRequest;

public class Student extends User{
    private String studentId;
    Student(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }
    Student(String studentId, LoginRequest request){
        super(request.getUsername(), request.getPassword());
        this.studentId = studentId;
    }
}
