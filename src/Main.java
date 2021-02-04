import modele.CacheEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.CacheRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main
{
    private static final SessionFactory ourSessionFactory;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
        boolean terminate = false;
        while(!terminate)
        {
            System.out.println("Selectionnez en entrant le numéro du menu");
            System.out.println("-----------------------------------------");
            System.out.println("1 - Gestion des caches");
            System.out.println("2 - Gestion des utilisateurs");
            System.out.println("3 - Gestion des lieux");
            System.out.println("4 - Gestion des visites");
            System.out.println("5 - Quitter");

            String res = reader.readLine();

            System.out.println("-----------------------------------------");

            switch (Integer.parseInt(res))
            {
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
                case 5:
                    terminate = true;
                    break;
            }
        }
        System.out.println("Fin du programme");
    }


    public static void menu1() throws IOException
    {
        System.out.println("-----------------------------------------");
        System.out.println("1 - Ajouter une cache");
        System.out.println("2 - Modifier une cache");
        System.out.println("3 - Supprimer une cache");
        System.out.println("4 - Rechercher une cache");
        System.out.println("5 - Retour au menu précédent");



        String res = reader.readLine();

        System.out.println("-----------------------------------------");

        CacheRepository cacheRepository = new CacheRepository(getSession());

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
                System.out.println("Entrez l'identifiant de la cache recherchée : ");
                String id = reader.readLine();
                CacheEntity cache = cacheRepository.findCacheById(Integer.parseInt(id));

                System.out.println(cache != null?cache.toString():"Cache non trouvée.");
                break;
            case 5:
                return;
        }
        System.out.println("Appuyer sur ENTER pour continuer.");
        reader.readLine();


    }
}