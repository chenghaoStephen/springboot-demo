package com.abc.quartz.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SchedulerQuartzJob2 implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        System.out.println("执行任务2" + "，任务名称:" + jobDetail.getKey().getName() + "，参数:" + jobDetail.getJobDataMap().get("param"));
    }
}
