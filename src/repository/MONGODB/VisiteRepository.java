package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.CacheEntity;
import modele.LieuEntity;
import modele.UtilisateurEntity;
import modele.VisiteEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.math.BigDecimal;
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

    public VisiteEntity findById(String id)
    {
        return datastore.get(entityClass, id);
    }

    public void deleteById(String id)
    {
        datastore.delete(id);
    }

    public List<VisiteEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }

    public void updateVisite(String id, String dateVisite, String utilisateurId, String cacheId, String commentaire, String statut) {

        Query query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
        UpdateOperations<VisiteEntity> operation = datastore.createUpdateOperations(entityClass);
        if (!"".equals(dateVisite) )
        {
            operation.set("dateVisite",Timestamp.valueOf(dateVisite));
        }
        if (!"".equals(utilisateurId)){
            UtilisateurEntity utilisateur = new UtilisateurRepository().findById(utilisateurId);
            operation.set("utilisateur",utilisateur.getId());
        }
        if (!"".equals(cacheId)){
            CacheEntity cache = new CacheRepository().findById(cacheId);
            operation.set("cache",cache.getId());
        }
        if (!"".equals(commentaire)){
            operation.set("commentaire",commentaire);
        }
        if (!"".equals(statut)){
            operation.set("statut",statut.toUpperCase());
        }

        datastore.update(query, operation);
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
        UtilisateurEntity utilisateur = new UtilisateurRepository().findById(utilisateurId);
        visite.setUtilisateur(utilisateur);

        CacheEntity cache = new CacheRepository().findById(cacheId);
        visite.setCache(cache);

        visite.setStatut(statut.toUpperCase());

        datastore.save(visite);
    }

    public void validerVisite(String idVisite, String commentaire) {
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

    public List<VisiteEntity> getVisiteByDate(String date){

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
