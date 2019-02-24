package Thread;

import DataBase.DataBase;
import PojoClass.Task;
import javafx.application.Platform;

import java.sql.SQLException;

public class NotificationThread extends Thread {
    private TestFX testFX;
    private Task task; //final
    private DataBase dataBase;

    public NotificationThread(Task task,DataBase dataBase) {
        this.task = task;
        this.dataBase = dataBase;
        testFX = new TestFX(task,dataBase);
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
