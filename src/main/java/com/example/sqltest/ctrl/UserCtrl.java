package com.example.sqltest.ctrl;

import com.example.sqltest.bean.UserBean;
import com.example.sqltest.service.UserSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserSer userSer;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisConnectionFactory factory;


    @PostMapping("/login")
    public String login(@RequestBody UserBean userBean) throws Exception {
        return userSer.getKey(userSer.login(userBean));
    }

    @PostMapping("/register")
    public Object register(@RequestBody UserBean userBean) throws Exception {
        userSer.register(userBean);
        return "OK";
    }

    @GetMapping("/checkUserName")
    public boolean checkUserName(String userName){
        return userSer.checkUserName(userName);
    }


}