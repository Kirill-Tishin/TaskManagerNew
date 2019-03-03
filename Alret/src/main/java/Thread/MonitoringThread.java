package Thread;

import Compound.Compound;
import PojoClass.Task;
import PojoClass.User;

import java.sql.SQLException;
import java.sql.Time;
import java.util.*;
public class MonitoringThread extends Thread {
    private List<Task> taskList; //Тут все задачи одного пользователя
    private Compound compound;
    private User user;
    private NotificationThread notificationThread;
    private ArrayList<Task> tasksWorking = new ArrayList<Task>();

    public MonitoringThread(Compound compound, User user) throws SQLException {
        this.compound = compound;
        this.user = user;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(5000); //Приостановка на 15 секунд //15000
            } catch (InterruptedException e) {
                break;
            }
            this.taskList = user.getTaskList(); //Получене записей пользователя не из бд, а из его листа
            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                Date dateTask = task.getDateTask();
                Time timeTask = task.getTimeTask();

                Calendar calendar = Calendar.getInstance();
                Date dateNow = calendar.getTime();
                Time timeNow = new Time(dateNow.getTime());

                //TODO: do not shot notification for same task twice until we got response from user ******************************************
                if ((dateTask.getTime() <= dateNow.getTime()) && (timeTask.getTime() <= timeNow.getTime())) {
                    if(!tasksWorking.contains(task)){ //Проверка существрования элемента
                        tasksWorking.add(task);
                        notificationThread = new NotificationThread(user,task,compound);
                        notificationThread.start();
                    }else{
                        System.out.println("Task выведена");
                        //Обновление ,если задача ужеудалена, чтобы не получилось, случайно, листа огромного размера из задач, которые уже удалены или отложены,
                        // но в этом листе их старая версия уже осталась
                        ArrayList<Task> newTasks = new ArrayList<>();
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