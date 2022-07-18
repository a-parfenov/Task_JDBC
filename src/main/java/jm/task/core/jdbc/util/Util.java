package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

public class Util {
    private static Util instance = null;
    private static SessionFactory sessionFactory = null;

    private Util() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        try {
            Configuration config = new Configuration();
            sessionFactory = config.configure().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error util");
            e.printStackTrace();
        }
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
