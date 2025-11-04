package app.model;
import java.time.LocalDate;

import app.dto.request.LoginRequest;

public class Student extends User {
    private Integer studentId;
    private Integer classId;
    public Student(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }

    public Student(Integer studentId, LoginRequest request){
        super(request.getUsername(), request.getPassword());
        this.studentId = studentId;
    }

    public Student(Integer studentId, String lastName, String firstName, String fullName,
                   String userName, String password, String phoneNumber,
                   String email, LocalDate dateOfBirth) {
        super(userName, password);
        this.studentId = studentId;
        setFullName(fullName);
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
    public Integer getClassId() {
        return classId;
    }
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
}
