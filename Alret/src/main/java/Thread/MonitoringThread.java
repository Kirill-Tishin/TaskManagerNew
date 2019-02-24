package Thread;

import DataBase.DataBase;
import PojoClass.Task;
import PojoClass.User;

import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

//TODO: Rename class to Thread.MonitoringThread **********************************************
//TODO: Create also SignalThread for notfications **************************************
public class MonitoringThread extends Thread {
    private List<Task> taskList; //Тут все задачи одного пользователя
    private DataBase dataBase;
    private User user;
    private NotificationThread notificationThread;

    //TODO: remove "n" from construstor, use logic with "interrupted" flag **************************************
    public MonitoringThread(DataBase dataBase, User user) throws SQLException {
        this.dataBase = dataBase;
        this.user = user;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(15000); //Приостановка на 15 секунд
            } catch (InterruptedException e) {
                break;
            }
            try {
                this.taskList = dataBase.getTasksUser(user.getIdUser(),dataBase); //Постоянное обновление листа записей
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < taskList.size(); i++) {
                Task task = taskList.get(i);
                Date dateTask = task.getDateTask();
                Time timeTask = task.getTimeTask();

                Calendar calendar = Calendar.getInstance();
                Date dateNow = calendar.getTime();
                Time timeNow = new Time(dateNow.getTime());

                //TODO: do not shot notification for same task twice until we got response from user
                if ((dateTask.getTime() <= dateNow.getTime()) && (timeTask.getTime() <= timeNow.getTime())) {
                    notificationThread = new NotificationThread(task,user.getDataBase());
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