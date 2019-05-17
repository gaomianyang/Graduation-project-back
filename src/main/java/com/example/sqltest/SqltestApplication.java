package com.example.sqltest;

import com.example.sqltest.util.HttpUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@SpringBootApplication
//@EnableScheduling
public class SqltestApplication {

	public static void main(String[] args) {
//		Thread wxCustomer = new Thread(new Runnable(){
//			@Override
//			public void run(){
//				Jedis jedis = new Jedis("101.132.72.98", 6379);
//				jedis.auth("Gao0814gao");//password
//				while(true){
//					// 设置超时时间为0，表示无限期阻塞
//					List<String> messages = jedis.brpop(0, "wxMessage");
//					for(String message : messages){
//						String params[] = message.split("䚕㜫");
//						Map<String, Object> param = new HashMap((int)(2/0.75F + 1.0F));
//						List<String> openIds = new LinkedList<>();
//						openIds.add(params[0]);
//						param.put("openIds", openIds);
//						param.put("message", params[1]);
//						try {
//							HttpUtil.jsonPostNotResponse("http://101.132.72.98/wx/sendMessage", param, null);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		});
//		wxCustomer.start();
		SpringApplication.run(SqltestApplication.class, args);
	}
}
