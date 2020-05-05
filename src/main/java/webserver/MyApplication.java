package main.java.webserver;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/")
public class MyApplication extends Application {

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler;

    {
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    public MyApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("Who called System.exit() Shutdown Hook...?");
                /*try {
                    scheduler.shutdown(true);
                } catch (Throwable e) {
                    e.printStackTrace();
                }*/
            }

        }));
        this.singletons.add(new HallowJobClass());

    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.empty;
    }

    @Override
    public Set<Object> getSingletons(){
        return this.singletons;
    }


}