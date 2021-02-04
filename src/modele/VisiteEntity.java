package modele;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "visite", schema = "la2-geocache", catalog = "")
public class VisiteEntity
{
    private int id;
    private Timestamp dateVisite;
    private int utilisateurId;
    private int cacheId;
    private String commentaire;
    private String statut;

    @Id
    @Column(name = "id", nullable = false)
    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    @Basic
    @Column(name = "utilisateur_id", nullable = false)
    public int getUtilisateurId()
    {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId)
    {
        this.utilisateurId = utilisateurId;
    }

    @Basic
    @Column(name = "cache_id", nullable = false)
    public int getCacheId()
    {
        return cacheId;
    }

    public void setCacheId(int cacheId)
    {
        this.cacheId = cacheId;
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
                utilisateurId == that.utilisateurId &&
                cacheId == that.cacheId &&
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
        return Objects.hash(id, dateVisite, utilisateurId, cacheId, commentaire, statut);
    }
}
