import modele.CacheEntity;
import modele.VisiteEntity;
import modele.CacheEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.expression.function.UpperFunction;
import repository.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final SessionFactory ourSessionFactory;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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

    public static void main(final String[] args) throws Exception {
        menu();
    }


    public static void menu() throws IOException {
        boolean terminate = false;
        while (!terminate) {
            System.out.println("Selectionnez en entrant le numéro du menu");
            System.out.println("-----------------------------------------");
            System.out.println("1 - Gestion des caches");
            System.out.println("2 - Gestion des utilisateurs");
            System.out.println("3 - Gestion des lieux");
            System.out.println("4 - Gestion des visites");
            System.out.println("5 - Quitter");
            String res;
            int choice;
            do
            {
                res = reader.readLine();

                choice = isNumeric(res);
            }while(choice > 5 || choice < 0);


            System.out.println("-----------------------------------------");

            switch (Integer.parseInt(res)) {
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


    public static void menu(String typeMenu) throws IOException {
        RepositoryInterface repository;
        String menuString;

        switch (typeMenu) {
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
        System.out.println("1 - Afficher tous les " + typeMenu + (typeMenu.equals("lieu") ?"x":"s"));
        System.out.println("2 - Ajouter " + menuString);
        System.out.println("3 - Modifier " + menuString);
        System.out.println("4 - Supprimer " + menuString);
        System.out.println("5 - Rechercher " + menuString);
        System.out.println("6 - Retour au menu précédent");
        String res ;
        int choice;
        do
        {
            res = reader.readLine();

            choice = isNumeric(res);
        }while(choice > 6 || choice < 0);


        System.out.println("-----------------------------------------");
        String id;
        Object object ;
        List<?> objectList;
        switch (Integer.parseInt(res)){
            case 1:
                objectList = repository.getAll();
                for (Object objectfromlist:objectList)
                {
                    System.out.println(objectfromlist != null?objectfromlist.toString():"");
                }
                break;
            case 2:
                System.out.println("ajouter");
                break;
            case 3:
                switch (menuString) {
                    case "une cache":
                        updateCache(repository);
                        break;
                    case "un lieu":
                        updateLieu(repository);
                        break;

                    case "un utilisateur":
                        // On récupere l'identifiant de l'utilisateur
                        updateUtilisateur(repository);
                        break;
                    case "une visite":
                        updateVisite(repository);


                        break;

                }
                break;
            case 4:
                System.out.println("Entrez l'identifiant à supprimer : ");
                id = reader.readLine();
                repository.deleteById(Integer.parseInt(id));

                break;
            case 5:
                System.out.println("Entrez l'identifiant rechercher : ");
                id = reader.readLine();
                object = repository.findById(Integer.parseInt(id));
                System.out.println(object != null ? object.toString() : "Resultat non trouvé.");
                break;
            case 6:
                return;
        }
        System.out.println("Appuyer sur ENTER pour continuer.");
        reader.readLine();

    }

    public static int isNumeric(String res){
        int numeric;
        try
        {
            numeric = Integer.parseInt(res);
        } catch (NumberFormatException nfe)
        {
            numeric = -1;
        }

        return numeric;
    }

    private static void updateUtilisateur(RepositoryInterface repository) throws IOException {
        System.out.println("Entrez l'identifiant de l'utilisateur à modifier : ");
        String id = reader.readLine();
        Object object = repository.findById(Integer.parseInt(id));
        System.out.println(object != null ? object.toString() : "Resultat non trouvé.");
        if (object != null) {
            System.out.println("Si une valeur est inchangée tapez sur entree");
            System.out.println("Entrez le nouveau pseudo ");
            String pseudo;
            do
                pseudo = reader.readLine();
            while (pseudo.length() > 50);
            System.out.println("Entrez la nouvelle description ");
            String descripton = reader.readLine();

            String avatar;
            System.out.println("Entrez le nouveau nom de l'image ");
            System.out.println("Ne pas oblier .png a la fin");
            do
                avatar = reader.readLine();
            while (avatar.length() > 255);
            UtilisateurRepository utilisateurRepo = new UtilisateurRepository(getSession());
            utilisateurRepo.updateUtilisateur(Integer.parseInt(id), pseudo, descripton, avatar);
        }
    }


    private static void updateLieu(RepositoryInterface repository) throws IOException {
        // On récupere l'identifiant de lieux pour afficher les informations au testeur
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Entrez l'identifiant du lieux à modifier : ");
        String id = reader.readLine();
        Object object = repository.findById(Integer.parseInt(id));
        System.out.println(object != null ? object.toString() : "Resultat non trouvé.");
        if (object != null) {
            // On declare un libellé que nous recupérons sur la ligne de commande
            String libelle = "";
            System.out.println("Entrez le nouveau libellé de lieux");

            //On vérifie que l'entrée n'est pas vide
            do
                libelle = reader.readLine();
            while (("".equals(libelle)) || (libelle.length() > 100));

            //On effectue les changements en base
            LieuRepository repoLieu = new LieuRepository(getSession());
            repoLieu.updateLieu(Integer.parseInt(id), libelle);
        }
    }

    private static void updateVisite(RepositoryInterface repository) throws IOException {
        System.out.println("Entrez l'identifiant de la visite à modifier : ");
        // A FAIRE : redemander si l'identifiant de visite n'est pas bon
        String idVisite = reader.readLine();
        Object object = repository.findById(Integer.parseInt(idVisite));
        System.out.println(object != null ? object.toString() : "Resultat non trouvé.");

        if (object != null) {
            System.out.println("Si une valeur est inchangée tapez sur entree");
            System.out.println("Entrez la nouvelle date et heure de visite");
            System.out.println("Format : YYYY-MM-DD hh:mm:ss");
            // A faire : Controle de la date
            String dateVisite;
            dateVisite = reader.readLine();


            System.out.println("Entrez l'id de l'utilisateur que a fait la visite  ");
            String utilisateurId;
            do {
                utilisateurId = reader.readLine();
                //On regarde si on a un utilisateur que existe
                UtilisateurRepository repoUtilisateur = new UtilisateurRepository(getSession());
                object = repoUtilisateur.findById(Integer.parseInt(utilisateurId));
                System.out.println(object != null ? object.toString() : "Utilisateur non trouvé.");
            } while (object == null);


            System.out.println("Entrez l'id de la cache'");
            String cacheId;
            do {
                cacheId = reader.readLine();
                //On regarde si on a un utilisateur que existe
                CacheRepository repoCache = new CacheRepository(getSession());
                object = repoCache.findById(Integer.parseInt(cacheId));
                System.out.println(object != null ? object.toString() : "Cache non trouvé.");
            } while (object == null);


            String commentaire;
            System.out.println("Entrez un commentaire'");
            commentaire = reader.readLine();

            String statut;
            System.out.println("Entrez un statut : En cours / Terminee'");

            do {
                statut = reader.readLine();
                // }while(("EN COURS".equals(statut.toUpperCase())) ||  ("TERMINEE".equals(statut.toUpperCase()) ));
            } while (!"EN COURS".equals(statut.toUpperCase()) && (!"TERMINEE".equals(statut.toUpperCase())));


            //DateTimeFormatter formatter= DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
            //LocalDateTime dateHeureVisite = LocalDateTime.parse(dateVisite, formatter);

            VisiteRepository visiteRepo = new VisiteRepository(getSession());
            // visiteRepo.updateVisite(Integer.parseInt(idVisite), dateHeureVisite,utilisateurId,cacheId,commentaire,statut);
            visiteRepo.updateVisite(Integer.parseInt(idVisite), dateVisite, utilisateurId, cacheId, commentaire, statut);
        }

    }


    private static void updateCache(RepositoryInterface repository) throws IOException {
        System.out.println("Entrez l'identifiant de la cache à modifier : ");
        String idCache = reader.readLine();
        Object object = repository.findById(Integer.parseInt(idCache));
        System.out.println(object != null ? object.toString() : "Resultat non trouvé.");

        if (object != null) {
            System.out.println("Si une valeur est inchangée tapez sur entree");
            System.out.println("Entrez la latitude");
            String latitude;
            latitude = reader.readLine();

            System.out.println("Entrez la longitude");
            String longitude;
            longitude = reader.readLine();

            System.out.println("Entrez la description");
            String description;
            description = reader.readLine();

            System.out.println("Entrez la nature PHYSIQUE / VIRTUELLE");
            String nature;
            do{
            nature = reader.readLine();
            } while (!"PHYSIQUE".equals(nature.toUpperCase()) && (!"VIRTUELLE".equals(nature.toUpperCase())) && (!"".equals(nature.toUpperCase())));


        System.out.println("Entrez le type de cache");
            System.out.println("TRADITIONNELLE / JEU DE PISTE / OBJET");
            String typeCache;
            do
                    typeCache = reader.readLine();
            while (!"TRADITIONNELLE".equals(typeCache.toUpperCase()) && (!"JEU DE PISTE".equals(typeCache.toUpperCase())) && (!"OBJET".equals(typeCache.toUpperCase())) && (!"".equals(typeCache.toUpperCase())));



            System.out.println("Entrez le code secret");
            String codeSecret;
            codeSecret = reader.readLine();

            System.out.println("Entrez le statut de la cache");
            System.out.println("ACTIVE / INACTIVE");
            String statut;
            do {
                statut = reader.readLine();
            } while (!"ACTIVE".equals(statut.toUpperCase()) && (!"INACTIVE".equals(statut.toUpperCase())) && (!"".equals(statut.toUpperCase())));


            System.out.println("Obligatoire : Entrez l'id du proprietaire de la cache  ");
            String proprietaireId;
            do {
                proprietaireId = reader.readLine();
                //On regarde si on a un utilisateur que existe
                UtilisateurRepository repoUtilisateur = new UtilisateurRepository(getSession());
                object = repoUtilisateur.findById(Integer.parseInt(proprietaireId));
                System.out.println(object != null ? object.toString() : "Propriétaire non trouvé.");
            } while (object == null);

            System.out.println("Obligatoire : Entrez l'id du lieu de la cache'");
            String lieuId;
            do {
                lieuId = reader.readLine();
                LieuRepository repoCache = new LieuRepository(getSession());
                object = repoCache.findById(Integer.parseInt(lieuId));
                System.out.println(object != null ? object.toString() : "lieu non trouvé.");
            } while (object == null);

            CacheRepository cacheRepo = new CacheRepository(getSession());
            // visiteRepo.updateVisite(Integer.parseInt(idVisite), dateHeureVisite,utilisateurId,cacheId,commentaire,statut);
            cacheRepo.updateCache(Integer.parseInt(idCache), latitude, longitude, description, nature, typeCache, statut, codeSecret, lieuId, proprietaireId);
        }
    }
}
