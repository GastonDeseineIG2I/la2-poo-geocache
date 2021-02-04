package repository;

import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UtilisateurRepository implements RepositoryInterface
{

    private Session session;

    public UtilisateurRepository(Session session){
        this.session = session;
    }

    public UtilisateurEntity findById(int id)
    {
        Query q = this.session.createQuery("from UtilisateurEntity where id = :id");
        q.setParameter("id",id);

        return (UtilisateurEntity) q.uniqueResult();

    }

}
