package app.model;
import java.time.LocalDate;

import app.dto.request.LoginRequest;

public class Student extends User {
    private Integer studentId;

    public Student(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }

    public Student(Integer studentId, LoginRequest request){
        super(request.getUsername(), request.getPassword());
        this.studentId = studentId;
    }

    public Student(Integer studentId, String lastName, String firstName,
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

    public Student(Integer studentId, String firstName, String lastName,
                   String email, String phoneNumber) {
        super("", "");
        this.studentId = studentId;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
}
