package com.example.sqltest.service;

import com.example.sqltest.bean.ActReProcdefBean;
import com.example.sqltest.dao.ActReProcdefDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by T016 on 2019/4/1.
 */
@Service
public class ActReProcdefSer {

    @Autowired
    private ActReProcdefDao actReProcdefDao;

    public List<ActReProcdefBean> findByIds(List<String> ids){
        return actReProcdefDao.findByDeploymentIdIn(ids);
    }
}
