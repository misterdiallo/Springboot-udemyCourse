package com.alpha.graduatesmeeting.api.usersservice.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginUserRequestModel {

    @NotNull(message = "User Id can't not be empty")
    @Size(min = 2, message = "User Id should be at least 2 characters")
    private String userId;

    @NotNull(message = "Password can't not be empty")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
