package repository.MONGODB;

import com.mongodb.MongoClient;
import modele.CacheEntity;
import modele.UtilisateurEntity;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

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
        return datastore.get(entityClass, id);
    }

    public void deleteById(String id)
    {
        datastore.delete(id);
    }

    public List<UtilisateurEntity> getAll()
    {
        return datastore.find(entityClass).asList();
    }

    public void updateUtilisateur(String id, String pseudo, String descripton, String avatar) {
    /*Transaction tx = session.beginTransaction();
    UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
    if (!"".equals(pseudo) )
    {
        //TODO verifier l'unicité
        utilisateur.setPseudo(pseudo);
    }
    if (!"".equals(descripton)){
        utilisateur.setDescription(descripton);
    }
    if (!"".equals(avatar)){
        utilisateur.setAvatar(avatar);
    }
    session.update(utilisateur);
    tx.commit();*/
}

    public void createUtilisateur(String pseudo, String descripton, String avatar) {
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        if (!"".equals(pseudo) )
        {
            //TODO verifier l'unicité
            utilisateur.setPseudo(pseudo);
        }
        if (!"".equals(descripton)){
            utilisateur.setDescription(descripton);
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
