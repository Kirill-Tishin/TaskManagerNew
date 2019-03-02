package PojoClass;
import java.sql.Time;
import java.util.Date;

public class Task {
    private int idTask;
    private String nameTask;
    private String descriptionTask;
    private int idUser;
    private Date dateTask; //dateTask

    private Time timeTask; //timeTask

    public Task(int idTask, int idUser, String nameTask, String descriptionTask, Date dateTask, Time timeTask) {
        this.idTask=idTask;
        this.idUser = idUser;
        this.nameTask=nameTask;
        this.descriptionTask =descriptionTask;
        this.dateTask = dateTask;
        this.timeTask = timeTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateTask() {
        return dateTask;
    }

    public void setDateTask(Date dateTask) {
        this.dateTask = dateTask;
    }

    public Time getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(Time timeTask) {
        this.timeTask = timeTask;
    }
}
