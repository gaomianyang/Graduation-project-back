package com.example.sqltest.bean;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="users")
@Component
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UserBean {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name="id")
    private String userid;

    @Column(name="user_name")
    private String userName;

    @Column(name="pass_word")
    private String password;

    public UserBean() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}