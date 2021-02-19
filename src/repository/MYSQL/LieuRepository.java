package repository.MYSQL;

import modele.LieuEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LieuRepository extends MYSQLRepository
{

    private Session session;

    public LieuRepository()
    {
        this.session = MYSQLRepository.getSession();
    }

    public LieuEntity findById(String id)
    {
        Query q = this.session.createQuery("from LieuEntity where id = :id");
        q.setParameter("id", id);

        return (LieuEntity) q.uniqueResult();

    }

    // Permet de supprimer un lieu avec son id
    public void deleteById(String id)
    {
        Transaction tx = session.beginTransaction();
        LieuEntity lieu = session.load(LieuEntity.class, id);
        session.delete(lieu);
        tx.commit();
    }

    public List<LieuEntity> getAll()
    {
        List<LieuEntity> lieux = session.createQuery("from LieuEntity").list();
        return lieux;
    }

    @Override
    public void create(HashMap<String, Object> data)
    {
        String nomlieu = (String) data.get("libelle");

        Transaction tx = session.beginTransaction();
        LieuEntity lieu = new LieuEntity();
        lieu.setId(UUID.randomUUID().toString());
        lieu.setLibelle(nomlieu);
        session.persist(lieu);
        tx.commit();
    }

    @Override
    public void update(HashMap<String, Object> data)
    {
        String nomlieu = (String) data.get("libelle");
        String id = (String) data.get("id");

        Transaction tx = session.beginTransaction();
        LieuEntity lieu = session.load(LieuEntity.class, id);
        lieu.setLibelle(nomlieu);
        session.update(lieu);
        tx.commit();
    }



}
