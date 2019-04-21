package entityH;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "User", schema = "main", catalog = "")
public class UserEntity {
    private int idUser;
    private String nameUser;
    private Collection<TaskEntity> taskByIdUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user",nullable = false)
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "name_user")
    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
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

    @OneToMany(mappedBy = "userByIdUser")
    public Collection<TaskEntity> getTaskByIdUser() {
        return taskByIdUser;
    }

    public void setTaskByIdUser(Collection<TaskEntity> taskByIdUser) {
        this.taskByIdUser = taskByIdUser;
    }
}