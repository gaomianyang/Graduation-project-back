package com.example.sqltest;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by T016 on 2019/3/4.
 */
public class TestCustomer {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        while(true){
            // 设置超时时间为0，表示无限期阻塞
            List<String> message = jedis.brpop(0, "test queue");
            System.out.println(message);
        }
    }
}
