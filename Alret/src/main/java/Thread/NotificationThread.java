package Thread;

import Compound.Compound;
import PojoClass.Task;
import PojoClass.User;
import javafx.application.Platform;

import java.sql.SQLException;

public class NotificationThread extends Thread {
    private TestFX testFX;
    private Task task;
    private User user;

    public NotificationThread(User user, Task task, Compound compound) {
        this.task = task;
        this.user = user;
        testFX = new TestFX(user, task, compound);
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
