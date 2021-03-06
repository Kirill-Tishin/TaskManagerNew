package Thread;

import Compound.CompoandForHib;
import entityH.TaskEntity;
import entityH.UserEntity;

import java.sql.Time;
import java.util.*;

public class MonitoringThread extends Thread {
    private List<TaskEntity> taskList; //Тут все задачи одного пользователя
    private CompoandForHib compound;
    private UserEntity user;
    private NotificationThread notificationThread;
    private ArrayList<TaskEntity> tasksWorking;

    public MonitoringThread(CompoandForHib compound, UserEntity user) {
        this.compound = compound;
        this.user = user;
        tasksWorking = new ArrayList<TaskEntity>();
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(5000); //Приостановка на 15 секунд //15000
            } catch (InterruptedException e) {
                break;
            }
            this.taskList = new ArrayList<>(user.getTaskByIdUser()); //Получене записей пользователя не из бд, а из его листа
            for (int i = 0; i < taskList.size(); i++) {
                TaskEntity task = taskList.get(i);
                Date dateTask = task.getDateTask();
                Time timeTask = task.getTimeTask();

                Calendar calendar = Calendar.getInstance();
                Date dateNow = calendar.getTime();
                Time timeNow = new Time(dateNow.getTime());

                if ((dateTask.getTime() <= dateNow.getTime()) && (timeTask.getTime() <= timeNow.getTime())) {
                    if(!tasksWorking.contains(task)){ //Проверка существрования элемента
                        tasksWorking.add(task);
                        notificationThread = new NotificationThread(user,task,compound);
                        notificationThread.start();
                    }else{
                        System.out.println("Task выведена");
                        //Обновление ,если задача уже удалена, чтобы не получилось, случайно, листа огромного
                        // размера из задач, которые уже удалены или отложены,
                        // но в этом листе их старая версия уже осталась
                        ArrayList<TaskEntity> newTasks = new ArrayList<>();
                        for(int j=0;j<taskList.size();j++){
                            for(int k=0;k<tasksWorking.size();k++){
                                if(taskList.get(j)==tasksWorking.get(k)){
                                    newTasks.add(tasksWorking.get(k));
                                }
                            }
                        }
                        tasksWorking=newTasks;
                    }
                }
            }
        }
    }
}