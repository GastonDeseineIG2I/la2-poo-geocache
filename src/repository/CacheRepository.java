package repository;

import modele.CacheEntity;
import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class CacheRepository implements RepositoryInterface
{

    private Session session;

    public CacheRepository(Session session){
        this.session = session;
    }

    public CacheEntity findById(int id)
    {
        Query q = this.session.createQuery("from CacheEntity where id = :id");
        q.setParameter("id",id);

        return (CacheEntity) q.uniqueResult();

    }

    @Override
    public void deleteById(int id)
    {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        session.delete(cache);
        tx.commit();
    }

}
