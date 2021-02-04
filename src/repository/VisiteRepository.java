package repository;

import modele.VisiteEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class VisiteRepository implements RepositoryInterface
{

    private Session session;

    public VisiteRepository(Session session){
        this.session = session;
    }

    public VisiteEntity findById(int id)
    {
        Query q = this.session.createQuery("from VisiteEntity where id = :id");
        q.setParameter("id",id);

        return (VisiteEntity) q.uniqueResult();

    }

}
