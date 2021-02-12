package repository.JPA;

import modele.LieuEntity;
import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.RepositoryInterface;

import java.util.List;

public class UtilisateurRepository extends JPARepository
{

    private Session session;

    public UtilisateurRepository(){
        this.session = JPARepository.getSession();
    }

    public UtilisateurEntity findById(int id)
    {
        Query q = this.session.createQuery("from UtilisateurEntity where id = :id");
        q.setParameter("id",id);

        return (UtilisateurEntity) q.uniqueResult();

    }

    public void deleteById(int id)
    {
        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
        session.delete(utilisateur);
        tx.commit();
    }

    public List<UtilisateurEntity> getAll()
    {
        List<UtilisateurEntity> lieux = session.createQuery("from UtilisateurEntity").list();
        return lieux;
    }

    public void updateUtilisateur(int id,String pseudo, String descripton, String avatar) {
    Transaction tx = session.beginTransaction();
    UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
    if (!"".equals(pseudo) )
    {
        //TODO verifier l'unicité
        utilisateur.setPseudo(pseudo);
    }
    if (!"".equals(descripton)){
        utilisateur.setDescription(descripton);
    }
    if (!"".equals(avatar)){
        utilisateur.setAvatar(avatar);
    }
    session.update(utilisateur);
    tx.commit();
}

    public void createUtilisateur(String pseudo, String descripton, String avatar) {
        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = new UtilisateurEntity();
        if (!"".equals(pseudo) )
        {
            //TODO verifier l'unicité
            utilisateur.setPseudo(pseudo);
        }
        if (!"".equals(descripton)){
            utilisateur.setDescription(descripton);
        }else{
            utilisateur.setDescription("");
        }
        if (!"".equals(avatar)){
            utilisateur.setAvatar(avatar);
        }else{
            utilisateur.setAvatar("default.png");
        }
        session.persist(utilisateur);
        tx.commit();
    }
}
