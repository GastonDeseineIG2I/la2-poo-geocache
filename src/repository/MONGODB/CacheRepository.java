package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.CacheEntity;
import modele.LieuEntity;
import modele.UtilisateurEntity;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.math.BigDecimal;
import java.util.List;


public class CacheRepository extends MONGODBRepository
{

    private static MongoClient mongoClient;
    private static Datastore datastore;
    private final static Class<CacheEntity> entityClass = CacheEntity.class;

    public CacheRepository(){
        mongoClient = MONGODBRepository.getSession();
        Morphia morphia = new Morphia();
        morphia.map(CacheEntity.class);
        datastore = morphia.createDatastore(mongoClient,"la2geocache");

    }

    public CacheEntity findById(String id)
    {
        return datastore.get(entityClass, id);

    }

    @Override
    public void deleteById(String id)
    {
        datastore.delete(id);
    }

    public void updateCache(String id, String latitude, String longitude, String description, String nature,
                            String typeCache, String statut, String codeSecret, String lieuId, String proprietaireId) {

        Query query = datastore.createQuery(entityClass).field("uUid").equal(id);
        UpdateOperations<CacheEntity> operation = datastore.createUpdateOperations(entityClass);


        if (!"".equals(latitude) )
        {
            BigDecimal lat = new BigDecimal(latitude);
            operation.set("latitude", lat);
        }
        if (!"".equals(longitude)){
            BigDecimal lon = new BigDecimal(longitude);
            operation.set("longitude", lon);
        }
        if (!"".equals(description)){
            operation.set("description", description);
        }
        if (!"".equals(nature)){
            operation.set("nature", nature.toUpperCase());
        }
        if (!"".equals(typeCache)){
            operation.set("type_cache",typeCache.toUpperCase());
        }
        if (!"".equals(codeSecret)){
            operation.set("code_secret", codeSecret);
        }
        if (!"".equals(lieuId)){
            LieuEntity lieu = new LieuRepository().findById(lieuId);
            operation.set("lieu", lieu.getLibelle());
        }
        if (!"".equals(proprietaireId)){
            UtilisateurEntity proprietaire = new UtilisateurRepository().findById(proprietaireId);
            operation.set("proprietaire", proprietaire.getPseudo());
        }
        if (!"".equals(statut)){
            operation.set("statut",statut.toUpperCase());
        }
        datastore.update(query, operation);
    }


    public void createCache(String latitude, String longitude, String description, String nature,
                            String typeCache, String codeSecret,String lieuId, String proprietaireId) {
        CacheEntity cache = new CacheEntity();

        if (!"".equals(latitude) )
        {
            BigDecimal lat = new BigDecimal(latitude);
            cache.setLatitude(lat);
        }
        if (!"".equals(longitude)){
            BigDecimal lon = new BigDecimal(longitude);
            cache.setLongitude(lon);
        }
        if (!"".equals(description)){
            cache.setDescription(description);
        }
        if (!"".equals(lieuId)){
            LieuEntity lieu = new LieuRepository().findById(lieuId);
            cache.setLieu(lieu);
        }
        // Ne peuvent pas être nul
        cache.setNature(nature.toUpperCase());
        cache.setTypeCache(typeCache.toUpperCase());
        cache.setCodeSecret(codeSecret);
        UtilisateurEntity proprietaire = new UtilisateurRepository().findById(proprietaireId);
        cache.setProprietaire(proprietaire);
        cache.setStatut("INACTIVE");

        datastore.save(cache);
    }

    public List<CacheEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }


    public void activeCache(String id) {
        Query query = datastore.createQuery(entityClass).field("uUid").equal(id);
        UpdateOperations<CacheEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("statut","ACTIVE");
        datastore.update(query, operation);
    }
    public void desactiveCache(String id) {
        Query query = datastore.createQuery(entityClass).field("uUid").equal(id);
        UpdateOperations<CacheEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("statut","INACTIVE");
        datastore.update(query, operation);
    }


    //List<CacheEntity> cacheUtilisateur = session.createQuery("from CacheEntity as cache join UtilisateurEntity as utilisateur " +
      //      "on cache.proprietaireId =utilisateur.id where cache.proprietaireId =:id ").setParameter("id",id).list();

    public List<CacheEntity> getCacheByProprietaire(String id){

        /*UtilisateurEntity utilisateur = new UtilisateurRepository().findById(id);

        List<CacheEntity> cacheUtilisateur = session.createQuery("from CacheEntity as cache where cache.proprietaire =:utilisateur ")
                .setParameter("utilisateur",utilisateur)
                .list();
        return cacheUtilisateur;*/
        return null;
    }

    public List<CacheEntity> getCacheByLieu(int id){

        /*LieuEntity lieu = new LieuRepository().findById(id);

        List<CacheEntity> cacheLieux = session.createQuery("from CacheEntity as cache where cache.lieu =:lieu ")
                .setParameter("lieu",lieu)
                .list();
        return cacheLieux;*/
        return null;
    }
}
