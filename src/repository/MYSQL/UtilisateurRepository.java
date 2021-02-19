package repository.MYSQL;

import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UtilisateurRepository extends MYSQLRepository
{

    private Session session;

    public UtilisateurRepository()
    {
        this.session = MYSQLRepository.getSession();
    }

    public UtilisateurEntity findById(String id)
    {
        Query q = this.session.createQuery("from UtilisateurEntity where id = :id");
        q.setParameter("id", id);

        return (UtilisateurEntity) q.uniqueResult();

    }

    public void deleteById(String id)
    {
        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
        session.delete(utilisateur);
        tx.commit();
    }

    public List<UtilisateurEntity> getAll()
    {
        List<UtilisateurEntity> lieux = session.createQuery("from UtilisateurEntity").list();
        return lieux;
    }

    @Override
    public void create(HashMap<String, Object> data)
    {
        String pseudo = (String) data.get("pseudo");
        String description = (String) data.get("description");
        String avatar = (String) data.get("avatar");

        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        utilisateur.setId(UUID.randomUUID().toString());
        if (!"".equals(pseudo))
        {
            //TODO verifier l'unicité
            utilisateur.setPseudo(pseudo);
        }
        if (!"".equals(description))
        {
            utilisateur.setDescription(description);
        } else
        {
            utilisateur.setDescription("");
        }
        if (!"".equals(avatar))
        {
            utilisateur.setAvatar(avatar);
        } else
        {
            utilisateur.setAvatar("default.png");
        }
        session.persist(utilisateur);
        tx.commit();
    }

    @Override
    public void update(HashMap<String, Object> data)
    {

        String id = (String) data.get("id");
        String pseudo = (String) data.get("pseudo");
        String description = (String) data.get("description");
        String avatar = (String) data.get("avatar");

        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
        if (!"".equals(pseudo))
        {
            //TODO verifier l'unicité
            utilisateur.setPseudo(pseudo);
        }
        if (!"".equals(description))
        {
            utilisateur.setDescription(description);
        }
        if (!"".equals(avatar))
        {
            utilisateur.setAvatar(avatar);
        }
        session.update(utilisateur);
        tx.commit();
    }

}
