package app.model;

import java.time.LocalDate;

abstract class User {
    protected String lastName;
    protected String firstName;
    protected String fullName;
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
        this.fullName = "";
        this.dateOfBirth = null;
    }
    User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getLastName() { return lastName; }
    public String getFullName() { return fullName; }
    public String getFirstName() { return firstName; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    // Setter methods
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}


