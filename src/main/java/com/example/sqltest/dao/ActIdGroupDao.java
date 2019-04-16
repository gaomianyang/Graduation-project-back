package com.example.sqltest.dao;

import com.example.sqltest.bean.ActIdGroupBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by T016 on 2019/3/12.
 * @author T016
 */
public interface ActIdGroupDao extends JpaRepository<ActIdGroupBean, String> {
}
