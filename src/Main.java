import modele.CacheEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.*;

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
                    menu("cache");
                    break;
                case 2:
                    menu("utilisateur");
                    break;
                case 3:
                    menu("lieu");
                    break;
                case 4:
                    menu("visite");
                    break;
                case 5:
                    terminate = true;
                    break;
            }
        }
        System.out.println("Fin du programme");
    }


    public static void menu(String typeMenu) throws IOException
    {
        RepositoryInterface repository;
        String menuString;

        switch (typeMenu){
            case "cache":
                menuString = "une cache";
                repository = new CacheRepository(getSession());
                break;
            case "utilisateur":
                menuString = "un utilisateur";
                repository = new UtilisateurRepository(getSession());
                break;
            case "lieu":
                menuString = "un lieu";
                repository = new LieuRepository(getSession());
                break;
            case "visite":
                menuString = "une visite";
                repository = new VisiteRepository(getSession());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeMenu);
        }

        System.out.println("-----------------------------------------");
        System.out.println("1 - Ajouter " + menuString);
        System.out.println("2 - Modifier " + menuString);
        System.out.println("3 - Supprimer " + menuString);
        System.out.println("4 - Rechercher " + menuString);
        System.out.println("5 - Retour au menu précédent");


        String res = reader.readLine();

        System.out.println("-----------------------------------------");
        String id;
        Object object ;
        switch (Integer.parseInt(res)){
            case 1:
                System.out.println(1);
                break;
            case 2:
                // On récupere l'identifiant de lieux pour afficher les informations au testeur
                System.out.println("Entrez l'identifiant du lieux à modifier : ");
                id = reader.readLine();
                object = repository.findById(Integer.parseInt(id));
                System.out.println(object != null?object.toString():"Resultat non trouvé.");

                // On declare un libellé que nous recupérons sur la ligne de commande
                String libelle="";
                System.out.println("Entrez le nouveau libellé de lieux");

                //On vérifie que l'entrée n'est pas vide
                do
                    libelle = reader.readLine();
                while(("".equals(libelle)) || (libelle.length()>100));

                //On effectue les changements en base
                LieuRepository repoLieu = new LieuRepository(getSession());
                repoLieu.updateLieu(Integer.parseInt(id),libelle);
                break;
            case 3:
                System.out.println("Entrez l'identifiant à supprimer : ");
                id = reader.readLine();
                repository.deleteById(Integer.parseInt(id));

                break;
            case 4:
                System.out.println("Entrez l'identifiant recherché : ");
                id = reader.readLine();
                object = repository.findById(Integer.parseInt(id));
                System.out.println(object != null?object.toString():"Resultat non trouvé.");
                break;
            case 5:
                return;
        }
        System.out.println("Appuyer sur ENTER pour continuer.");
        reader.readLine();

    }
}