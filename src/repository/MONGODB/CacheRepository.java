package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.CacheEntity;
import modele.LieuEntity;
import modele.UtilisateurEntity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


public class CacheRepository extends MONGODBRepository
{


    private static Datastore datastore;
    private final static Class<CacheEntity> entityClass = CacheEntity.class;

    public CacheRepository(){
        try {
            MongoClient mongoClient = MONGODBRepository.getSession();
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(mongoClient,"la2geocache");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public CacheEntity findById(String id)
    {
        return datastore.get(entityClass, new ObjectId(id));

    }

    public void deleteById(String id)
    {
        datastore.delete(entityClass, new ObjectId(id));
    }


    public void update(CacheEntity object){

        Query query = datastore.createQuery(entityClass).field("_id").equal(object.get_id());
        UpdateOperations<CacheEntity> operation = datastore.createUpdateOperations(entityClass);

        operation.set("latitude", object.getLatitude());
        operation.set("longitude", object.getLongitude());
        operation.set("description", object.getDescription());
        operation.set("nature", object.getNature().toUpperCase());
        operation.set("typeCache", object.getTypeCache().toUpperCase());
        operation.set("codeSecret", object.getCodeSecret());
        operation.set("lieu", object.getLieu());
        operation.set("proprietaire", object.getProprietaire());
        operation.set("statut", object.getStatut());

        datastore.update(query, operation);
    }


    public void create(CacheEntity object){
        datastore.save(object);
    }

    public List<CacheEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }

    @Override
    public void create(HashMap<String, ?> data)
    {

    }

    @Override
    public void update(HashMap<String, ?> data)
    {

    }


    public void activeCache(String id) {
        Query query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
        UpdateOperations<CacheEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("statut","ACTIVE");
        datastore.update(query, operation);
    }
    public void desactiveCache(String id) {
        Query query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
        UpdateOperations<CacheEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("statut","INACTIVE");
        datastore.update(query, operation);
    }


    //List<CacheEntity> cacheUtilisateur = session.createQuery("from CacheEntity as cache join UtilisateurEntity as utilisateur " +
      //      "on cache.proprietaireId =utilisateur.id where cache.proprietaireId =:id ").setParameter("id",id).list();

    public List<CacheEntity> getCacheByProprietaire(String id){
        return datastore.createQuery(entityClass).field("proprietaire._id").equal(new ObjectId(id)).asList();

        /*UtilisateurEntity utilisateur = new UtilisateurRepository().findById(id);

        List<CacheEntity> cacheUtilisateur = session.createQuery("from CacheEntity as cache where cache.proprietaire =:utilisateur ")
                .setParameter("utilisateur",utilisateur)
                .list();
        return cacheUtilisateur;*/


    }

    public List<CacheEntity> getCacheByLieu(String id){
        return datastore.createQuery(entityClass).field("lieu._id").equal(new ObjectId(id)).asList();
        /*LieuEntity lieu = new LieuRepository().findById(id);

        List<CacheEntity> cacheLieux = session.createQuery("from CacheEntity as cache where cache.lieu =:lieu ")
                .setParameter("lieu",lieu)
                .list();
        return cacheLieux;*/

    }
}
