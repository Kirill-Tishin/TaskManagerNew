package ControllerClasses;

import DataBase.DataBaseOLD;
import PojoClass.Task;
import PojoClass.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerAddTask implements Initializable {
    private DataBaseOLD dataBaseOLD;
    private User user;
    private Task task;

    public DataBaseOLD getDataBaseOLD() {
        return dataBaseOLD;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setDataBaseOLD(DataBaseOLD dataBaseOLD) {
        this.dataBaseOLD = dataBaseOLD;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public TextField textFieldNameTask;
    public TextField textFieldHourTask;
    public TextField textFieldMinuteTask;
    public TextField textFieldDescriptionTask;
    public Button buttonAdd;
    private Alert alert;
    public DatePicker datePic;

    @FXML
    private void addAlter(String strOutput, String identification) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(identification);
        alert.setHeaderText(null);
        alert.setContentText(strOutput);
        alert.showAndWait();
    }

    //Проверка, нет ли букв в строке
    private boolean checkNum(String str) {
        boolean flag = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                addAlter("Поле времени должно содержать только цыфры", "Ошибка");
                flag = false;
                break;
            }
        }
        return flag;
    }

    public void setButtonAdd() throws SQLException, ParseException {
        int hour = 0;
        int minute = 0;
        if (!textFieldNameTask.getText().equals("")) {
            String dateStr = datePic.getEditor().getText();
            DateFormat format = new SimpleDateFormat("d.M.yyyy");
            Date date = format.parse(dateStr);

            if ((!textFieldHourTask.getText().equals("")) && (!textFieldMinuteTask.getText().equals(""))) {
                String hourText = textFieldHourTask.getText();
                String minuteText = textFieldMinuteTask.getText();
                if ((checkNum(hourText)) && (checkNum(minuteText))) {
                    hour = Integer.parseInt(textFieldHourTask.getText());
                    minute = Integer.parseInt(textFieldMinuteTask.getText());
                    if ((hour >= 0) && (hour < 24) && (minute >= 0) && (minute < 60)) {
                        addTask(hour, minute, date);
                    } else {
                        addAlter("Время должно быть корректным, т.е. часы от 0 до 23, а минуты от 0 до 59!", "Ошибка");
                    }
                }
            } else {
                addTask(hour, minute, date);
            }
        } else {
            addAlter("Введите название задачи", "Ошибка");
        }
    }

    private void addTask(int hour, int minute, Date date) throws SQLException {
        Calendar calendarNow = Calendar.getInstance();
        Date dateNow = calendarNow.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.MINUTE, minute);

        Date dateTask = calendar.getTime();

        //TODO: create interface Validator with "validate" method, and use its implementations below
        if (dateNow.getTime() <= dateTask.getTime()) {
            if (buttonAdd.getText().equals("Добавить")) {
                dataBaseOLD.addTask(user.getIdUser(), textFieldNameTask.getText(), textFieldDescriptionTask.getText(), dateTask, new Time(dateTask.getTime()));
                addAlter("Задача добавлена", "Информация");
            } else {
                dataBaseOLD.updateTask(user.getIdUser(), task.getIdTask(), textFieldNameTask.getText(), textFieldDescriptionTask.getText(), dateTask, new Time(dateTask.getTime()));
                addAlter("Задача изменена", "Информация");
            }
        } else {
            addAlter("Дата уже устарела", "Ошибка");
        }
    }
}