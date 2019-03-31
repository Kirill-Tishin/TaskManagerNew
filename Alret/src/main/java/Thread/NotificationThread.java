package Thread;

import Compound.Compound;
import PojoClass.Task;
import PojoClass.User;
import javafx.application.Platform;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class NotificationThread extends Thread {
    private TestFX testFX;
    private Task task;

    public NotificationThread(User user, Task task, Compound compound) {
        this.task = task;
        testFX = new TestFX(user, task, compound);
    }

    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    testFX.addAlter("Пришло время выполнения задачи " + task.getIdTask() + " " + task.getNameTask() + "\n Date PojoClass.Task " + task.getDateTask() + " Time PojoClass.Task " + task.getTimeTask(), "Задача " + task.getNameTask());
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
