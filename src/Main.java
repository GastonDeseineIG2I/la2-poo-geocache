import modele.UtilisateurEntity;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main
{
    private static final SessionFactory ourSessionFactory;

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

    public static void main(final String[] args) throws Exception
    {
        final Session session = getSession();
        try
        {
            System.out.println("Select utilisateurs");
            session.beginTransaction();


            Query q = session.createQuery("from UtilisateurEntity");

            for (Object o : q.list())
            {
                UtilisateurEntity utilisateur = (UtilisateurEntity) o;
                System.out.println("  " + utilisateur.getPseudo());
            }

            System.out.println("ajout utilisateur");

            UtilisateurEntity user = new UtilisateurEntity();
            user.setPseudo("test");
            user.setDescription("");
            user.setAvatar("default.png");

            session.persist(user);
            session.flush();

            System.out.println("Select utilisateurs");

            q = session.createQuery("from UtilisateurEntity");

            for (Object o : q.list())
            {
                UtilisateurEntity utilisateur = (UtilisateurEntity) o;
                System.out.println("  " + utilisateur.getPseudo());
            }

/*
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities())
            {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list())
                {
                    System.out.println("  " + o);
                }
            }*/
        } finally
        {
            session.close();
        }
    }
}