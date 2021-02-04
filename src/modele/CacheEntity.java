package modele;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cache", schema = "la2-geocache", catalog = "")
public class CacheEntity
{
    private int id;
    private Integer latitude;
    private Integer longitude;
    private String description;
    private String nature;
    private String typeCache;
    private String statut;
    private Integer lieuId;
    private int proprietaireId;

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
    @Column(name = "latitude", nullable = true, precision = 0)
    public Integer getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Integer latitude)
    {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 0)
    public Integer getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Integer longitude)
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

    @Basic
    @Column(name = "lieu_id", nullable = true)
    public Integer getLieuId()
    {
        return lieuId;
    }

    public void setLieuId(Integer lieuId)
    {
        this.lieuId = lieuId;
    }

    @Basic
    @Column(name = "proprietaire_id", nullable = false)
    public int getProprietaireId()
    {
        return proprietaireId;
    }

    public void setProprietaireId(int proprietaireId)
    {
        this.proprietaireId = proprietaireId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheEntity that = (CacheEntity) o;
        return id == that.id &&
                proprietaireId == that.proprietaireId &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(description, that.description) &&
                Objects.equals(nature, that.nature) &&
                Objects.equals(statut, that.statut) &&
                Objects.equals(lieuId, that.lieuId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, latitude, longitude, description, nature, statut, lieuId, proprietaireId);
    }
}
