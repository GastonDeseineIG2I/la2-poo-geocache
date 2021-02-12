package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.CacheEntity;
import modele.LieuEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import java.util.UUID;

import java.util.List;

public class LieuRepository extends MONGODBRepository
{

    private static Datastore datastore;
    private final static Class<LieuEntity> entityClass = LieuEntity.class;


    public LieuRepository(){
        try {
            MongoClient mongoClient = MONGODBRepository.getSession();
            Morphia morphia = new Morphia();
            morphia.map(CacheEntity.class);
            datastore = morphia.createDatastore(mongoClient,"la2geocache");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public LieuEntity findById(int id)
    {
        return datastore.get(entityClass, ""+id);

    }

    // Permet de supprimer un lieu avec son id
    public void deleteById(int id)
    {
            datastore.delete(id);
    }

    public List<LieuEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }


    // Permet de mettre a jour le libellé d'un lieu
    public void updateLieu(int id, String nomlieu)
    {
        Query query = datastore.createQuery(entityClass).field("uUid").equal(id);
        UpdateOperations<LieuEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("lieu", nomlieu);

        datastore.update(query, operation);
    }

    // Permet de créer un lieu
    public void createLieu(String nomlieu)
    {
        LieuEntity lieu = new LieuEntity();
        lieu.setLibelle(nomlieu);

        datastore.save(lieu);
    }

}
