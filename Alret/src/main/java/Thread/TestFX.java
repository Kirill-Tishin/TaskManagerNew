package Thread;

import Compound.CompoandForHib;
import entityH.TaskEntity;
import entityH.UserEntity;
import hibernateDao.TaskDaoHib;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.*;

public class TestFX implements Initializable {
    private UserEntity user;
    private TaskEntity task;
    private TaskDaoHib taskDaoHib;

    public TestFX(UserEntity user, TaskEntity task, CompoandForHib compound) {
        this.task = task;
        this.user = user;
        this.taskDaoHib = compound.getTaskDaoHib();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Alert alert;

    @FXML
    void addAlter(String strOutput, String identification) throws SQLException, ParseException {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(identification);
        alert.setHeaderText(null);
        alert.setContentText(strOutput);

        ButtonType postpone = new ButtonType("Отложить");
        ButtonType remove = new ButtonType("Удалить");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(postpone, remove);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == postpone) {
         // Добавление 5-ти минтут к нынешнему времени
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendar.getTime());
            calendar.add(Calendar.MINUTE, 5);

          /*  Date dateNow = new Date();
            Date date = new Date(dateNow.getTime()+5000L*60);*/

            ArrayList<TaskEntity> arrayListNew = new ArrayList<>(user.getTaskByIdUser());
            //Обновили в бд
            TaskEntity taskEntityNew = new TaskEntity();
            taskEntityNew.setNameTask(task.getNameTask());
            taskEntityNew.setDescriptionTask(task.getDescriptionTask());
            taskEntityNew.setDateTask(calendar.getTime());
            taskEntityNew.setTimeTask(new Time(calendar.getTimeInMillis()));
            taskEntityNew.setUserByIdUser(task.getUserByIdUser());
            taskEntityNew.setIdUser(task.getIdUser());
            taskDaoHib.deleteTask(task);
            taskDaoHib.addTask(taskEntityNew);

            arrayListNew.remove(task); //Обновили у пользователя
            arrayListNew.add(taskEntityNew);
            user.setTaskByIdUser(arrayListNew);
        } else {
            ArrayList arrayListNew = new ArrayList<>(user.getTaskByIdUser());
            arrayListNew.remove(task);
            user.setTaskByIdUser(arrayListNew); //Удилили задачу из листа пользователя
            taskDaoHib.deleteTask(task); //Удилили задачу из бд
        }
    }
}