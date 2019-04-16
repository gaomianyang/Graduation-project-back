package com.example.sqltest.dao;

import com.example.sqltest.bean.ActDeModelBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by T016 on 2019/3/29.
 */
public interface ActDeModelDao extends JpaRepository<ActDeModelBean, String> {
    List<ActDeModelBean> findByModelKeyIn(List<String> modelKeys);
}
