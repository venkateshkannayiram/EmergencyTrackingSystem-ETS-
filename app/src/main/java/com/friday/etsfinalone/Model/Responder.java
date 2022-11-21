package com.friday.etsfinalone.Model;

public class Responder {
    private String email;
    private String password;

    public Responder() {
    }

    public Responder(String email2, String password2) {
        this.email = email2;
        this.password = password2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }
}
