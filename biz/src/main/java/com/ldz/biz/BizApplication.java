package com.ldz.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.ldz")
public class BizApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizApplication.class, args);
		/*GnService bean = SpringContextUtil.getBean(GnService.class);
		bean.initPermission();*/
	}

}
