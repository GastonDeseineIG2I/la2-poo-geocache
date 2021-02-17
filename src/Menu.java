import modele.LieuEntity;
import modele.VisiteEntity;
import repository.*;
import repository.JPA.CacheRepository;
import repository.JPA.LieuRepository;
import repository.JPA.UtilisateurRepository;
import repository.JPA.VisiteRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu
{

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void menu() throws IOException
    {
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

                choice = Main.isNumeric(res);
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
                repository = new CacheRepository();
                break;
            case "utilisateur":
                menuString = "un utilisateur";
                repository = new UtilisateurRepository();
                break;
            case "lieu":
                menuString = "un lieu";
                repository = new LieuRepository();
                break;
            case "visite":
                menuString = "une visite";
                repository = new VisiteRepository();
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

        menuSpecifique(typeMenu, menuString);


        System.out.println("99 - Retour au menu précédent");
        String res ;
        int choice;
        do
        {
            res = reader.readLine();

            choice = Main.isNumeric(res);
        }while(choice > 99 || choice < 0);


        System.out.println("-----------------------------------------");
        String id;
        Object object ;
        switch (Integer.parseInt(res)){
            case 1:
                for (Object objectfromlist:repository.getAll())
                {
                    System.out.println(objectfromlist != null?objectfromlist.toString():"");
                }
                break;
            case 2:
                switch (typeMenu) {
                    case "cache":
                        createCache(repository);
                        break;
                    case "lieu":
                        createLieu(repository);
                        break;
                    case "utilisateur":
                        createUtilisateur(repository);
                        break;
                    case "visite":
                        createVisite(repository);
                        break;
                }
                break;
            case 3:
                switch (typeMenu) {
                    case "cache":
                        updateCache(repository);
                        break;
                    case "lieu":
                        updateLieu(repository);
                        break;

                    case "utilisateur":
                        // On récupere l'identifiant de l'utilisateur
                        updateUtilisateur(repository);
                        break;
                    case "visite":
                        updateVisite(repository);

                        break;

                }
                break;
            case 4:
                System.out.println("Entrez l'identifiant à supprimer : ");
                id = reader.readLine();
                repository.deleteById(id);

                break;
            case 5:
                System.out.println("Entrez l'identifiant rechercher : ");
                id = reader.readLine();
                object = repository.findById(id);
                System.out.println(object != null ? object.toString() : "Resultat non trouvé.");
                break;
            case 99:
                return;
            default:
                casSpecifique(typeMenu,Integer.parseInt(res), repository);
                break;
        }
        System.out.println("Appuyer sur ENTER pour continuer.");
        reader.readLine();

    }


    public static void menuSpecifique(String typeMenu, String menuString){
        switch (typeMenu) {
            case "cache":
                System.out.println("6 - Activer " + menuString);
                System.out.println("7 - Desactiver " + menuString);
                System.out.println("8 - Liste les caches d'un utilisateur " );
                System.out.println("9 - Liste les caches d'un lieu " );
                break;
            case "utilisateur":
                System.out.println("6 - Specifique " + menuString);
                break;
            case "lieu":
                System.out.println("6 - Specifique " + menuString);
                break;
            case "visite":
                System.out.println("6 - Valider " + menuString);
                System.out.println("7 - Recherche de visite en fonction d'une date");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeMenu);
        }
    }


    private static void casSpecifique(String typeMenu, int cas, RepositoryInterface repository) throws IOException
    {
        String id;
        Object object;
        switch (typeMenu) {
            case "cache":
                switch (cas){
                    case 6:
                        System.out.println("Entrez l'identifiant à activer : ");
                        id = reader.readLine();
                        object = repository.findById(id);
                        if(object != null){
                            ((CacheRepository)repository).activeCache(id);
                            System.out.println(object.toString());
                            System.out.println("La cache a été activée.");
                        }else{
                            System.out.println("Cette cache n'existe pas");
                        }
                    break;
                    case 7:
                        System.out.println("Entrez l'identifiant à desactiver : ");
                        id = reader.readLine();
                        object = repository.findById(id);
                        if(object != null){
                            ((CacheRepository)repository).desactiveCache(id);
                            System.out.println(object.toString());
                            System.out.println("La cache a été desactivée.");
                        }else{
                            System.out.println("Cette cache n'existe pas");
                        }

                    break;
                    case 8:
                        listeCacheProprietaire(((CacheRepository)repository));
                        break;
                    case 9:
                        listeCacheLieu(((CacheRepository)repository));
                        break;
                }
                break;
            case "utilisateur":
                switch (cas){

                }
                break;
            case "lieu":
                switch (cas){

                }
                break;
            case "visite":
                switch (cas){
                    case 6:
                        System.out.println("Entrez l'identifiant de la visite : ");
                        id = reader.readLine();
                        object = repository.findById(id);
                        System.out.println(object.toString());
                        if(object != null){
                            String code;
                            do
                            {
                                System.out.println(" Obligatoire : Code secret de la cache");
                                code = reader.readLine();
                            }while(!((VisiteRepository)repository).compareCodeSecret(""+((VisiteEntity)object).getCache().getId(), code));

                            String commentaire;
                            System.out.println(" Optionnel : Entrez un commentaire");
                            commentaire = reader.readLine();
                            ((VisiteRepository)repository).validerVisite(id, commentaire);
                            object = repository.findById(id);
                            System.out.println(object.toString());
                            System.out.println("La visite a été validée.");
                        }else{
                            System.out.println("Cette visite n'existe pas.");
                        }
                        break;
                    case 7:
                        listeVisiteParDate(((VisiteRepository)repository));
                }
                break;
            default:
                return;
        }
    }

    private static void listeVisiteParDate(VisiteRepository repository) throws IOException {
        System.out.println("Entrez la date de visite :");
        System.out.println("Format YYYY-MM-dd:");
        String dateVisite ;
        Object object;
        dateVisite = reader.readLine();


        for (Object objectfromlist:(repository).getVisiteByDate(dateVisite))
        {
            System.out.println(objectfromlist != null?objectfromlist.toString():"");
        }

    }


    private static void listeCacheLieu(CacheRepository repository) throws IOException {
        System.out.println("Entre l'identifiant du lieu pour afficher ses caches");
        String id ;
        Object object;
        do {
            id = reader.readLine();
            LieuRepository repoLieux = new LieuRepository();
            object = repoLieux.findById(id);
            System.out.println(object != null ? object.toString() : "Lieu non trouvé.");
        } while (object == null);
        for (Object objectfromlist:((LieuEntity)object).getCaches())
        {
            System.out.println(objectfromlist != null?objectfromlist.toString():"");
        }

    }
    private static void listeCacheProprietaire(CacheRepository repository) throws IOException {
        System.out.println("Entre l'identifiant de l'utilisateur pour afficher ses caches");
        String id ;
        Object object;
        do {
            id = reader.readLine();
            UtilisateurRepository repoUtilisateur = new UtilisateurRepository();
            object = repoUtilisateur.findById(id);
            System.out.println(object != null ? object.toString() : "Utilisateur non trouvé.");
        } while (object == null);
        for (Object objectfromlist:(repository).getCacheByProprietaire(id))
        {
            System.out.println(objectfromlist != null?objectfromlist.toString():"");
        }

    }


    private static void updateUtilisateur(RepositoryInterface repository) throws IOException {
        System.out.println("Entrez l'identifiant de l'utilisateur à modifier : ");
        String id = reader.readLine();
        Object object = repository.findById(id);
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


            ((UtilisateurRepository)repository).updateUtilisateur(id, pseudo, descripton, avatar);
        }
    }
    private static void updateCache(RepositoryInterface repository) throws IOException {
        System.out.println("Entrez l'identifiant de la cache à modifier : ");
        String idCache = reader.readLine();
        Object object = repository.findById(idCache);
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
                UtilisateurRepository repoUtilisateur = new UtilisateurRepository();
                object = repoUtilisateur.findById(proprietaireId);
                System.out.println(object != null ? object.toString() : "Propriétaire non trouvé.");
            } while (object == null);

            System.out.println("Obligatoire : Entrez l'id du lieu de la cache");
            String lieuId;
            do {
                lieuId = reader.readLine();
                LieuRepository repoCache = new LieuRepository();
                object = repoCache.findById(lieuId);
                System.out.println(object != null ? object.toString() : "lieu non trouvé.");
            } while (object == null);


            ((CacheRepository)repository).updateCache(idCache, latitude, longitude, description, nature, typeCache, statut, codeSecret, lieuId, proprietaireId);
        }
    }
    private static void updateLieu(RepositoryInterface repository) throws IOException {
        // On récupere l'identifiant de lieux pour afficher les informations au testeur
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Entrez l'identifiant du lieux à modifier : ");
        String id = reader.readLine();
        Object object = repository.findById(id);
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
            ((LieuRepository)repository).updateLieu(id, libelle);
        }
    }
    private static void updateVisite(RepositoryInterface repository) throws IOException {
        System.out.println("Entrez l'identifiant de la visite à modifier : ");
        // A FAIRE : redemander si l'identifiant de visite n'est pas bon
        String idVisite = reader.readLine();
        Object object = repository.findById(idVisite);
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
                UtilisateurRepository repoUtilisateur = new UtilisateurRepository();
                object = repoUtilisateur.findById(utilisateurId);
                System.out.println(object != null ? object.toString() : "Utilisateur non trouvé.");
            } while (object == null);


            System.out.println("Entrez l'id de la cache");
            String cacheId;
            do {
                cacheId = reader.readLine();
                //On regarde si on a un utilisateur que existe
                CacheRepository repoCache = new CacheRepository();
                object = repoCache.findById(cacheId);
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


            ((VisiteRepository)repository).updateVisite(Integer.parseInt(idVisite), dateVisite, utilisateurId, cacheId, commentaire, statut);
        }

    }

    private static void createVisite(RepositoryInterface repository) throws IOException{

        System.out.println("Si une valeur est inchangée tapez sur entree");
        System.out.println(" Optionnel : Entrez la nouvelle date et heure de visite");
        System.out.println("Format : YYYY-MM-DD hh:mm:ss");
        // A faire : Controle de la date
        String dateVisite;
        dateVisite = reader.readLine();
        System.out.println("Obligatoire : Entrez l'id de l'utilisateur quI a fait la visite  ");
        String utilisateurId;
        Object object;
        do{
            utilisateurId = reader.readLine();
            //On regarde si on a un utilisateur que existe
            UtilisateurRepository repoUtilisateur = new UtilisateurRepository();
            object = repoUtilisateur.findById(utilisateurId);
            System.out.println(object != null ? object.toString() : "Utilisateur non trouvé.");
        }while (object == null);
        System.out.println(" Obligatoire : Entrez l'id de la cache");
        String cacheId;
        do {
            cacheId = reader.readLine();
            //On regarde si on a un utilisateur que existe
            CacheRepository repoCache = new CacheRepository();
            object = repoCache.findById(cacheId);
            System.out.println(object != null ? object.toString() : "Cache non trouvé.");
        } while (object == null);

        String commentaire;
        System.out.println(" Optionnel : Entrez un commentaire");
        commentaire = reader.readLine();
        String statut;
        System.out.println(" Obligatoire : Entrez un statut : En cours / Terminee");
        do {
            statut = reader.readLine();
            // }while(("EN COURS".equals(statut.toUpperCase())) ||  ("TERMINEE".equals(statut.toUpperCase()) ));
        } while (!"EN COURS".equals(statut.toUpperCase()) && (!"TERMINEE".equals(statut.toUpperCase())));
        ((VisiteRepository)repository).createVisite(dateVisite, utilisateurId, cacheId, commentaire, statut);
    }
    private static void createLieu(RepositoryInterface repository) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // On declare un libellé que nous recupérons sur la ligne de commande
        String libelle = "";
        System.out.println("Obligatoire : Entrez le libellé du nouveau lieu");

        //On vérifie que l'entrée n'est pas vide
        do
            libelle = reader.readLine();
        while (("".equals(libelle)) || (libelle.length() > 100));

        //On effectue les changements en base
        ((LieuRepository)repository).createLieu(libelle);

        System.out.println("Le lieu a été créé");

    }
    private static void createUtilisateur(RepositoryInterface repository) throws IOException {
        System.out.println("Obligatoire : Entrez le  pseudo ");
        String pseudo;

        do
            pseudo = reader.readLine();
            //TODO tester si le pseudo est unique
        while (pseudo.length() > 50);

        System.out.println(" Optionnel : Entrez la description ");
        String descripton = reader.readLine();

        String avatar;
        System.out.println("Optionnel : Entrez le nom de l'image ");
        System.out.println("Ne pas oblier .png a la fin");
        do
            avatar = reader.readLine();
        while (avatar.length() > 255);


        ((UtilisateurRepository)repository).createUtilisateur(pseudo, descripton, avatar);
        System.out.println("L'utilisateur a bien été ajouté ");

    }
    private static void createCache (RepositoryInterface repository) throws  IOException{

        System.out.println("Si une valeur est inchangée tapez sur entree");
        System.out.println("Optionnel : Entrez la latitude");
        String latitude;
        latitude = reader.readLine();

        System.out.println("Optionnel : Entrez la longitude");
        String longitude;
        longitude = reader.readLine();

        System.out.println("Optionnel : Entrez la description");
        String description;
        description = reader.readLine();

        System.out.println("Obligatoire : Entrez la nature PHYSIQUE / VIRTUELLE");
        String nature;
        do{
            nature = reader.readLine();
        } while (!"PHYSIQUE".equals(nature.toUpperCase()) && (!"VIRTUELLE".equals(nature.toUpperCase())) );


        System.out.println("Obligatoire : Entrez le type de cache");
        System.out.println("TRADITIONNELLE / JEU DE PISTE / OBJET");
        String typeCache;
        do
            typeCache = reader.readLine();
        while (!"TRADITIONNELLE".equals(typeCache.toUpperCase()) && (!"JEU DE PISTE".equals(typeCache.toUpperCase())) && (!"OBJET".equals(typeCache.toUpperCase())));



        System.out.println("Obligatoire : Entrez le code secret");
        String codeSecret;
        codeSecret = reader.readLine();

        System.out.println("Obligatoire : Entrez le statut de la cache");
        System.out.println("ACTIVE / INACTIVE");


        System.out.println("Obligatoire : Entrez l'id du proprietaire de la cache  ");
        String proprietaireId;
        Object object;
        do {

            proprietaireId = reader.readLine();
            //On regarde si on a un utilisateur que existe
            UtilisateurRepository repoUtilisateur = new UtilisateurRepository();
            object = repoUtilisateur.findById(proprietaireId);
            System.out.println(object != null ? object.toString() : "Propriétaire non trouvé.");
        } while (object == null);

        System.out.println("Obligatoire : Entrez l'id du lieu de la cache");
        String lieuId;
        do {
            lieuId = reader.readLine();
            LieuRepository repoCache = new LieuRepository();
            object = repoCache.findById(lieuId);
            System.out.println(object != null ? object.toString() : "lieu non trouvé.");
        } while (object == null);

        ((CacheRepository)repository).createCache(latitude, longitude, description, nature, typeCache, codeSecret, lieuId, proprietaireId);
    }
}
