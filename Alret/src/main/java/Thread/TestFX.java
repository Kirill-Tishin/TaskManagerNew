package Thread;

import Compound.Compound;
import InterfaceDao.TaskInterface;
import PojoClass.Task;
import PojoClass.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class TestFX implements Initializable {
    private User user;
    private Task task;
    private TaskInterface taskInterface;

    public TestFX(User user, Task task, Compound compound) {
        this.task = task;
        this.user = user;
        this.taskInterface = compound.getTaskInterface();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Alert alert;

    @FXML
    void addAlter(String strOutput, String identification) throws SQLException {
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
          //Добавление 5-ти минтут к нынешнему времени
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendar.getTime());
            calendar.add(Calendar.MINUTE, 5);

            ArrayList arrayListNew = user.getTaskList();
            //Обновили в бд
            taskInterface.updateTask(task.getIdUser(), task.getIdTask(), task.getNameTask(), task.getDescriptionTask(), calendar.getTime(), new Time(calendar.getTimeInMillis()));
            arrayListNew.remove(task); //Обновили у пользователя
            Task taskNew = new Task(task.getIdTask(), task.getIdUser(), task.getNameTask(), task.getDescriptionTask(), calendar.getTime(), new Time(calendar.getTimeInMillis()));
            arrayListNew.add(taskNew);
            user.setTaskList(arrayListNew);
        } else {
            ArrayList arrayListNew = user.getTaskList();
            arrayListNew.remove(task);
            user.setTaskList(arrayListNew); //Удилили задачу из листа пользователя
            taskInterface.deleteTask(task); //Удилили задачу из бд
        }
    }
}