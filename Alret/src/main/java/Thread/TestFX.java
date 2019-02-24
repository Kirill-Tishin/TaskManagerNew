package Thread;

import DataBase.DataBase;
import PojoClass.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class TestFX implements Initializable {
    private Task task;
    private DataBase dataBase;

    public TestFX(Task task,DataBase dataBase){
        this.task = task;
        this.dataBase = dataBase;
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
            //Добавление 5-ти минтут к задаче
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(task.getTimeTask());
            calendar.add(Calendar.MINUTE, 5);
            dataBase.updateTask(task.getId_User(),task.getIdTask(),task.getNameTask(),task.getDescriptionTask(),calendar.getTime(),new Time(calendar.getTimeInMillis()));
        } else {
            dataBase.deleteTask(task.getIdTask());
        }
    }
}