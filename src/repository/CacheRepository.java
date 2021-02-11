package repository;

import modele.CacheEntity;
import modele.LieuEntity;
import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigDecimal;
import java.util.List;


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

    public void updateCache(int id, String latitude, String longitude, String description, String nature,
                            String typeCache,String statut, String codeSecret,String lieuId, String proprietaireId) {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        if (!"".equals(latitude) )
        {
            BigDecimal lat = new BigDecimal(latitude);
            cache.setLatitude(lat);
        }
        if (!"".equals(longitude)){
            BigDecimal lon = new BigDecimal(longitude);
            cache.setLongitude(lon);
        }
        if (!"".equals(description)){
            cache.setDescription(description);
        }
        if (!"".equals(nature)){
            cache.setNature(nature.toUpperCase());
        }
        if (!"".equals(typeCache)){
            cache.setTypeCache(typeCache.toUpperCase());
        }
        if (!"".equals(codeSecret)){
            cache.setCodeSecret(codeSecret);
        }
        if (!"".equals(lieuId)){
            LieuEntity lieu = new LieuRepository(session).findById(Integer.parseInt(lieuId));
            cache.setLieu(lieu);
        }
        if (!"".equals(proprietaireId)){
            UtilisateurEntity proprietaire = new UtilisateurRepository(session).findById(Integer.parseInt(proprietaireId));
            cache.setProprietaire(proprietaire);
        }
        if (!"".equals(statut)){
            cache.setStatut(statut.toUpperCase());
        }
        session.update(cache);
        tx.commit();
    }


    public void createCache(String latitude, String longitude, String description, String nature,
                            String typeCache, String codeSecret,String lieuId, String proprietaireId) {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = new CacheEntity();

        if (!"".equals(latitude) )
        {
            BigDecimal lat = new BigDecimal(latitude);
            cache.setLatitude(lat);
        }
        if (!"".equals(longitude)){
            BigDecimal lon = new BigDecimal(longitude);
            cache.setLongitude(lon);
        }
        if (!"".equals(description)){
            cache.setDescription(description);
        }
        if (!"".equals(lieuId)){
            LieuEntity lieu = new LieuRepository(session).findById(Integer.parseInt(lieuId));
            cache.setLieu(lieu);
        }
        // Ne peuvent pas être nul
        cache.setNature(nature.toUpperCase());
        cache.setTypeCache(typeCache.toUpperCase());
        cache.setCodeSecret(codeSecret);
        UtilisateurEntity proprietaire = new UtilisateurRepository(session).findById(Integer.parseInt(proprietaireId));
        cache.setProprietaire(proprietaire);
        cache.setStatut("INACTIVE");
        session.persist(cache);
        tx.commit();
    }

    public List<CacheEntity> getAll()
    {
        List<CacheEntity> lieux = session.createQuery("from CacheEntity").list();
        return lieux;
    }


    public void activeCache(int id) {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        cache.setStatut("ACTIVE");
        session.update(cache);
        tx.commit();
    }
    public void desactiveCache(int id) {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        cache.setStatut("INACTIVE");
        session.update(cache);
        tx.commit();
    }


}
