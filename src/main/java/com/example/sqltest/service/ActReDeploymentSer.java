package com.example.sqltest.service;

import com.example.sqltest.bean.ActReDeploymentBean;
import com.example.sqltest.dao.ActReDeploymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by T016 on 2019/3/27.
 */
@Service
public class ActReDeploymentSer {

    @Autowired
    private ActReDeploymentDao actReDeploymentDao;

    public List<ActReDeploymentBean> findAll(){
        return actReDeploymentDao.findAll();
    }
}
