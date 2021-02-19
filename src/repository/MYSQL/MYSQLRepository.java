package repository.MYSQL;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryInterface;

public abstract class MYSQLRepository implements RepositoryInterface
{
    private static SessionFactory ourSessionFactory;

    static
    {

        try
        {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }


    }

    public static Session getSession() throws HibernateException
    {
        return ourSessionFactory.openSession();
    }
}
