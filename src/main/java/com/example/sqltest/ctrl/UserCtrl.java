package com.example.sqltest.ctrl;

import com.example.sqltest.bean.UserBean;
import com.example.sqltest.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;


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
    /**

     * 测试保存数据方法.

     * @return

     */

    @PostMapping("/findOne")
    public Object findUser(@RequestBody UserBean userBean){
        System.out.println("账号：" + userBean.getUserName() + "密码：" + userBean.getPassword());
        Example<UserBean> example = Example.of(userBean);
        return userDao.findOne(example).get();
    }
}