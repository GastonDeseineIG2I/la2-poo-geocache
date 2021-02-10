package repository;

import modele.CacheEntity;
import modele.UtilisateurEntity;
import modele.VisiteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.text.DecimalFormat;


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

    public void updateCache(int id, DecimalFormat latitude, DecimalFormat longitude, String description, String nature,
                            String typeCache,String statut, String codeSecret,int lieuId, int proprietaireId) {
        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
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
        session.update(utilisateur);
        tx.commit();
    }
}
