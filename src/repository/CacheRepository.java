package repository;

import modele.CacheEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CacheRepository
{

    private Session session;

    public CacheRepository(Session session){
        this.session = session;
    }

    public CacheEntity findCacheById(int id)
    {
        Query q = this.session.createQuery("from CacheEntity where id = :id");
        q.setParameter("id",id);

        return (CacheEntity) q.uniqueResult();

    }

}
