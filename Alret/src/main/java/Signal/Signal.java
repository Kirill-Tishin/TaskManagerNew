package Signal;

import Compound.Compound;
import PojoClass.User;
import Thread.MonitoringThread;

import java.sql.SQLException;

public class Signal {
    private User user;
    private Compound compound;
    private MonitoringThread monitoringThread;

    public Signal(Compound compound, User user) {
        this.compound = compound;
        this.user = user;
    }

    //Запуск потока с уведомлениями
    public void startSignal() throws SQLException {
        monitoringThread = new MonitoringThread(compound, user);
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