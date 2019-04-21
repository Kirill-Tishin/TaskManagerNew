package Signal;

import Compound.CompoandForHib;
import Thread.MonitoringThread;
import entityH.UserEntity;

import java.sql.SQLException;

public class Signal {
    private UserEntity userEntity;
    private CompoandForHib compoandForHib;
    private MonitoringThread monitoringThread;

    public Signal(CompoandForHib compoandForHib, UserEntity userEntity) {
        this.compoandForHib = compoandForHib;
        this.userEntity = userEntity;
    }

    //Запуск потока с уведомлениями
    public void startSignal() throws SQLException {
        monitoringThread = new MonitoringThread(compoandForHib, userEntity);
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