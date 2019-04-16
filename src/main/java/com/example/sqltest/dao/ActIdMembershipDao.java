package com.example.sqltest.dao;

import com.example.sqltest.bean.ActIdMembershipBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by T016 on 2019/3/12.
 * @author T016
 */
public interface ActIdMembershipDao extends JpaRepository<ActIdMembershipBean, String> {
}
