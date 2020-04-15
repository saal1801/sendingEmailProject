package main.java.com.email.webserver;

import main.java.com.email.webserver.EmailRestServer;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


//declares root resource and provider classes
@ApplicationPath("/")
public class MyApplication extends Application {

    /*private Set<Object> singletons = new HashSet<>();

    private Set<Class<?>> empty = new HashSet<>();

    private MyApplication(){

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Who called System.exit()...?");
            }
        }));
        singletons.add(new EmailRestServer());
    }

    @Override
    public Set<Class<?>> getClasses(){
        return this.empty;
    }

    @Override
    public Set<Object> getSingletons(){
        return this.singletons;
    }*/

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( EmailRestServer.class );
        return h;
    }

    JobDetail job = JobBuilder.newJob( HellowJob.class)
            .withIdentity("jobName", "jobGroup")
            .build();

    Trigger trigger = TriggerBuilder
            .newTrigger()
            .withIdentity("triggerName", "triggerGroup")
            .withSchedule(
                    CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
            .build();

    Scheduler scheduler;

    {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
