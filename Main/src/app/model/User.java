package app.model;

import java.time.LocalDate;

abstract class User {
    protected String lastName;
    protected String firstName;
    protected String userName;
    protected String password;
    protected String phoneNumber;
    protected String email;
    protected LocalDate dateOfBirth;
    User() {
        this.lastName = "";
        this.firstName = "";
        this.userName = "";
        this.password = "";
        this.phoneNumber = "";
        this.email = "";
        this.dateOfBirth = null;
    }
}