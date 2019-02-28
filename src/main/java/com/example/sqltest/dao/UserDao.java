package com.example.sqltest.dao;

import com.example.sqltest.bean.UserBean;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserBean, String> {
    UserBean findByUserName(String userName);
}