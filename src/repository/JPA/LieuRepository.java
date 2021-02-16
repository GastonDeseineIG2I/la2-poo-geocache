package repository.JPA;

import modele.LieuEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LieuRepository extends JPARepository
{

    private Session session;

    public LieuRepository(){
        this.session = JPARepository.getSession();
    }

    public LieuEntity findById(String id)
    {
        Query q = this.session.createQuery("from LieuEntity where id = :id");
        q.setParameter("id",id);

        return (LieuEntity) q.uniqueResult();

    }

    // Permet de supprimer un lieu avec son id
    public void deleteById(String id)
    {
        Transaction tx = session.beginTransaction();
        LieuEntity lieu = session.load(LieuEntity.class, id);
        session.delete(lieu);
        tx.commit();
    }

    public List<LieuEntity> getAll()
    {
        List<LieuEntity> lieux = session.createQuery("from LieuEntity").list();
        return lieux;
    }


    // Permet de mettre a jour le libellé d'un lieu
    public void updateLieu(int id, String nomlieu)
    {
        Transaction tx = session.beginTransaction();
        LieuEntity lieu = session.load(LieuEntity.class, id);
        lieu.setLibelle(nomlieu);
        session.update(lieu);
        tx.commit();
    }

    // Permet de créer un lieu
    public void createLieu(String nomlieu)
    {
        Transaction tx = session.beginTransaction();
        LieuEntity lieu = new LieuEntity();
        lieu.setLibelle(nomlieu);
        session.persist(lieu);
        tx.commit();
    }

}
