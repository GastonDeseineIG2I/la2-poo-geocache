package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.CacheEntity;
import modele.UtilisateurEntity;
import modele.VisiteEntity;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.sql.Timestamp;
import java.util.List;

public class VisiteRepository extends MONGODBRepository
{

    private static MongoClient mongoClient;
    private static Datastore datastore;
    private final static Class<VisiteEntity> entityClass = VisiteEntity.class;


    public VisiteRepository(){

        mongoClient = MONGODBRepository.getSession();
        Morphia morphia = new Morphia();
        morphia.map(CacheEntity.class);
        datastore = morphia.createDatastore(mongoClient,"la2geocache");

    }

    public VisiteEntity findById(int id)
    {
        return datastore.get(entityClass, id);
    }

    public void deleteById(int id)
    {
        datastore.delete(id);
    }

    public List<VisiteEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }

    public void updateVisite(int id, String dateVisite, String utilisateurId, String cacheId, String commentaire, String statut) {
        /*Transaction tx = session.beginTransaction();
        VisiteEntity visite = session.load(VisiteEntity.class, id);
        if (!"".equals(dateVisite) )
        {
            visite.setDateVisite(Timestamp.valueOf(dateVisite));
        }
        if (!"".equals(utilisateurId)){
            UtilisateurEntity utilisateur = new UtilisateurRepository().findById(Integer.parseInt(utilisateurId));
            visite.setUtilisateur(utilisateur);
        }
        if (!"".equals(cacheId)){
            CacheEntity cache = new CacheRepository().findById(Integer.parseInt(cacheId));
            visite.setCache(cache);
        }
        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        if (!"".equals(statut)){
            visite.setStatut(statut.toUpperCase());
        }
        session.update(visite);
        tx.commit();*/
    }

    public void createVisite(String dateVisite, String utilisateurId, String cacheId, String commentaire, String statut) {

        VisiteEntity visite = new VisiteEntity();
        if (!"".equals(utilisateurId)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           visite.setDateVisite(timestamp);
        }else{
            visite.setDateVisite(Timestamp.valueOf(dateVisite));
        }

        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        UtilisateurEntity utilisateur = new UtilisateurRepository().findById(Integer.parseInt(utilisateurId));
        visite.setUtilisateur(utilisateur);

        CacheEntity cache = new CacheRepository().findById(Integer.parseInt(cacheId));
        visite.setCache(cache);

        visite.setStatut(statut.toUpperCase());

        datastore.save(visite);
    }

    public void validerVisite(int idVisite, String commentaire) {
        /*Transaction tx = session.beginTransaction();
        VisiteEntity visite = session.load(VisiteEntity.class, idVisite);

        visite.setDateVisite(Timestamp.valueOf(LocalDateTime.now()));

        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        visite.setStatut("TERMINEE");

        session.update(visite);
        tx.commit();*/
    }

    public boolean compareCodeSecret(String idCache, String CodeSecret){
        /*CacheEntity cache = session.load(CacheEntity.class, idCache);
        String codeOfficielCache = cache.getCodeSecret();
        findById(idCache);
        if(codeOfficielCache.equals(CodeSecret)){
            VisiteRepository visite = new VisiteRepository();
        }else{
            return false ;
        }*/
        return true ;
    }

    public List<VisiteEntity> getVisiteByDate(String datee){

     //   String[] parts = date.split(" ");
       /* String part1 = datee + " 00:00:00";
        String part2 =datee + " 23:59:59";
        Timestamp dateDebut = Timestamp.valueOf(part1);
        Timestamp dateFin = Timestamp.valueOf(part2);
        System.out.println(part1);
        System.out.println(part2);
        List<VisiteEntity> visite = session.createQuery("from VisiteEntity as visite where visite.dateVisite >= :dateDebut  and visite.dateVisite <= :dateFin")
                .setParameter("dateDebut",dateDebut)
                .setParameter("dateFin",dateFin)
                .list();
        return visite;*/
       return null;
    }

}
