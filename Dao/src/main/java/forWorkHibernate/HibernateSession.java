package forWorkHibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSession {
    private static SessionFactory sessionFactory;

    private HibernateSession() {
    }

    public static SessionFactory getSession() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(UserEntity.class);
            configuration.addAnnotatedClass(TaskEntity.class);
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(standardServiceRegistryBuilder.build());
        }
        return sessionFactory;
    }
}
