package com.lanwantec;

import com.lanwantec.wechat.mybatis.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import(value = DynamicDataSourceRegister.class)
public class StartApplication  {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

}
