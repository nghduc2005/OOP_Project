package app.model;
import java.time.LocalDate;

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


    public Student(String studentId, String lastName, String firstName,
                   String userName, String password, String phoneNumber,
                   String email, LocalDate dateOfBirth) {
        super(userName, password);
        this.studentId = studentId;
        setLastName(lastName);
        setFirstName(firstName);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setDateOfBirth(dateOfBirth);
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
