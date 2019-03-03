package PojoClass;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
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
}
