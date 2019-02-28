package com.example.sqltest.service;

import com.example.sqltest.bean.UserBean;
import com.example.sqltest.dao.UserDao;
import com.example.sqltest.util.AESUtil;
import com.example.sqltest.util.MD5Util;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.security.Key;
import java.util.Date;

/**
 * Created by T016 on 2019/2/22.
 */
@Service
@PropertySource(value = {"classpath:/salt.properties"}, encoding = "UTF-8")
public class UserSer {

    @Value("${saltKey}")
    private String saltKey;

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private UserDao userDao;

    public void register(UserBean userBean) throws Exception {
        if(StringUtils.isEmpty(userBean.getUserName()) || StringUtils.isEmpty(userBean.getPassword())){
            throw new RuntimeException("Error, please re-register!");
        }
        if(!checkUserName(userBean.getUserName())){
            userBean.setPassword(MD5Util.md5(userBean.getPassword(),saltKey));
            userDao.save(userBean);
        }
    }

    public boolean checkUserName(String userName){
        if(null != userDao.findByUserName(userName)){
            return true;
        }
        return false;
    }

    public String login(UserBean userBean) throws Exception {
        System.out.println("账号：" + userBean.getUserName() + "密码：" + userBean.getPassword());
        if(StringUtils.isEmpty(userBean.getUserName()) || StringUtils.isEmpty(userBean.getPassword())){
            throw new RuntimeException("账号或密码为空");
        }
        userBean.setPassword(MD5Util.md5(userBean.getPassword(),saltKey));
        Example<UserBean> example = Example.of(userBean);
        return userDao.findOne(example).get().getUserid();
    }

    public String getKey(String sub){
        Date expirationDate = new Date();
        expirationDate.setTime(expirationDate.getTime()+30*60*1000);

        String jws = Jwts.builder().setSubject(sub).signWith(key).setExpiration(expirationDate).compact();
        jws = AESUtil.encrypt(jws,saltKey);

        return jws;
    }

    public boolean testJjwt(String Authorization){
        try {
            Authorization = AESUtil.decrypt(Authorization,saltKey);
            Jwts.parser().setSigningKey(key).parseClaimsJws(Authorization);
            return true;

        } catch (JwtException e) {
            System.out.println("error");
            return false;
        }
    }

}
