package repository;

import modele.LieuEntity;
import modele.UtilisateurEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

    public void deleteById(int id)
    {
        Transaction tx = session.beginTransaction();
        UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
        session.delete(utilisateur);
        tx.commit();
    }

    public void updateUtilisateur(int id,String pseudo, String descripton, String avatar) {
    Transaction tx = session.beginTransaction();
    UtilisateurEntity utilisateur = session.load(UtilisateurEntity.class, id);
    if (!"".equals(pseudo) )
    {
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
}
