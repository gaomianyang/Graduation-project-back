package com.example.sqltest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by T016 on 2019/3/6.
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.stringRedisTemplate = this.stringRedisTemplate;
        redisUtil.redisTemplate = this.redisTemplate;
    }

    public static void setKeyValue(String key, String value, int timeOut){
        ValueOperations<String, String> operations = redisUtil.redisTemplate.opsForValue();
        operations.set(key, value, timeOut, TimeUnit.SECONDS);
    }

    public static String getValueByKey(String key){
        String cookie = redisUtil.stringRedisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(cookie)){
            cookie=cookie.substring(1, cookie.length()-1);
            return cookie;
        } else {
            return null;
        }
    }
}
