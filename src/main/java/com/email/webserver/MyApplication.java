package main.java.com.email.webserver;

import main.java.com.email.webserver.EmailRestServer;

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
}
