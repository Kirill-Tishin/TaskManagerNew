package Thread;

import Compound.CompoandForHib;
import entityH.TaskEntity;
import entityH.UserEntity;
import javafx.application.Platform;

import java.sql.SQLException;
import java.text.ParseException;

public class NotificationThread extends Thread {
    private TestFX testFX;
    private TaskEntity task;

    public NotificationThread(UserEntity user, TaskEntity task, CompoandForHib compound) {
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
