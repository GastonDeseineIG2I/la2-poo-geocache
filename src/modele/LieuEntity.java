package modele;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@org.mongodb.morphia.annotations.Entity
@Table(name = "lieu", schema = "la2-geocache", catalog = "")
public class LieuEntity
{
    private String libelle;
    @org.mongodb.morphia.annotations.Id
    private String id;

    private Set<CacheEntity> caches;

    @Basic
    @Column(name = "libelle", nullable = false, length = 100)
    public String getLibelle()
    {
        return libelle;
    }

    public void setLibelle(String libelle)
    {
        this.libelle = libelle;
    }

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

    @OneToMany(mappedBy="lieu")
    public Set<CacheEntity> getCaches() {return caches; }

    public void setCaches(Set<CacheEntity> caches){this.caches = caches ;}

    public void addCaches(CacheEntity cache){this.caches.add(cache) ;}
    public void removeCaches(CacheEntity cache){this.caches.remove(cache) ;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LieuEntity that = (LieuEntity) o;
        return id == that.id &&
                Objects.equals(libelle, that.libelle);
    }

    public String toString(){
        return  " | Id : " + this.id + "\n" +
                " | Libelle : " + this.libelle + "\n";
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(libelle, id);
    }
}
