package modele;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "utilisateur", schema = "la2-geocache", catalog = "")
public class UtilisateurEntity
{
    private int id;
    private String pseudo;
    private String description;
    private String avatar;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateurEntity that = (UtilisateurEntity) o;
        return id == that.id &&
                Objects.equals(pseudo, that.pseudo) &&
                Objects.equals(description, that.description) &&
                Objects.equals(avatar, that.avatar);
    }

    public String toString(){
        return  " | Id : " + this.id + "\n" +
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
