import modele.CacheEntity;
import modele.VisiteEntity;
import repository.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class Menu
{

    public static String choixBDD;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static HashMap<String, RepositoryInterface> repository = new HashMap<>();

    public static void menu() throws IOException
    {
        System.out.println("Selectionnez la base souhaité MYSQL ou MONGODB");
        do
        {
            choixBDD = reader.readLine();
        } while (!"MYSQL".equals(choixBDD.toUpperCase()) && (!"MONGODB".equals(choixBDD.toUpperCase())));

        Main.initSession(choixBDD);

        if (choixBDD.equals("MYSQL"))
        {
            repository.put("cache", new repository.MYSQL.CacheRepository());
            repository.put("lieu", new repository.MYSQL.LieuRepository());
            repository.put("utilisateur", new repository.MYSQL.UtilisateurRepository());
            repository.put("visite", new repository.MYSQL.VisiteRepository());
        } else
        {
            repository.put("cache", new repository.MONGODB.CacheRepository());
            repository.put("lieu", new repository.MONGODB.LieuRepository());
            repository.put("utilisateur", new repository.MONGODB.UtilisateurRepository());
            repository.put("visite", new repository.MONGODB.VisiteRepository());
        }

        System.out.println("Selectionnez en entrant le numéro du menu");
        System.out.println("-----------------------------------------");
        boolean terminate = false;
        while (!terminate)
        {
            System.out.println("1 - Gestion des caches");
            System.out.println("2 - Gestion des utilisateurs");
            System.out.println("3 - Gestion des lieux");
            System.out.println("4 - Gestion des visites");
            System.out.println("0 - Quitter");
            String res;
            int choice;
            do
            {
                res = reader.readLine();
                choice = Main.isNumeric(res);
            } while (choice > 4 || choice < 0);

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
                case 0:
                    terminate = true;
                    break;
            }
        }
        System.out.println("Fin du programme");
    }


    public static void menu(String typeMenu) throws IOException
    {
        String menuString;

        switch (typeMenu)
        {
            case "cache":
                menuString = "une cache";
                break;
            case "utilisateur":
                menuString = "un utilisateur";
                break;
            case "lieu":
                menuString = "un lieu";
                break;
            case "visite":
                menuString = "une visite";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeMenu);
        }
        boolean terminate = false;
        while (!terminate)
        {
            System.out.println("-----------------------------------------");
            System.out.println("1 - Afficher tous les " + typeMenu + (typeMenu.equals("lieu") ? "x" : "s"));
            System.out.println("2 - Ajouter " + menuString);
            System.out.println("3 - Modifier " + menuString);
            System.out.println("4 - Supprimer " + menuString);
            System.out.println("5 - Rechercher " + menuString);

            menuSpecifique(typeMenu, menuString);


            System.out.println("0 - Retour au menu précédent");
            String res;
            int choice;
            do
            {
                res = reader.readLine();

                choice = Main.isNumeric(res);
            } while (choice > 99 || choice < 0);


            System.out.println("-----------------------------------------");
            String id;
            Object object;
            switch (Integer.parseInt(res))
            {
                case 1:
                    for (Object objectfromlist : repository.get(typeMenu).getAll())
                    {
                        System.out.println(objectfromlist != null ? objectfromlist : "");
                    }
                    break;
                case 2:
                    switch (typeMenu)
                    {
                        case "cache":
                            createCache();
                            break;
                        case "lieu":
                            createLieu();
                            break;
                        case "utilisateur":
                            createUtilisateur();
                            break;
                        case "visite":
                            createVisite();
                            break;
                    }
                    break;
                case 3:
                    switch (typeMenu)
                    {
                        case "cache":
                            updateCache();
                            break;
                        case "lieu":
                            updateLieu();
                            break;
                        case "utilisateur":
                            updateUtilisateur();
                            break;
                        case "visite":
                            updateVisite();
                            break;
                    }
                    break;
                case 4:
                    delete(typeMenu);
                    break;
                case 5:

                    for (Object objectfromlist : repository.get(typeMenu).getAll())
                    {
                        System.out.println(objectfromlist != null ? objectfromlist : "");
                    }
                    System.out.println("Entrez l'identifiant rechercher : ");
                    id = reader.readLine();
                    object = repository.get(typeMenu).findById(id);
                    System.out.println(object != null ? object : "Resultat non trouvé.");
                    break;
                case 0:
                    return;
                default:
                    casSpecifique(typeMenu, Integer.parseInt(res));
                    break;
            }
            System.out.println("Appuyer sur ENTER pour continuer.");
            reader.readLine();
        }
    }


    public static void menuSpecifique(String typeMenu, String menuString)
    {
        switch (typeMenu)
        {
            case "cache":
                System.out.println("6 - Activer " + menuString);
                System.out.println("7 - Desactiver " + menuString);
                System.out.println("8 - Liste les caches d'un utilisateur ");
                System.out.println("9 - Liste les caches d'un lieu ");
                break;
            case "utilisateur":
                break;
            case "lieu":
                break;
            case "visite":
                System.out.println("6 - Valider " + menuString);
                System.out.println("7 - Recherche de visite en fonction d'une date");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeMenu);
        }
    }


    private static void casSpecifique(String typeMenu, int cas) throws IOException
    {
        String id;
        Object object;
        switch (typeMenu)
        {
            case "cache":
                switch (cas)
                {
                    case 6:
                        changeStatutCache(true);

                        break;
                    case 7:
                        changeStatutCache(false);


                        break;
                    case 8:
                        listeCacheProprietaire();
                        break;
                    case 9:
                        listeCacheLieu();
                        break;
                }
                break;
            case "utilisateur":
                break;
            case "lieu":
                break;
            case "visite":
                switch (cas)
                {
                    case 6:
                        System.out.println("Entrez l'identifiant de la visite : ");
                        id = reader.readLine();
                        object = repository.get("visite").findById(id);
                        System.out.println(object);
                        if (object != null)
                        {
                            String code;
                            boolean compareCode = false;
                            do
                            {
                                System.out.println("Obligatoire : Code secret de la cache");
                                code = reader.readLine();
                                if (choixBDD.equals("MYSQL"))
                                {
                                    compareCode = !((repository.MYSQL.VisiteRepository) repository.get("visite")).compareCodeSecret("" + ((VisiteEntity) object).getCache().getId(), code);
                                } else
                                {
                                    compareCode = !((repository.MONGODB.VisiteRepository) repository.get("visite")).compareCodeSecret("" + ((VisiteEntity) object).getCache().getId(), code);
                                }

                            } while (compareCode);

                            String commentaire;
                            System.out.println("Optionnel : Entrez un commentaire");
                            commentaire = reader.readLine();
                            if (choixBDD.equals("MYSQL"))
                            {
                                ((repository.MYSQL.VisiteRepository) repository.get("visite")).validerVisite(id, commentaire);
                            } else
                            {
                                ((repository.MONGODB.VisiteRepository) repository.get("visite")).validerVisite(id, commentaire);
                            }
                            object = repository.get("visite").findById(id);
                            System.out.println(object);
                            System.out.println("La visite a été validée.");
                        } else
                        {
                            System.out.println("Cette visite n'existe pas.");
                        }
                        break;
                    case 7:
                        listeVisiteParDate();
                }
                break;
        }
    }


    private static void changeStatutCache(boolean statut) throws IOException
    {
        for (Object objectfromlist : repository.get("cache").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Entrez l'identifiant de la cache : ");
        String id;
        id = reader.readLine();
        Object object = repository.get("cache").findById(id);
        if (object != null)
        {
            if (statut)
            {
                if (choixBDD.equals("MYSQL"))
                {
                    ((repository.MYSQL.CacheRepository) repository.get("cache")).activeCache(id);
                } else
                {
                    ((repository.MONGODB.CacheRepository) repository.get("cache")).activeCache(id);
                }
                System.out.println(object);
                System.out.println("La cache a été activée.");
            } else
            {
                if (choixBDD.equals("MYSQL"))
                {
                    ((repository.MYSQL.CacheRepository) repository.get("cache")).desactiveCache(id);
                } else
                {
                    ((repository.MONGODB.CacheRepository) repository.get("cache")).desactiveCache(id);
                }

                System.out.println(object);
                System.out.println("La cache a été desactivée.");
            }


        } else
        {
            System.out.println("Cette cache n'existe pas");
        }
    }


    private static void listeVisiteParDate() throws IOException
    {
        System.out.println("Entrez la date de visite :");
        System.out.println("Format YYYY-MM-dd:");
        String dateVisite;
        dateVisite = reader.readLine();

        List<VisiteEntity> visites;
        if (choixBDD.equals("MYSQL"))
        {
            visites = ((repository.MYSQL.VisiteRepository) repository.get("visite")).getVisiteByDate(dateVisite);
        } else
        {
            visites = ((repository.MONGODB.VisiteRepository) repository.get("visite")).getVisiteByDate(dateVisite);
        }

        for (Object objectfromlist : visites)
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }

    }


    private static void listeCacheLieu() throws IOException
    {
        for (Object objectfromlist : repository.get("lieu").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Entre l'identifiant du lieu pour afficher ses caches");
        String id;
        Object object;
        do
        {
            id = reader.readLine();

            object = repository.get("lieu").findById(id);
            System.out.println(object != null ? object : "Lieu non trouvé.");
        } while (object == null);

        List<CacheEntity> caches;
        if (choixBDD.equals("MYSQL"))
        {
            caches = ((repository.MYSQL.CacheRepository) repository.get("cache")).getCacheByLieu(id);
        } else
        {
            caches = ((repository.MONGODB.CacheRepository) repository.get("cache")).getCacheByLieu(id);
        }

        for (Object objectfromlist : caches)
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }

    }

    private static void listeCacheProprietaire() throws IOException
    {
        for (Object objectfromlist : repository.get("utilisateur").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Entre l'identifiant de l'utilisateur pour afficher ses caches");
        String id;
        Object object;
        do
        {
            id = reader.readLine();
            object = repository.get("utilisateur").findById(id);
            System.out.println(object != null ? object : "Utilisateur non trouvé.");
        } while (object == null);

        List<CacheEntity> caches;
        if (choixBDD.equals("MYSQL"))
        {
            caches = ((repository.MYSQL.CacheRepository) repository.get("cache")).getCacheByProprietaire(id);
        } else
        {
            caches = ((repository.MONGODB.CacheRepository) repository.get("cache")).getCacheByProprietaire(id);
        }

        for (Object objectfromlist : caches)
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }

    }

    private static void updateUtilisateur() throws IOException
    {

        for (Object objectfromlist : repository.get("utilisateur").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Entrez l'identifiant de l'utilisateur à modifier : ");
        String id = reader.readLine();
        Object object = repository.get("utilisateur").findById(id);
        System.out.println(object != null ? object : "Resultat non trouvé.");
        if (object != null)
        {
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
            System.out.println("Ne pas oublier .png a la fin");
            do
                avatar = reader.readLine();
            while (avatar.length() > 255);

            HashMap<String, Object> data = new HashMap<>();
            data.put("id", id);
            data.put("pseudo", pseudo);
            data.put("description", descripton);
            data.put("avatar", avatar);
            repository.get("utilisateur").update(data);
        }
    }

    private static void updateCache() throws IOException
    {
        for (Object objectfromlist : repository.get("cache").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Entrez l'identifiant de la cache à modifier : ");
        String idCache = reader.readLine();
        Object object = repository.get("cache").findById(idCache);
        System.out.println(object != null ? object : "Resultat non trouvé.");

        if (object != null)
        {
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
            do
            {
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
            do
            {
                statut = reader.readLine();
            } while (!"ACTIVE".equals(statut.toUpperCase()) && (!"INACTIVE".equals(statut.toUpperCase())) && (!"".equals(statut.toUpperCase())));


            for (Object objectfromlist : repository.get("utilisateur").getAll())
            {
                System.out.println(objectfromlist != null ? objectfromlist : "");
            }
            System.out.println("Obligatoire : Entrez l'id du proprietaire de la cache  ");
            String proprietaireId;
            do
            {
                proprietaireId = reader.readLine();
                object = repository.get("utilisateur").findById(proprietaireId);
                System.out.println(object != null ? object : "Propriétaire non trouvé.");
            } while (object == null);


            for (Object objectfromlist : repository.get("lieu").getAll())
            {
                System.out.println(objectfromlist != null ? objectfromlist : "");
            }
            System.out.println("Obligatoire : Entrez l'id du lieu de la cache");
            String lieuId;
            do
            {
                lieuId = reader.readLine();
                // LieuRepository repoCache = new LieuRepository();
                object = repository.get("lieu").findById(lieuId);
                System.out.println(object != null ? object : "lieu non trouvé.");
            } while (object == null);


            HashMap<String, Object> data = new HashMap<>();
            data.put("id", idCache);
            data.put("latitude", latitude);
            data.put("longitude", longitude);
            data.put("description", description);
            data.put("nature", nature);
            data.put("typeCache", typeCache);
            data.put("codeSecret", codeSecret);
            data.put("statut", statut);
            data.put("lieuId", lieuId);
            data.put("proprietaireId", proprietaireId);
            repository.get("cache").update(data);

        }
    }

    private static void updateLieu() throws IOException
    {
        for (Object objectfromlist : repository.get("lieu").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        // On récupere l'identifiant de lieux pour afficher les informations au testeur
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Entrez l'identifiant du lieux à modifier : ");
        String id = reader.readLine();
        Object object = repository.get("lieu").findById(id);
        System.out.println(object != null ? object : "Resultat non trouvé.");
        if (object != null)
        {
            // On declare un libellé que nous recupérons sur la ligne de commande
            String libelle = "";
            System.out.println("Entrez le nouveau libellé de lieux");

            //On vérifie que l'entrée n'est pas vide
            do
                libelle = reader.readLine();
            while (("".equals(libelle)) || (libelle.length() > 100));

            //On effectue les changements en base

            HashMap<String, Object> data = new HashMap<>();
            data.put("libelle", libelle);
            data.put("id", id);
            repository.get("lieu").update(data);
        }
    }

    private static void updateVisite() throws IOException
    {

        for (Object objectfromlist : repository.get("visite").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Entrez l'identifiant de la visite à modifier : ");
        // A FAIRE : redemander si l'identifiant de visite n'est pas bon
        String idVisite = reader.readLine();
        Object object = repository.get("visite").findById(idVisite);
        System.out.println(object != null ? object : "Resultat non trouvé.");

        if (object != null)
        {
            System.out.println("Si une valeur est inchangée tapez sur entree");
            System.out.println("Entrez la nouvelle date et heure de visite");
            System.out.println("Format : YYYY-MM-DD hh:mm:ss");
            // A faire : Controle de la date
            String dateVisite;
            dateVisite = reader.readLine();

            for (Object objectfromlist : repository.get("utilisateur").getAll())
            {
                System.out.println(objectfromlist != null ? objectfromlist : "");
            }
            System.out.println("Entrez l'id de l'utilisateur que a fait la visite  ");
            String utilisateurId;
            do
            {
                utilisateurId = reader.readLine();
                //On regarde si on a un utilisateur que existe
                object = repository.get("utilisateur").findById(utilisateurId);
                System.out.println(object != null ? object : "Utilisateur non trouvé.");
            } while (object == null);

            for (Object objectfromlist : repository.get("cache").getAll())
            {
                System.out.println(objectfromlist != null ? objectfromlist : "");
            }
            System.out.println("Entrez l'id de la cache");
            String cacheId;
            do
            {
                cacheId = reader.readLine();
                //On regarde si on a un utilisateur que existe
                object = repository.get("cache").findById(cacheId);
                System.out.println(object != null ? object : "Cache non trouvé.");
            } while (object == null);


            String commentaire;
            System.out.println("Entrez un commentaire'");
            commentaire = reader.readLine();

            String statut;
            System.out.println("Entrez un statut : EN COURS / TERMINEE'");

            do
            {
                statut = reader.readLine();
                // }while(("EN COURS".equals(statut.toUpperCase())) ||  ("TERMINEE".equals(statut.toUpperCase()) ));
            } while (!"EN COURS".equals(statut.toUpperCase()) && (!"TERMINEE".equals(statut.toUpperCase())));


            //DateTimeFormatter formatter= DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.SSS");
            //LocalDateTime dateHeureVisite = LocalDateTime.parse(dateVisite, formatter);

            HashMap<String, Object> data = new HashMap<>();
            data.put("id", idVisite);
            data.put("dateVisite", dateVisite);
            data.put("utilisateurId", utilisateurId);
            data.put("cacheId", cacheId);
            data.put("commentaire", commentaire);
            data.put("statut", statut);
            repository.get("visite").update(data);
        }

    }

    private static void createVisite() throws IOException
    {

        System.out.println("Si une valeur est inchangée tapez sur entree");
        System.out.println("Optionnel : Entrez la nouvelle date et heure de visite");
        System.out.println("Format : YYYY-MM-DD hh:mm:ss");
        // A faire : Controle de la date
        String dateVisite;
        dateVisite = reader.readLine();

        for (Object objectfromlist : repository.get("utilisateur").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Obligatoire : Entrez l'id de l'utilisateur quI a fait la visite  ");
        String utilisateurId;
        Object object;
        do
        {
            utilisateurId = reader.readLine();
            //On regarde si on a un utilisateur que existe
            object = repository.get("utilisateur").findById(utilisateurId);
            System.out.println(object != null ? object : "Utilisateur non trouvé.");
        } while (object == null);


        for (Object objectfromlist : repository.get("cache").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Obligatoire : Entrez l'id de la cache");
        String cacheId;
        do
        {
            cacheId = reader.readLine();
            //On regarde si on a un utilisateur que existe
            object = repository.get("cache").findById(cacheId);
            System.out.println(object != null ? object : "Cache non trouvé.");
        } while (object == null);

        String commentaire;
        System.out.println("Optionnel : Entrez un commentaire");
        commentaire = reader.readLine();
        String statut;
        System.out.println("Obligatoire : Entrez un statut : En cours / Terminee");
        do
        {
            statut = reader.readLine();
            // }while(("EN COURS".equals(statut.toUpperCase())) ||  ("TERMINEE".equals(statut.toUpperCase()) ));
        } while (!"EN COURS".equals(statut.toUpperCase()) && (!"TERMINEE".equals(statut.toUpperCase())));

        HashMap<String, Object> data = new HashMap<>();
        data.put("dateVisite", dateVisite);
        data.put("utilisateurId", utilisateurId);
        data.put("cacheId", cacheId);
        data.put("commentaire", commentaire);
        data.put("statut", statut);
        repository.get("visite").create(data);
    }

    private static void createLieu() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // On declare un libellé que nous recupérons sur la ligne de commande
        String libelle = "";
        System.out.println("Obligatoire : Entrez le libellé du nouveau lieu");

        //On vérifie que l'entrée n'est pas vide
        do
            libelle = reader.readLine();
        while (("".equals(libelle)) || (libelle.length() > 100));


        //On effectue les changements en base
        HashMap<String, Object> data = new HashMap<>();
        data.put("libelle", libelle);
        repository.get("lieu").create(data);

        System.out.println("Le lieu a été créé");

    }

    private static void createUtilisateur() throws IOException
    {
        System.out.println("Obligatoire : Entrez le  pseudo ");
        String pseudo;

        do
            pseudo = reader.readLine();
            //TODO tester si le pseudo est unique
        while (pseudo.length() > 50);

        System.out.println("Optionnel : Entrez la description ");
        String descripton = reader.readLine();

        String avatar;
        System.out.println("Optionnel : Entrez le nom de l'image ");
        System.out.println("Ne pas oublier .png a la fin");
        do
            avatar = reader.readLine();
        while (avatar.length() > 255);


        HashMap<String, Object> data = new HashMap<>();
        data.put("pseudo", pseudo);
        data.put("description", descripton);
        data.put("avatar", avatar);
        repository.get("utilisateur").create(data);

        System.out.println("L'utilisateur a bien été ajouté ");

    }

    private static void createCache() throws IOException
    {

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
        do
        {
            nature = reader.readLine();
        } while (!"PHYSIQUE".equals(nature.toUpperCase()) && (!"VIRTUELLE".equals(nature.toUpperCase())));


        System.out.println("Obligatoire : Entrez le type de cache");
        System.out.println("TRADITIONNELLE / JEU DE PISTE / OBJET");
        String typeCache;
        do
            typeCache = reader.readLine();
        while (!"TRADITIONNELLE".equals(typeCache.toUpperCase()) && (!"JEU DE PISTE".equals(typeCache.toUpperCase())) && (!"OBJET".equals(typeCache.toUpperCase())));


        System.out.println("Obligatoire : Entrez le code secret");
        String codeSecret;
        codeSecret = reader.readLine();

        for (Object objectfromlist : repository.get("utilisateur").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }

        System.out.println("Obligatoire : Entrez l'id du proprietaire de la cache  ");
        String proprietaireId;
        Object object;
        do
        {

            proprietaireId = reader.readLine();
            //On regarde si on a un utilisateur que existe

            object = repository.get("utilisateur").findById(proprietaireId);
            System.out.println(object != null ? object : "Propriétaire non trouvé.");
        } while (object == null);


        for (Object objectfromlist : repository.get("lieu").getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        System.out.println("Obligatoire : Entrez l'id du lieu de la cache");
        String lieuId;
        do
        {
            lieuId = reader.readLine();

            object = repository.get("lieu").findById(lieuId);
            System.out.println(object != null ? object : "lieu non trouvé.");
        } while (object == null);

        HashMap<String, Object> data = new HashMap<>();
        data.put("latitude", latitude);
        data.put("longitude", longitude);
        data.put("description", description);
        data.put("nature", nature);
        data.put("typeCache", typeCache);
        data.put("codeSecret", codeSecret);
        data.put("lieuId", lieuId);
        data.put("proprietaireId", proprietaireId);
        repository.get("cache").create(data);

    }

    private static void delete(String type) throws IOException
    {
        for (Object objectfromlist : repository.get(type).getAll())
        {
            System.out.println(objectfromlist != null ? objectfromlist : "");
        }
        String id;
        System.out.println("Entrez l'identifiant à supprimer : ");
        id = reader.readLine();
        repository.get(type).deleteById(id);

    }
}
