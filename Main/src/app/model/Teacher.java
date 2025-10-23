package app.model;

import app.dto.request.LoginRequest;
import java.time.LocalDate;

public class Teacher extends User{
    private String teacherId;
    
    public Teacher(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }

    public Teacher(String teacherId, String lastName, String firstName,
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

    public String getTeacherId() { 
        return teacherId; 
    }
    
    public void setTeacherId(String teacherId) { 
        this.teacherId = teacherId; 
    }
}
