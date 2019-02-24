package Signal;

import DataBase.DataBase;
import PojoClass.User;
import Thread.MonitoringThread;

import java.sql.SQLException;

public class Signal {
    private User user;
    private DataBase dataBase;
    //TODO: why variable is static? ***********************************************
    //private static MonitoringThread monitoringThread;
    private MonitoringThread monitoringThread;

    public Signal(DataBase dataBase, User user) {
        this.dataBase = dataBase;
        this.user = user;

        //TODO: use separate method for this ***********************************
      /*  monitoringThread = new MonitoringThread(dataBase,user,0);
        monitoringThread.start();*/
    }

    //Запуск потока с уведомлениями
    public void startSignal() throws SQLException {
        monitoringThread = new MonitoringThread(dataBase, user);
        monitoringThread.start();
    }

    //Остановка потока с уведомлениями
    public void stopSignal() {
        if (!monitoringThread.currentThread().isInterrupted()) {
            monitoringThread.interrupt(); //Остановка потока
            System.out.println("Поток уведомлений остановлен");
        } else {
            System.out.println("Поток уведомлений еще не запущен");
        }
    }
}