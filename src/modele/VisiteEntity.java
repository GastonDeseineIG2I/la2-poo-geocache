package modele;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@org.mongodb.morphia.annotations.Entity
@Table(name = "visite", schema = "la2-geocache", catalog = "")
public class VisiteEntity
{
    @org.mongodb.morphia.annotations.Id
    private String id;

    private Timestamp dateVisite;
    private String commentaire;
    private String statut;
    private UtilisateurEntity utilisateur;
    private CacheEntity cache;

    @Id
    @Column(name = "id", nullable = false)
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "date_visite", nullable = true)
    public Timestamp getDateVisite()
    {
        return dateVisite;
    }

    public void setDateVisite(Timestamp dateVisite)
    {
        this.dateVisite = dateVisite;
    }

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    public UtilisateurEntity getUtilisateur()
    {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntity utilisateur)
    {
        this.utilisateur = utilisateur;
    }

    @ManyToOne
    @JoinColumn(name = "cache_id", nullable = false)
    public CacheEntity getCache()
    {
        return cache;
    }

    public void setCache(CacheEntity cache)
    {
        this.cache = cache;
    }

    @Basic
    @Column(name = "commentaire", nullable = true, length = -1)
    public String getCommentaire()
    {
        return commentaire;
    }

    public void setCommentaire(String commentaire)
    {
        this.commentaire = commentaire;
    }

    @Basic
    @Column(name = "statut", nullable = false, length = 25)
    public String getStatut()
    {
        return statut;
    }

    public void setStatut(String statut)
    {
        this.statut = statut;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisiteEntity that = (VisiteEntity) o;
        return id == that.id &&
                utilisateur.getId() == that.utilisateur.getId() &&
                cache.getId() == that.cache.getId() &&
                Objects.equals(dateVisite, that.dateVisite) &&
                Objects.equals(commentaire, that.commentaire) &&
                Objects.equals(statut, that.statut);
    }

    public String toString(){
        return  " | Id : " + this.id + "\n" +
                " | Date visite : " + this.dateVisite + "\n" +
                " | Commentaire : " + this.commentaire + "\n" +
                " | Statut : " + this.statut + "\n" ;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, dateVisite, utilisateur.getId(), cache.getId(), commentaire, statut);
    }
}
