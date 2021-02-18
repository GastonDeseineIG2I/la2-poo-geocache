package repository.JPA;

import com.mongodb.MongoClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryInterface;

import javax.persistence.EntityManager;

public abstract class JPARepository<T> implements RepositoryInterface<T>
{
    private static SessionFactory ourSessionFactory;

    static {

        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }




    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }
}
