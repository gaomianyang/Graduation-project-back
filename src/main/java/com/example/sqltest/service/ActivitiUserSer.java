package com.example.sqltest.service;

import com.example.sqltest.bean.ActivitiUserBean;
import com.example.sqltest.dao.ActivitiUserDao;
import com.example.sqltest.util.MD5Util;
import com.example.sqltest.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by T016 on 2019/3/7.
 */
@Service
@PropertySource(value = {"classpath:/salt.properties"}, encoding = "UTF-8")
public class ActivitiUserSer {

    @Autowired
    private ActivitiUserDao activitiUserDao;

    @Value("${saltKey}")
    private String saltKey;

    public void register(RegisterVo registerVo) throws Exception {
        ActivitiUserBean activitiUserBean = new ActivitiUserBean();
        activitiUserBean.setRev((long)1);
        BeanUtils.copyProperties(registerVo, activitiUserBean);
        activitiUserBean.setPassword(MD5Util.md5(activitiUserBean.getPassword(), saltKey));
        activitiUserDao.save(activitiUserBean);
    }

    public ActivitiUserBean findById(String id){
        return activitiUserDao.findById(id).get();
    }
}
