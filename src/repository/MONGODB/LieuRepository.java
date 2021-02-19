package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.LieuEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.HashMap;
import java.util.List;

public class LieuRepository extends MONGODBRepository
{

    private final static Class<LieuEntity> entityClass = LieuEntity.class;
    private static Datastore datastore;


    public LieuRepository()
    {
        try
        {
            MongoClient mongoClient = MONGODBRepository.getSession();
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(mongoClient, "la2-geocache");
        } catch (Exception e)
        {
            System.out.println(e.toString());
        }

    }

    public LieuEntity findById(String id)
    {
        return datastore.get(entityClass, new ObjectId(id));

    }

    // Permet de supprimer un lieu avec son id
    public void deleteById(String id)
    {
        datastore.delete(entityClass, new ObjectId(id));
    }

    public List<LieuEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }

    @Override
    public void create(HashMap<String, Object> data)
    {
        String nomlieu = (String) data.get("libelle");

        LieuEntity lieu = new LieuEntity();
        lieu.setLibelle(nomlieu);
        datastore.save(lieu);
    }

    @Override
    public void update(HashMap<String, Object> data)
    {
        String nomlieu = (String) data.get("libelle");
        String id = (String) data.get("id");

        Query query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
        UpdateOperations<LieuEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("libelle", nomlieu);

        datastore.update(query, operation);
    }


}
