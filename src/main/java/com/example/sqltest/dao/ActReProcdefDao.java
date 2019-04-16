package com.example.sqltest.dao;

import com.example.sqltest.bean.ActReProcdefBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by T016 on 2019/4/1.
 */
public interface ActReProcdefDao extends JpaRepository<ActReProcdefBean, String> {
    List<ActReProcdefBean> findByDeploymentIdIn(List<String> ids);
}
