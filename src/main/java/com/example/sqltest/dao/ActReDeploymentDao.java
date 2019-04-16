package com.example.sqltest.dao;

import com.example.sqltest.bean.ActReDeploymentBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by T016 on 2019/3/28.
 */
public interface ActReDeploymentDao extends JpaRepository<ActReDeploymentBean, String> {
}
