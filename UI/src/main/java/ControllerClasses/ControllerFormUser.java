package ControllerClasses;

import Compound.CompoandForHib;
import entityH.TaskEntity;
import entityH.UserEntity;
import hibernateDao.TaskDaoHib;
import hibernateDao.UserDaoHib;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerFormUser implements Initializable {
    private CompoandForHib compound;
    private UserDaoHib userDaoHib;
    private TaskDaoHib taskDaoHib;
    private UserEntity user;

    public UserEntity getUser(){ return user; }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private ObservableList<TaskEntity> tasksData;

    @FXML
    public Alert alert;
    public javafx.scene.control.TableView<TaskEntity> tableViewTask;
    public TableColumn<TaskEntity,Integer> colIdTask;
    public TableColumn<TaskEntity,String> colNameTask;
    public TableColumn<TaskEntity,String> colDesTask;
    public TableColumn<TaskEntity, Date> colDateTask;
    public TableColumn<TaskEntity, java.sql.Time> colTimeTask;
    public Button buttonAddTask;
    public Button buttonDellTask;
    public Button buttonChangeTask;

    @FXML
    private void addAlter(String strOutput, String identification){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(identification);
        alert.setHeaderText(null);
        alert.setContentText(strOutput);
        alert.showAndWait();
    }

    //Отображение таблицы и её обновление
    public void setTableViewTask() throws SQLException, ClassNotFoundException {
        setTaskData();
        colIdTask.setCellValueFactory(new PropertyValueFactory<TaskEntity,Integer>("idTask"));
        colNameTask.setCellValueFactory(new PropertyValueFactory<TaskEntity,String>("nameTask"));
        colDesTask.setCellValueFactory(new PropertyValueFactory<TaskEntity,String>("descriptionTask"));
        colDateTask.setCellValueFactory(new PropertyValueFactory<TaskEntity, Date>("dateTask"));
        colTimeTask.setCellValueFactory(new PropertyValueFactory<TaskEntity, Time>("timeTask"));
        tableViewTask.setItems(tasksData);
    }

    //Запись данных о пользователях
    private void setTaskData() throws SQLException {
        tasksData=null;
        tasksData = FXCollections.observableArrayList();
        List<TaskEntity> tasksList = new ArrayList<>(user.getTaskByIdUser());
        for(int i=0;i<tasksList.size();i++){
            tasksData.add(tasksList.get(i));
        }
    }

    public void setButtonDellTask() throws SQLException, ClassNotFoundException {
        if(!tableViewTask.getSelectionModel().isEmpty()){
            TaskEntity task = tableViewTask.getSelectionModel().getSelectedItem();
            ArrayList arrayListNew = new ArrayList<>(user.getTaskByIdUser());
            arrayListNew.remove(task);
            user.setTaskByIdUser(arrayListNew); //Удилили задачу из листа пользователя
            taskDaoHib.deleteTask(task); //Удилили задачу из бд
            setTableViewTask();
        }else{
            addAlter("Для удаления необходимо выбрать задачу, которую вы хотите удалить","Ошибка");
        }
    }

    public void setButtonAddTask() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL); //Чтобы прошлая форма была не активна

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FormAddTask.fxml"));
        Pane rootFormUser = fxmlLoader.load();

        //Ограничение
        ControllerAddTask controllerAddTask = fxmlLoader.getController();

        controllerAddTask.datePic.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfYear() < LocalDate.now().getDayOfYear());
            }
        });

        StringConverter<LocalDate> converter = new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String string) {
                LocalDate date = super.fromString(string);
                if (date.getDayOfYear() < LocalDate.now().getDayOfYear()) {
                    return date.minusDays(1);
                } else {
                    return date ;
                }
            }
        };
        controllerAddTask.datePic.setConverter(converter);

        controllerAddTask.datePic.setValue(LocalDate.now()); //Установка нынешней даты
        controllerAddTask.textFieldMinuteTask.setText("00");
        controllerAddTask.textFieldHourTask.setText("00");
        controllerAddTask.setCompound(compound); //Передача параметров
        controllerAddTask.setTaskDaoHib(taskDaoHib);
        controllerAddTask.setUserDaoHib(userDaoHib);
        controllerAddTask.setUser(user);

        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(rootFormUser));
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    setTableViewTask();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setButtonChangeTask() throws IOException {
        if(!tableViewTask.getSelectionModel().isEmpty()){
            TaskEntity task = tableViewTask.getSelectionModel().getSelectedItem();
            Stage primaryStage = new Stage();
            primaryStage.initModality(Modality.APPLICATION_MODAL); //Чтобы прошлая форма была не активна

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FormAddTask.fxml"));
            Pane rootFormUser = fxmlLoader.load();

            ControllerAddTask controllerAddTask = fxmlLoader.getController();

            controllerAddTask.setCompound(compound); //Передача параметров
            controllerAddTask.setTaskDaoHib(taskDaoHib);
            controllerAddTask.setUserDaoHib(userDaoHib);
            controllerAddTask.setUser(user);
            controllerAddTask.setTask(task);
            controllerAddTask.textFieldNameTask.setText(task.getNameTask());
            controllerAddTask.textFieldDescriptionTask.setText(task.getDescriptionTask());
            controllerAddTask.textFieldMinuteTask.setText("00");
            controllerAddTask.textFieldHourTask.setText("00");

            //Усановка даты выбранной задачи
            java.util.Date date = task.getDateTask();
            LocalDate localDate = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            controllerAddTask.datePic.setValue(localDate);

            controllerAddTask.buttonAdd.setText("Изменить");

            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(new Scene(rootFormUser));
            primaryStage.setResizable(false);
            primaryStage.show();

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    try {
                        setTableViewTask();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            addAlter("Для редактирование необходимо выбрать задачу, которую вы хотите отредактировать","Ошибка");
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