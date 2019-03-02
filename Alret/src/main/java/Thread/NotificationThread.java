package Thread;

import DataBase.DataBaseOLD;
import PojoClass.Task;
import javafx.application.Platform;

import java.sql.SQLException;

public class NotificationThread extends Thread {
    private TestFX testFX;
    private Task task; //final
    private DataBaseOLD dataBaseOLD;

    public NotificationThread(Task task, DataBaseOLD dataBaseOLD) {
        this.task = task;
        this.dataBaseOLD = dataBaseOLD;
        testFX = new TestFX(task, dataBaseOLD);
    }

    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    testFX.addAlter("Пришло время выполнения задачи " + task.getIdTask() + " " + task.getNameTask() + "\n Date PojoClass.Task " + task.getDateTask() + " Time PojoClass.Task " + task.getTimeTask(), "Задача " + task.getNameTask());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
