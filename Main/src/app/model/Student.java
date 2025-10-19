package app.model;
import java.time.LocalDate;
import app.dto.request.LoginRequest;

public class Student extends User {
    private String studentId;

    public Student(LoginRequest request){
        super(request.getUsername(), request.getPassword());
    }

    public Student(String studentId, LoginRequest request){
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

    public Student(String studentId, String firstName, String lastName,
                   String email, String phoneNumber) {
        super("", "");
        this.studentId = studentId;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
}
