package com.example.sqltest.vo;

/**
 * Created by T016 on 2019/3/7.
 */
public class AdminVo {

    private boolean ifAdmin = true;

    private String adminPassword;

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public boolean isIfAdmin() {
        return ifAdmin;
    }
}
