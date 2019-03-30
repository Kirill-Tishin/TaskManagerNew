package entityH;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "main", catalog = "")
public class UserEntity {
    private Short idUser;
    private Object nameUser;

    @Id
    @Column(name = "id_user")
    public Short getIdUser() {
        return idUser;
    }

    public void setIdUser(Short idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "name_user")
    public Object getNameUser() {
        return nameUser;
    }

    public void setNameUser(Object nameUser) {
        this.nameUser = nameUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(idUser, that.idUser) &&
                Objects.equals(nameUser, that.nameUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, nameUser);
    }
}
