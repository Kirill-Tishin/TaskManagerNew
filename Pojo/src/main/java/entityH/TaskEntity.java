package entityH;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Task", schema = "main", catalog = "")
public class TaskEntity {
    private Short idTask;
    private Object nameTask;
    private Object descriptionTask;
    private Object dateTask;
    private Object timeTask;
    private short idUser;
    private UserEntity userByIdUser;

    @Id
    @Column(name = "id_task", nullable = true)
    public Short getIdTask() {
        return idTask;
    }

    public void setIdTask(Short idTask) {
        this.idTask = idTask;
    }

    @Basic
    @Column(name = "name_task", nullable = false, length = 50)
    public Object getNameTask() {
        return nameTask;
    }

    public void setNameTask(Object nameTask) {
        this.nameTask = nameTask;
    }

    @Basic
    @Column(name = "DescriptionTask", nullable = true, length = 150)
    public Object getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(Object descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    @Basic
    @Column(name = "dateTask", nullable = true)
    public Object getDateTask() {
        return dateTask;
    }

    public void setDateTask(Object dateTask) {
        this.dateTask = dateTask;
    }

    @Basic
    @Column(name = "timeTask", nullable = true)
    public Object getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(Object timeTask) {
        this.timeTask = timeTask;
    }

    @Basic
    @Column(name = "id_user", nullable = false)
    public short getIdUser() {
        return idUser;
    }

    public void setIdUser(short idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return idUser == that.idUser &&
                Objects.equals(idTask, that.idTask) &&
                Objects.equals(nameTask, that.nameTask) &&
                Objects.equals(descriptionTask, that.descriptionTask) &&
                Objects.equals(dateTask, that.dateTask) &&
                Objects.equals(timeTask, that.timeTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask, nameTask, descriptionTask, dateTask, timeTask, idUser);
    }

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    public UserEntity getUserByIdUser() {
        return userByIdUser;
    }

    public void setUserByIdUser(UserEntity userByIdUser) {
        this.userByIdUser = userByIdUser;
    }
}
