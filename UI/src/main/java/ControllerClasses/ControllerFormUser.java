package ControllerClasses;

import Compound.Compound;
import InterfaceDao.TaskInterface;
import InterfaceDao.UserInterface;
import PojoClass.Task;
import PojoClass.User;
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
    private Compound compound;
    private UserInterface userInterface;
    private TaskInterface taskInterface;
    private User user;

    public User getUser(){ return user; }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private ObservableList<Task> tasksData;

    @FXML
    public Alert alert;
    public javafx.scene.control.TableView<Task> tableViewTask;
    public TableColumn<Task,Integer> colIdTask;
    public TableColumn<Task,String> colNameTask;
    public TableColumn<Task,String> colDesTask;
    public TableColumn<Task, Date> colDateTask;
    public TableColumn<Task, java.sql.Time> colTimeTask;
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
        colIdTask.setCellValueFactory(new PropertyValueFactory<Task,Integer>("idTask"));
        colNameTask.setCellValueFactory(new PropertyValueFactory<Task,String>("nameTask"));
        colDesTask.setCellValueFactory(new PropertyValueFactory<Task,String>("descriptionTask"));
        colDateTask.setCellValueFactory(new PropertyValueFactory<Task, Date>("dateTask"));
        colTimeTask.setCellValueFactory(new PropertyValueFactory<Task, Time>("timeTask"));
        tableViewTask.setItems(tasksData);
    }

    /*DateFormat formatDate = new SimpleDateFormat("d.M.yyyy");
            String dateStr  = formatDate.format(calendar.getTime());
            Date date = formatDate.parse(dateStr);*/

    //Запись данных о пользователях
    private void setTaskData() throws SQLException {
        tasksData=null;
        tasksData = FXCollections.observableArrayList();
        List<Task> tasksList = user.getTaskList();
        for(int i=0;i<tasksList.size();i++){
            tasksData.add(tasksList.get(i));
        }
    }

    public void setButtonDellTask() throws SQLException, ClassNotFoundException {
        if(!tableViewTask.getSelectionModel().isEmpty()){
            Task task = tableViewTask.getSelectionModel().getSelectedItem();
            ArrayList arrayListNew = user.getTaskList();
            arrayListNew.remove(task);
            user.setTaskList(arrayListNew); //Удилили задачу из листа пользователя
            taskInterface.deleteTask(task); //Удилили задачу из бд
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
        controllerAddTask.setTaskInterface(taskInterface);
        controllerAddTask.setUserInterface(userInterface);
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
            Task task = tableViewTask.getSelectionModel().getSelectedItem();
            Stage primaryStage = new Stage();
            primaryStage.initModality(Modality.APPLICATION_MODAL); //Чтобы прошлая форма была не активна

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FormAddTask.fxml"));
            Pane rootFormUser = fxmlLoader.load();

            ControllerAddTask controllerAddTask = fxmlLoader.getController();

            controllerAddTask.setCompound(compound); //Передача параметров
            controllerAddTask.setTaskInterface(taskInterface);
            controllerAddTask.setUserInterface(userInterface);
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