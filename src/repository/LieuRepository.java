package repository;

import modele.LieuEntity;
import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LieuRepository implements RepositoryInterface
{

    private Session session;

    public LieuRepository(Session session){
        this.session = session;
    }

    public LieuEntity findById(int id)
    {
        Query q = this.session.createQuery("from LieuEntity where id = :id");
        q.setParameter("id",id);

        return (LieuEntity) q.uniqueResult();

    }

    public void deleteById(int id)
    {
        Transaction tx = session.beginTransaction();
        LieuEntity lieu = session.load(LieuEntity.class, id);
        session.delete(lieu);
        tx.commit();
    }

}
