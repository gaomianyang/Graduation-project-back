package com.example.sqltest.dao;

import com.example.sqltest.bean.ActivitiUserBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by T016 on 2019/3/7.
 * @author T016
 */
public interface ActivitiUserDao extends JpaRepository<ActivitiUserBean, String> {
    ActivitiUserBean findByUserName(String userName);
}
