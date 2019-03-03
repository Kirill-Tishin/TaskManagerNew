package Thread;

import Compound_DB_and_pojo.Compound;
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


    public MonitoringThread(Compound compound, User user) throws SQLException {
        this.compound = compound;
        this.user = user;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(15000); //Приостановка на 15 секунд
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

                //TODO: do not shot notification for same task twice until we got response from user
                if ((dateTask.getTime() <= dateNow.getTime()) && (timeTask.getTime() <= timeNow.getTime())) {
                    notificationThread = new NotificationThread(user,task,compound);
                    notificationThread.start();
                }
            }
            Set<Thread> threads = Thread.getAllStackTraces().keySet();
            for (Thread thread : threads) {
                System.out.println("Thread: " + thread + ":" + "state:" + thread.getState());
            }
        }
    }

    //Сравнение 2-х потоков
 /*   public static boolean equalStreams(Stream<?>...streams) {
        List<Iterator<?>>is = Arrays.stream(streams).map(Stream::iterator).collect(Collectors.toList());
        while(is.stream().allMatch(Iterator::hasNext))
            if(is.stream().map(Iterator::next).distinct().limit(2).count()>1) return false;
        return is.stream().noneMatch(Iterator::hasNext);
    }*/
}