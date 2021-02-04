import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        menu();
        /*final Session session = getSession();
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

*//*
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
            }*//*
        } finally
        {
            session.close();
        }*/
    }


    public static void menu() throws IOException
    {
        System.out.println("Selectionnez en entrant le numéro du menu");
        System.out.println("-----------------------------------------");
        System.out.println("1 - Gestion des caches");
        System.out.println("2 - Gestion des utilisateurs");
        System.out.println("3 - Gestion des lieux");
        System.out.println("4 - Gestion des visites");
        //Enter data using BufferReader
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String res = reader.readLine();

        System.out.println("-----------------------------------------");

        switch (Integer.parseInt(res)){
            case 1:
                menu1();
                break;
            case 2:
                System.out.println(2);
                break;
            case 3:
                System.out.println(3);
                break;
            case 4:
                System.out.println(4);
                break;
        }
    }


    public static void menu1() throws IOException
    {
        System.out.println("-----------------------------------------");
        System.out.println("1 - Ajouter une cache");
        System.out.println("2 - Modifier une cache");
        System.out.println("3 - Supprimer une cache");
        System.out.println("4 - Rechercher une cache");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String res = reader.readLine();

        System.out.println("-----------------------------------------");

        switch (Integer.parseInt(res)){
            case 1:
                System.out.println(11);
                break;
            case 2:
                System.out.println(12);
                break;
            case 3:
                System.out.println(13);
                break;
            case 4:
                System.out.println(14);
                break;
        }
    }
}