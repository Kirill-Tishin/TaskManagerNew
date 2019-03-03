package ControllerClasses;

import Compound.Compound;
import InterfaceDao.TaskInterface;
import InterfaceDao.UserInterface;
import PojoClass.Task;
import PojoClass.User;
import ValidatorClass.Validator;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerAddTask implements Initializable {
    private Compound compound;
    private UserInterface userInterface;
    private TaskInterface taskInterface;
    private User user;
    private Task task;
    private Validator validator = new Validator();

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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

    public void setButtonAdd() throws SQLException, ParseException {
        int hour = 0;
        int minute = 0;
        if (validator.checkNameTask(textFieldNameTask.getText())) {
            String dateStr = datePic.getEditor().getText();
            DateFormat format = new SimpleDateFormat("d.M.yyyy");
            Date date = format.parse(dateStr);
            if ((!textFieldHourTask.getText().equals("")) && (!textFieldMinuteTask.getText().equals(""))) {
                String hourText = textFieldHourTask.getText();
                String minuteText = textFieldMinuteTask.getText();
                if ((validator.checkNum(hourText)) && (validator.checkNum(minuteText))) {
                    hour = Integer.parseInt(textFieldHourTask.getText());
                    minute = Integer.parseInt(textFieldMinuteTask.getText());
                    if (validator.checkTimeMinAndHour(hour,minute)) {
                        addTask(hour, minute, date);
                    }
                }
            } else {
                addTask(hour, minute, date);
            }
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

        //TODO: create interface Validator with "validate" method, and use its implementations below ********************************
        if (validator.checkOldTime(dateNow,dateTask)) {
            if (buttonAdd.getText().equals("Добавить")) {
                ArrayList arrayListNew = user.getTaskList();
                //Обновили в бд
                taskInterface.addTask(user.getIdUser(), textFieldNameTask.getText(), textFieldDescriptionTask.getText(), dateTask, new Time(dateTask.getTime()));//Обновили у пользователя
                arrayListNew.add(taskInterface.getTask(compound.getMaxIdTask())); //Последняя добавленная задача
                //Обновили у пользователя
                user.setTaskList(arrayListNew);
                addAlter("Задача добавлена", "Информация");
            } else {
                ArrayList arrayListNew = user.getTaskList();
                //Обновили в бд
                taskInterface.updateTask(user.getIdUser(), task.getIdTask(), textFieldNameTask.getText(), textFieldDescriptionTask.getText(), dateTask, new Time(dateTask.getTime()));
                //Обновили у пользователя
                arrayListNew.remove(task);
                Task taskNew = new Task(task.getIdTask(), user.getIdUser(), textFieldNameTask.getText(), textFieldDescriptionTask.getText(), dateTask, new Time(dateTask.getTime()));
                arrayListNew.add(taskNew);
                user.setTaskList(arrayListNew);
                addAlter("Задача изменена", "Информация");
            }
        }
    }

    public Compound getCompound() {
        return compound;
    }

    public void setCompound(Compound compound) {
        this.compound = compound;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public TaskInterface getTaskInterface() {
        return taskInterface;
    }

    public void setTaskInterface(TaskInterface taskInterface) {
        this.taskInterface = taskInterface;
    }
}