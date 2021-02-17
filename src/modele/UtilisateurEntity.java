package modele;

import org.bson.types.ObjectId;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@org.mongodb.morphia.annotations.Entity
@Table(name = "utilisateur", schema = "la2-geocache", catalog = "")
public class UtilisateurEntity
{

    @org.mongodb.morphia.annotations.Id
    private ObjectId _id;

    private String id;
    private String pseudo;
    private String description;
    private String avatar;
    private Set<CacheEntity> caches;
    private Set<VisiteEntity> visites;

    @Id
    @Column(name = "id", nullable = false, length = 100)
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    @Transient
    public ObjectId get_id()
    {
        return _id;
    }

    public void set_id(ObjectId _id)
    {
        this._id = _id;
    }

    @Basic
    @Column(name = "pseudo", nullable = false, length = 50)
    public String getPseudo()
    {
        return pseudo;
    }

    public void setPseudo(String pseudo)
    {
        this.pseudo = pseudo;
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
    @Column(name = "avatar", nullable = false, length = 255)
    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    @OneToMany(mappedBy="proprietaire")
    public Set<CacheEntity> getCaches() {return caches; }

    public void setCaches(Set<CacheEntity> caches){this.caches = caches ;}

    public void addCaches(CacheEntity cache){this.caches.add(cache) ;}
    public void removeCaches(CacheEntity cache){this.caches.remove(cache) ;}


    @OneToMany(mappedBy="utilisateur")
    public Set<VisiteEntity> getVisites() {return visites; }

    public void setVisites(Set<VisiteEntity> visites){this.visites = visites ;}

    public void addVisite(VisiteEntity visite){this.visites.add(visite) ;}
    public void removeVisite(VisiteEntity visite){this.visites.remove(visite) ;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateurEntity that = (UtilisateurEntity) o;
        return  Objects.equals(pseudo, that.pseudo) &&
                Objects.equals(description, that.description) &&
                Objects.equals(avatar, that.avatar);
    }

    public String toString(){
        return  " | Id : " + (this.id!=null?this.id:this._id) + "\n" +
                " | Pseudo : " + this.pseudo + "\n" +
                " | Description : " + this.description + "\n" +
                " | Avatar : " + this.avatar + "\n";
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, pseudo, description, avatar);
    }
}
