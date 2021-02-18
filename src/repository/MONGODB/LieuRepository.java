package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.LieuEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class LieuRepository extends MONGODBRepository<LieuEntity>
{

    private static Datastore datastore;
    private final static Class<LieuEntity> entityClass = LieuEntity.class;


    public LieuRepository(){
        try {
            MongoClient mongoClient = MONGODBRepository.getSession();
            Morphia morphia = new Morphia();
            datastore = morphia.createDatastore(mongoClient,"la2geocache");
        } catch (Exception e) {
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
    public void create(LieuEntity object)
    {

    }

    @Override
    public void update(LieuEntity object)
    {

    }


    // Permet de mettre a jour le libellé d'un lieu
    public void updateLieu(String id, String nomlieu)
    {
        Query query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
        UpdateOperations<LieuEntity> operation = datastore.createUpdateOperations(entityClass);
        operation.set("libelle", nomlieu);

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
