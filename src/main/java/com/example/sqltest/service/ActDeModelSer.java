package com.example.sqltest.service;

import com.example.sqltest.bean.ActDeModelBean;
import com.example.sqltest.dao.ActDeModelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by T016 on 2019/3/29.
 */
@Service
public class ActDeModelSer {

    @Autowired
    private ActDeModelDao actDeModelDao;

    public List<ActDeModelBean> findByModelKeys(List<String> modelKeys){
        return actDeModelDao.findByModelKeyIn(modelKeys);
    }
}
