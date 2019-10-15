package com.ldz.biz.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ldz.biz.config.ScheduleComponent;
import com.ldz.biz.job.ExceptionDataJob;

/**
 * 在项目服务启动的时候启动计划任务
 */
@Component
public class ServerJobRunner implements CommandLineRunner {

	@Autowired
    private ScheduleComponent schedule;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//每日0点执行异常统计计划任务
		schedule.addJobAndStart(ExceptionDataJob.class, "0 0 0 1/1 * ? ", ExceptionDataJob.class.getName());
//		schedule.addJobAndStart(ExceptionDataJob.class, "0/59 * * * * ? ", ExceptionDataJob.class.getName());
	}
}
