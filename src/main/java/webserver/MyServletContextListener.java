package main.java.webserver;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.UUID;

public class MyServletContextListener implements ServletContextListener {

    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        JobDetail jobDetail = JobBuilder.newJob(HallowJobClass.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .storeDurably().build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(jobDetail.getKey().getName(), "triggerGroup")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                /* SimpleScheduleBuilder.simpleSchedule()
                 .withIntervalInSeconds(4)
                 .repeatForever())*/
                .build();

        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
