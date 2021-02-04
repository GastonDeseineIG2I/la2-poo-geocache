package modele;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lieu", schema = "la2-geocache", catalog = "")
public class LieuEntity
{
    private String libelle;
    private int id;

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
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

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
