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

import java.util.List;

public class UtilisateurRepository extends MONGODBRepository
{

    private static MongoClient mongoClient;
    private static Datastore datastore;
    private final static Class<UtilisateurEntity> entityClass = UtilisateurEntity.class;


    public UtilisateurRepository(){
        mongoClient = MONGODBRepository.getSession();
        Morphia morphia = new Morphia();
        morphia.map(CacheEntity.class);
        datastore = morphia.createDatastore(mongoClient,"la2geocache");

    }

    public UtilisateurEntity findById(String id)
    {
        return datastore.get(entityClass, new ObjectId(id));
    }

    public void deleteById(String id)
    {
        datastore.delete(entityClass, new ObjectId(id));
    }

    public List<UtilisateurEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }

    public void updateUtilisateur(String id, String pseudo, String description, String avatar) {

        Query query = datastore.createQuery(entityClass).field("_id").equal(new ObjectId(id));
        UpdateOperations<UtilisateurEntity> operation = datastore.createUpdateOperations(entityClass);

        if (!"".equals(pseudo) )
        {
            //TODO verifier l'unicité
            operation.set("pseudo", pseudo);
        }
        if (!"".equals(description)){
            operation.set("description", description);
        }
        if (!"".equals(avatar)){
            operation.set("avatar", avatar);
        }
        datastore.update(query, operation);
}

    public void createUtilisateur(String pseudo, String description, String avatar) {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        if (!"".equals(pseudo) )
        {
            //TODO verifier l'unicité
            utilisateur.setPseudo(pseudo);
        }
        if (!"".equals(description)){
            utilisateur.setDescription(description);
        }else{
            utilisateur.setDescription("");
        }
        if (!"".equals(avatar)){
            utilisateur.setAvatar(avatar);
        }else{
            utilisateur.setAvatar("default.png");
        }
        datastore.save(utilisateur);
    }
}
