package com.example.sqltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
//@EnableScheduling
public class SqltestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqltestApplication.class, args);
	}
}
