package ControllerClasses;

import DataBase.DataBase;
import PojoClass.User;
import Signal.Signal;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    private DataBase dataBase = new DataBase();
    private User user;
    private Signal signal;

    public ControllerLogin() throws SQLException {
    }

    public DataBase getDataBase() {
        return dataBase;
    }
    public User getUser(){
        return user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public TextField textFieldLogin;
    public Button buttonOK;
    public Alert alert;

    @FXML
    public void setButtonOK() throws SQLException, IOException, ClassNotFoundException {
        if(!textFieldLogin.equals("")){
            if(dataBase.getEqualsUserName(textFieldLogin.getText())){
                user = new User(dataBase.getIdUserInName(textFieldLogin.getText()),textFieldLogin.getText(),dataBase);
                signal = new Signal(dataBase,user);
                signal.startSignal();//Запуск уведомлений сразу, как пользователь авторизовался

                Stage primaryStage = new Stage();
                primaryStage.initModality(Modality.APPLICATION_MODAL); //Чтобы прошлая форма была не активна

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FormMenuUser.fxml"));
                Pane rootFormUser = fxmlLoader.load();

                ControllerFormUser controllerFormUser = fxmlLoader.getController();

                controllerFormUser.setDataBase(dataBase); //Передача параметров
                controllerFormUser.setUser(user);
                controllerFormUser.setTableViewTask();

                textFieldLogin.setText("");

                primaryStage.setTitle("PojoClass.Task Manager");
                primaryStage.setScene(new Scene(rootFormUser));
                primaryStage.setResizable(false);
                primaryStage.show();

                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent we) {
                        signal.stopSignal(); //Остановка потока уведомлений, когда закрылась форма с пользователем, т.е. пользователь вышел из своего аккаунта
                    }
                });

            }else{
                addAlter("Данного пользователя нет в системе","Ошибка");
                textFieldLogin.setText("");
            }
        }else{
            addAlter("Введите логин пользователя","Ошибка");
        }
    }

    private void addAlter(String strOutput, String identification){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(identification);
        alert.setHeaderText(null);
        alert.setContentText(strOutput);
        alert.showAndWait();
    }
}