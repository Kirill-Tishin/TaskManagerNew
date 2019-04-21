package entityH;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Task", schema = "main", catalog = "")
public class TaskEntity {
    private int idTask;
    private String nameTask;
    private String descriptionTask;
    private Date dateTask;
    private Time timeTask;
    private int idUser;
    private UserEntity userByIdUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task",nullable = false)
    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    @Basic
    @Column(name = "name_task")
    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    @Basic
    @Column(name = "DescriptionTask")
    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    @Basic
    @Column(name = "dateTask")
    public Date getDateTask() {
        return dateTask;
    }

    public void setDateTask(Date dateTask) {
        this.dateTask = dateTask;
    }

    @Basic
    @Column(name = "timeTask")
    public Time getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(Time timeTask) {
        this.timeTask = timeTask;
    }

    @Basic
    @Column(name = "id_user")
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
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
    @JoinColumn(name = "id_user",referencedColumnName ="id_user",nullable = false,insertable = false, updatable = false)
    public UserEntity getUserByIdUser() {
        return userByIdUser;
    }

    public void setUserByIdUser(UserEntity userByIdUser) {
        this.userByIdUser = userByIdUser;
    }
}