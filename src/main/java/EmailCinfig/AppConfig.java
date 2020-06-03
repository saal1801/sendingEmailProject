package main.java.EmailCinfig;

import main.java.DAOService.SQLConClass;
import org.hibernate.cfg.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.util.Properties;

public class AppConfig {

    /*public static void proP() throws IOException, ClassNotFoundException {
        String propFileName = "config.properties";
        InputStream inputFile = SQLConClass.class.getClassLoader().getResourceAsStream(propFileName); // this = SQLConClass.class
        Properties emailConfig = new Properties();
        if (inputFile != null) {
            emailConfig.load(inputFile);
        } else {
            throw new FileNotFoundException("Property file '" + propFileName + "' no found in the classpath");
        }

            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setDriverClass((Class<? extends Driver>) Class.forName(emailConfig.getProperty("jdbc.driver")));//Class<Driver>
            dataSource.setUrl(emailConfig.getProperty("jdbc.url"));
            dataSource.setUsername(emailConfig.getProperty("jdbc.username"));
            dataSource.setPassword(emailConfig.getProperty("jdbc.password"));
        }*/
    }
