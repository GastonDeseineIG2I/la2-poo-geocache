package modele;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@org.mongodb.morphia.annotations.Entity
@Table(name = "cache", schema = "la2-geocache", catalog = "")
public class CacheEntity
{
    @org.mongodb.morphia.annotations.Id
    private String id;

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
    private String nature;
    private String typeCache;
    private String codeSecret;
    private String statut;
    private LieuEntity lieu;
    private UtilisateurEntity proprietaire;
    private Set<VisiteEntity> visites;

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
    @Column(name = "latitude", nullable = true, precision = 12, scale = 10)
    public BigDecimal getLatitude()
    {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude)
    {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 13, scale = 10)
    public BigDecimal getLongitude()
    {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude)
    {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Basic
    @Column(name = "nature", nullable = false, length = 10)
    public String getNature()
    {
        return nature;
    }

    public void setNature(String nature)
    {
        this.nature = nature;
    }

    @Basic
    @Column(name = "code_secret", nullable = false, length = 50)
    public String getCodeSecret()
    {
        return codeSecret;
    }

    public void setCodeSecret(String codeSecret)
    {
        this.codeSecret = codeSecret;
    }

    @Basic
    @Column(name = "type_cache", nullable = false, length = 25)
    public String getTypeCache()
    {
        return typeCache;
    }

    public void setTypeCache(String typeCache)
    {
        this.typeCache = typeCache;
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

    @ManyToOne
    @JoinColumn(name = "lieu_id", nullable = true)
    public LieuEntity getLieu()
    {
        return lieu;
    }

    public void setLieu(LieuEntity lieu)
    {
        this.lieu = lieu;
    }

    @ManyToOne
    @JoinColumn(name = "proprietaire_id", nullable = false)
    public UtilisateurEntity getProprietaire()
    {
        return proprietaire;
    }

    public void setProprietaire(UtilisateurEntity proprietaire)
    {
        this.proprietaire = proprietaire;
    }




    @OneToMany(mappedBy="cache")
    public Set<VisiteEntity> getVisites() {return visites; }

    public void setVisites(Set<VisiteEntity> visites){this.visites = visites ;}

    public void addVisite(VisiteEntity visite){this.visites.add(visite) ;}
    public void removeVisite(VisiteEntity visite){this.visites.remove(visite) ;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheEntity that = (CacheEntity) o;
        return id == that.id &&
                proprietaire.getId() == that.proprietaire.getId() &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(description, that.description) &&
                Objects.equals(nature, that.nature) &&
                Objects.equals(statut, that.statut) &&
                Objects.equals(lieu.getId(), that.lieu.getId());
    }

    public String toString(){
        return  " | Id : " + this.id + "\n" +
                " | GPS : " + this.latitude + " ; " + this.longitude + "\n" +
                " | Description : " + this.description + "\n" +
                " | Lieu : " + this.lieu.getLibelle() + "\n" +
                " | Proprietaire : " + this.proprietaire.getPseudo() + "\n" +
                " | Nature : " + this.nature + "\n" +
                " | Statut : " + this.statut + "\n" +
                " | Type cache : " + this.typeCache + "\n" +
                " | Code secret : " + this.codeSecret + "\n" ;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, latitude, longitude, description, nature, statut);
    }
}
