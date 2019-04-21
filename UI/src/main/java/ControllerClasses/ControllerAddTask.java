package ControllerClasses;

import Compound.CompoandForHib;
import ValidatorClass.Validator;
import entityH.TaskEntity;
import entityH.UserEntity;
import hibernateDao.TaskDaoHib;
import hibernateDao.UserDaoHib;
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
    private CompoandForHib compound;
    private UserDaoHib userDaoHib;
    private TaskDaoHib taskDaoHib;
    private UserEntity user;
    private TaskEntity task;
    private Validator validator = new Validator();

    public UserEntity getUser() {
        return user;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public void setUser(UserEntity user) {
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

        if (validator.checkOldTime(dateNow,dateTask)) {
            if (buttonAdd.getText().equals("Добавить")) {
                ArrayList<TaskEntity> arrayListNew = new ArrayList<>(user.getTaskByIdUser());
                //Обновили в бд
                TaskEntity taskEntityNew = new TaskEntity();
                taskEntityNew.setNameTask(textFieldNameTask.getText());
                taskEntityNew.setDescriptionTask(textFieldDescriptionTask.getText());
                taskEntityNew.setDateTask(dateTask);
                taskEntityNew.setTimeTask(new Time(dateTask.getTime()));
                taskEntityNew.setUserByIdUser(task.getUserByIdUser());
                taskEntityNew.setIdUser(task.getIdUser());

                taskDaoHib.deleteTask(task);
                taskDaoHib.addTask(taskEntityNew);

                //Обновление у пользователя
                arrayListNew.add(taskEntityNew);
                user.setTaskByIdUser(arrayListNew);
                addAlter("Задача добавлена", "Информация");
            } else {
                ArrayList arrayListNew = new ArrayList<>(user.getTaskByIdUser());
                //Обновили в бд
                TaskEntity taskEntityNew = new TaskEntity();
                taskEntityNew.setIdTask(task.getIdTask());
                taskEntityNew.setNameTask(textFieldNameTask.getText());
                taskEntityNew.setDescriptionTask(textFieldDescriptionTask.getText());
                taskEntityNew.setDateTask(dateTask);
                taskEntityNew.setTimeTask(new Time(dateTask.getTime()));
                taskEntityNew.setUserByIdUser(task.getUserByIdUser());
                taskEntityNew.setIdUser(task.getIdUser());
                taskDaoHib.updateTask(taskEntityNew); //Обновили в бд todo: Check work
                //Обновили у пользователя
                arrayListNew.remove(task);
                arrayListNew.add(taskEntityNew);
                user.setTaskByIdUser(arrayListNew);
                addAlter("Задача изменена", "Информация");
            }
        }
    }

    public CompoandForHib getCompound() {
        return compound;
    }

    public void setCompound(CompoandForHib compound) {
        this.compound = compound;
    }

    public UserDaoHib getUserDaoHib() {
        return userDaoHib;
    }

    public void setUserDaoHib(UserDaoHib userDaoHib) {
        this.userDaoHib = userDaoHib;
    }

    public TaskDaoHib getTaskDaoHib() {
        return taskDaoHib;
    }

    public void setTaskDaoHib(TaskDaoHib taskDaoHib) {
        this.taskDaoHib = taskDaoHib;
    }
}