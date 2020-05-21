package com.scaffolding.factories;

import com.scaffolding.configuration.HibernateEntitiesConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Properties;

@Component
public class HibernateSessionFactory {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(File fileName, String password) {
        if (fileName != null) {
            if (sessionFactory != null) sessionFactory.close();
            File dirPath = fileName.getParentFile();
            String path = "jdbc:h2:file:" + dirPath.getAbsolutePath();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "org.h2.Driver");
            properties.put(Environment.URL, path);
            properties.put(Environment.USER, "admin");
            properties.put(Environment.PASS, password);
            properties.put(Environment.POOL_SIZE, 1);
            properties.put(Environment.DEFAULT_SCHEMA, "PUBLIC");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
            properties.put(Environment.SHOW_SQL, true);
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            return new HibernateEntitiesConfiguration()
                    .setProperties(properties)
                    .buildSessionFactory();
        } else return null;
    }
}
