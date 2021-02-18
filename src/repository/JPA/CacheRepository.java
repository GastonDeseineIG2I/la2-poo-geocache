package repository.JPA;

import modele.CacheEntity;
import modele.LieuEntity;
import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class CacheRepository extends JPARepository
{

    private Session session;

    public CacheRepository()
    {
        this.session = JPARepository.getSession();
    }

    public CacheEntity findById(String id)
    {
        Query q = this.session.createQuery("from CacheEntity where id = :id");
        q.setParameter("id", id);

        return (CacheEntity) q.uniqueResult();

    }

    @Override
    public void deleteById(String id)
    {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        session.delete(cache);
        tx.commit();
    }

    public void updateCache(String id, String latitude, String longitude, String description, String nature,
                            String typeCache, String statut, String codeSecret, String lieuId, String proprietaireId)
    {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        if (!"".equals(latitude))
        {
            BigDecimal lat = new BigDecimal(latitude);
            cache.setLatitude(lat);
        }
        if (!"".equals(longitude))
        {
            BigDecimal lon = new BigDecimal(longitude);
            cache.setLongitude(lon);
        }
        if (!"".equals(description))
        {
            cache.setDescription(description);
        }
        if (!"".equals(nature))
        {
            cache.setNature(nature.toUpperCase());
        }
        if (!"".equals(typeCache))
        {
            cache.setTypeCache(typeCache.toUpperCase());
        }
        if (!"".equals(codeSecret))
        {
            cache.setCodeSecret(codeSecret);
        }
        if (!"".equals(lieuId))
        {
            LieuEntity lieu = new LieuRepository().findById(lieuId);
            cache.setLieu(lieu);
        }
        if (!"".equals(proprietaireId))
        {
            UtilisateurEntity proprietaire = new UtilisateurRepository().findById(proprietaireId);
            cache.setProprietaire(proprietaire);
        }
        if (!"".equals(statut))
        {
            cache.setStatut(statut.toUpperCase());
        }
        session.update(cache);
        tx.commit();
    }


    public void createCache(String latitude, String longitude, String description, String nature,
                            String typeCache, String codeSecret, String lieuId, String proprietaireId)
    {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = new CacheEntity();
        cache.setId(UUID.randomUUID().toString());

        if (!"".equals(latitude))
        {
            BigDecimal lat = new BigDecimal(latitude);
            cache.setLatitude(lat);
        }
        if (!"".equals(longitude))
        {
            BigDecimal lon = new BigDecimal(longitude);
            cache.setLongitude(lon);
        }
        if (!"".equals(description))
        {
            cache.setDescription(description);
        }
        if (!"".equals(lieuId))
        {
            LieuEntity lieu = new LieuRepository().findById(lieuId);
            cache.setLieu(lieu);
        }
        // Ne peuvent pas être nul
        cache.setNature(nature.toUpperCase());
        cache.setTypeCache(typeCache.toUpperCase());
        cache.setCodeSecret(codeSecret);
        UtilisateurEntity proprietaire = new UtilisateurRepository().findById(proprietaireId);
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

    @Override
    public void create(HashMap<String, Object> data)
    {

    }

    @Override
    public void update(HashMap<String, Object> data)
    {

    }


    public void activeCache(String id)
    {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        cache.setStatut("ACTIVE");
        session.update(cache);
        tx.commit();
    }

    public void desactiveCache(String id)
    {
        Transaction tx = session.beginTransaction();
        CacheEntity cache = session.load(CacheEntity.class, id);
        cache.setStatut("INACTIVE");
        session.update(cache);
        tx.commit();
    }


    //List<CacheEntity> cacheUtilisateur = session.createQuery("from CacheEntity as cache join UtilisateurEntity as utilisateur " +
    //      "on cache.proprietaireId =utilisateur.id where cache.proprietaireId =:id ").setParameter("id",id).list();

    public List<CacheEntity> getCacheByProprietaire(String id)
    {

        UtilisateurEntity utilisateur = new UtilisateurRepository().findById(id);

        List<CacheEntity> cacheUtilisateur = session.createQuery("from CacheEntity as cache where cache.proprietaire =:utilisateur ")
                .setParameter("utilisateur", utilisateur)
                .list();
        return cacheUtilisateur;
    }

    public List<CacheEntity> getCacheByLieu(String id)
    {

        LieuEntity lieu = new LieuRepository().findById(id);

        List<CacheEntity> cacheLieux = session.createQuery("from CacheEntity as cache where cache.lieu =:lieu ")
                .setParameter("lieu", lieu)
                .list();
        return cacheLieux;
    }
}
