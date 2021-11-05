package com.alpha.graduatesmeeting.api.usersservice.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

    @NotNull(message = "Id Number can't not be null")
    @Size(min = 6, message = "Id Number should be at least 6 characters")
    private String userNumber;

    @NotNull(message = "First name can't not be null")
    @Size(min = 1, message = "First Name should be at least 1 character")
    private String firstName;

    @NotNull(message = "Last name can't not be null")
    @Size(min = 1, message = "Last Name should be at least 1 character")
    private String lastName;

    @NotNull(message = "Email can't not be null")
    @Size(min = 5, message = "Email should be at least 5 characters")
    @Email(message = "You must enter an valid email address")
    private String email;

    @NotNull(message = "Phone can't not be null")
    @Size(min = 5, message = "Phone should be at least 5 characters")
    private String phone;

    @NotNull(message = "Password can't not be null")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;


    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
