package app.model;

import java.time.LocalDate;

import app.dto.request.LoginRequest;

public class Teacher extends User{
    private Integer teacherId;
    
    public Teacher(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }

    public Teacher(Integer teacherId, String lastName, String firstName,
                   String userName, String password, String phoneNumber,
                   String email, LocalDate dateOfBirth) {
        super(userName, password);
        this.teacherId = teacherId;
        setLastName(lastName);
        setFirstName(firstName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setDateOfBirth(dateOfBirth);
    }

    public Integer getTeacherId() { 
        return teacherId; 
    }
    
    public void setTeacherId(Integer teacherId) { 
        this.teacherId = teacherId; 
    }
}
