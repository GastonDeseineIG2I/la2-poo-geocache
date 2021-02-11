package repository;

import modele.CacheEntity;
import modele.LieuEntity;
import modele.UtilisateurEntity;
import modele.VisiteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
            UtilisateurEntity utilisateur = new UtilisateurRepository(session).findById(Integer.parseInt(utilisateurId));
            visite.setUtilisateur(utilisateur);
        }
        if (!"".equals(cacheId)){
            CacheEntity cache = new CacheRepository(session).findById(Integer.parseInt(cacheId));
            visite.setCache(cache);
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
        if (!"".equals(utilisateurId)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
           visite.setDateVisite(timestamp);
        }else{
            visite.setDateVisite(Timestamp.valueOf(dateVisite));
        }

        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        UtilisateurEntity utilisateur = new UtilisateurRepository(session).findById(Integer.parseInt(utilisateurId));
        visite.setUtilisateur(utilisateur);

        CacheEntity cache = new CacheRepository(session).findById(Integer.parseInt(cacheId));
        visite.setCache(cache);

        visite.setStatut(statut.toUpperCase());

        session.persist(visite);
        tx.commit();
    }

    public void validerVisite(int idVisite, String commentaire) {
        Transaction tx = session.beginTransaction();
        VisiteEntity visite = session.load(VisiteEntity.class, idVisite);

        visite.setDateVisite(Timestamp.valueOf(LocalDateTime.now()));

        if (!"".equals(commentaire)){
            visite.setCommentaire(commentaire);
        }
        visite.setStatut("TERMINEE");

        session.update(visite);
        tx.commit();
    }

    public boolean compareCodeSecret(int idCache, String CodeSecret){
        CacheEntity cache = session.load(CacheEntity.class, idCache);
        String codeOfficielCache = cache.getCodeSecret();
        findById(idCache);
        if(codeOfficielCache.equals(CodeSecret)){
            VisiteRepository visite = new VisiteRepository(session);
        }else{
            return false ;
        }
        return true ;
    }

    public List<VisiteEntity> getVisiteByDate(String datee){

     //   String[] parts = date.split(" ");
        String part1 = datee + " 00:00:00";
        String part2 =datee + " 23:59:59";
        Timestamp dateDebut = Timestamp.valueOf(part1);
        Timestamp dateFin = Timestamp.valueOf(part2);
        System.out.println(part1);
        System.out.println(part2);
        List<VisiteEntity> visite = session.createQuery("from VisiteEntity as visite where visite.dateVisite >= :dateDebut  and visite.dateVisite <= :dateFin")
                .setParameter("dateDebut",dateDebut)
                .setParameter("dateFin",dateFin)
                .list();
        return visite;
    }

}
