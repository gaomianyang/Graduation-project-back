package com.example.sqltest.service;

import com.example.sqltest.bean.ActIdMembershipBean;
import com.example.sqltest.dao.ActIdMembershipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by T016 on 2019/3/12.
 */
@Service
public class ActIdMembershipSer {

    @Autowired
    private ActIdMembershipDao actIdMembershipDao;

    public ActIdMembershipBean findByUsername(String userName){
        Optional<ActIdMembershipBean> optional = actIdMembershipDao.findById(userName);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void save(ActIdMembershipBean actIdMembershipBean){
        actIdMembershipDao.save(actIdMembershipBean);
    }
}
