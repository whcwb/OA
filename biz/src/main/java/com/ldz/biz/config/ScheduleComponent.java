package com.ldz.biz.config;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 动态计划任务管理工具
 * @author Lee
 *
 */
@Component
public class ScheduleComponent {

    Logger errorLog = LoggerFactory.getLogger("error_info");

    @Autowired
    private SchedulerFactoryBean schedulerFactory;
    
    /**
     * 任务只运行一次
     * @param cls
     * @param group
     * @throws Exception
     */
    public void addJobAndStartOnlyOne(Class<? extends Job> cls, String group) throws Exception{
    	JobConfig jobConfig = new JobConfig(cls, null, group);
        
		schedulerFactory.getScheduler().scheduleJob(jobConfig.getJobDetail(), jobConfig.getNotRepeatTrigger());
    }
    
    public void addJobAndStartWithTrigger(Class<? extends Job> cls, String group, SimpleTrigger trigger) throws Exception{
    	JobConfig jobConfig = new JobConfig(cls, null, group);
        
		schedulerFactory.getScheduler().scheduleJob(jobConfig.getJobDetail(), trigger);
    }
    
    /**
     * 动态添加计划任务
     * @param cls
     * @param cron
     * @param group
     * @throws Exception
     */
    public void addJobAndStart(Class<? extends Job> cls, String cron, String group) throws Exception{
    	JobConfig jobConfig = new JobConfig(cls,cron,group);
        
		schedulerFactory.getScheduler().scheduleJob(jobConfig.getJobDetail(), jobConfig.getCronTrigger());
    }
    
    /**
     * 删除计划任务
     * @param cls
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(Class<? extends Job> cls, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(cls.getName(), group);
        JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        
        schedulerFactory.getScheduler().deleteJob(jobKey);
    }
    
    /**
     * 检查任务是否存在
     * @param cls
     * @param group
     * @return
     * @throws SchedulerException
     */
    public boolean checkJobExist(Class<? extends Job> cls, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(cls.getName(), group);
        return schedulerFactory.getScheduler().checkExists(jobKey);
    }
}
