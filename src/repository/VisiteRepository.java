package repository;

import modele.VisiteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.List;

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

    public void deleteById(int id)
    {
        Transaction tx = session.beginTransaction();
        VisiteEntity visite = session.load(VisiteEntity.class, id);
        session.delete(visite);
        tx.commit();
    }

    public List<VisiteEntity> getAll()
    {
        List<VisiteEntity> visites = session.createQuery("from VisiteEntity").list();
        return visites;
    }

    public void updateVisite(int id, String dateVisite, String utilisateurId, String cacheId, String commentaire, String statut) {
        Transaction tx = session.beginTransaction();
        VisiteEntity visite = session.load(VisiteEntity.class, id);
        if (!"".equals(dateVisite) )
        {
            visite.setDateVisite(Timestamp.valueOf(dateVisite));
        }
        if (!"".equals(utilisateurId)){
            visite.setUtilisateurId(Integer.parseInt(utilisateurId));
        }
        if (!"".equals(cacheId)){
            visite.setCacheId(Integer.parseInt(cacheId));
        }
        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        if (!"".equals(statut)){
            visite.setStatut(statut.toUpperCase());
        }
        session.update(visite);
        tx.commit();
    }

    public void createVisite(String dateVisite, String utilisateurId, String cacheId, String commentaire, String statut) {
        Transaction tx = session.beginTransaction();
        VisiteEntity visite = new VisiteEntity();

        visite.setDateVisite(Timestamp.valueOf(dateVisite));

        if (!"".equals(utilisateurId)){
            visite.setUtilisateurId(Integer.parseInt(utilisateurId));
        }
        visite.setCacheId(Integer.parseInt(cacheId));

        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        visite.setStatut(statut.toUpperCase());

        session.persist(visite);
        tx.commit();
    }

}
