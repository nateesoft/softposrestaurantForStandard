package com.softpos.pos.core.model;

public class LoginBean {
    private String username;
    private String password;
    private String onact;
    private String macno;
    private boolean validLogin;
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOnact() {
        return onact;
    }

    public void setOnact(String onact) {
        this.onact = onact;
    }

    public String getMacno() {
        return macno;
    }

    public void setMacno(String macno) {
        this.macno = macno;
    }

    public boolean isValidLogin() {
        return validLogin;
    }

    public void setValidLogin(boolean validLogin) {
        this.validLogin = validLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
