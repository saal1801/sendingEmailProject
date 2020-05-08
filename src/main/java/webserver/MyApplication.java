package main.java.webserver;


import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/")
public class MyApplication extends Application {

    private final Set<Object> singletons = new HashSet<>();
    private final Set<Class<?>> empty = new HashSet<>();

    public MyApplication() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Who called System.exit() Shutdown Hook...?")));
        this.singletons.add(new HallowJobClass());

    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }


}