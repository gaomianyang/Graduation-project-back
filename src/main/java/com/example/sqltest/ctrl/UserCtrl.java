package com.example.sqltest.ctrl;

import com.example.sqltest.bean.UserBean;
import com.example.sqltest.dao.UserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.Date;


@RestController
@RequestMapping("/SqlTest")
public class UserCtrl {

    @Autowired
    private UserDao userDao;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    RedisConnectionFactory factory;

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    /**

     * 测试保存数据方法.

     * @return

     */

    @PostMapping("/findOne")
    public Object findUser(@RequestBody UserBean userBean){
        System.out.println("账号：" + userBean.getUserName() + "密码：" + userBean.getPassword());
        if(null == userBean.getUserName() || null == userBean.getPassword()){
            return new UserBean();
        }
        Example<UserBean> example = Example.of(userBean);
        return userDao.findOne(example).get();
    }

    @PostMapping("/testjjwt")
    public boolean testJjwt(@RequestHeader String Authorization){
        try {

            Jwts.parser().setSigningKey(key).parseClaimsJws(Authorization);
            return true;

        } catch (JwtException e) {

            System.out.println("error");
            return false;
        }
    }

    @GetMapping("/getkey")
    public String getKey(){
        Date expirationDate = new Date();
        expirationDate.setTime(expirationDate.getTime()+60*30000);

        String jws = Jwts.builder().setSubject("test").signWith(key).setExpiration(expirationDate).compact();

        return jws;
    }
}