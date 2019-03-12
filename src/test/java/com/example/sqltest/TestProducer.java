package com.example.sqltest;

import redis.clients.jedis.Jedis;

/**
 * Created by T016 on 2019/3/4.
 */
public class TestProducer {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        // 向键为“test queue”的值的左端推入数据
        jedis.lpush("test queue", "456456");
    }
}
