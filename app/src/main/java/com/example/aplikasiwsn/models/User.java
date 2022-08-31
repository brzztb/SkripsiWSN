package com.example.aplikasiwsn.models;

public class User {
    private String username;
    private String token;
    private String loginDate;

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

    public String getLoginDate() {
        return this.loginDate;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setLogin_date(String loginDate) {
        this.loginDate = loginDate;
    }
}
