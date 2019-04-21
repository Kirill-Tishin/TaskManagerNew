package ControllerClasses;

import Compound.CompoandForHib;
import entityH.UserEntity;
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
import Signal.Signal;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {
    private CompoandForHib compoandForHib = new CompoandForHib();
    private UserEntity userHib;
    private Signal signal;

    public ControllerLogin() throws SQLException {
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public TextField textFieldLogin;
    public Button buttonOK;
    private Alert alert;

    @FXML
    public void setButtonOK() throws SQLException, IOException, ClassNotFoundException {
        if(!textFieldLogin.equals("")){
            if(compoandForHib.getEqualsUserName(textFieldLogin.getText())){
                userHib = compoandForHib.getUserDaoHib().getUser(compoandForHib.getIdUserInName(textFieldLogin.getText()));
                //compound.createListForUserId(user);//Теперь делать не нужно, так как хибирнет присваивает юзеру его записи сам
                signal = new Signal(compoandForHib,userHib);
                signal.startSignal();//Запуск уведомлений сразу, как пользователь авторизовался

                Stage primaryStage = new Stage();
                primaryStage.initModality(Modality.APPLICATION_MODAL); //Чтобы прошлая форма была не активна

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FormMenuUser.fxml"));
                Pane rootFormUser = fxmlLoader.load();

                ControllerFormUser controllerFormUser = fxmlLoader.getController();

                controllerFormUser.setCompound(compoandForHib); //Передача параметров
                controllerFormUser.setUserDaoHib(compoandForHib.getUserDaoHib());
                controllerFormUser.setTaskDaoHib(compoandForHib.getTaskDaoHib());
                controllerFormUser.setUser(userHib);
                controllerFormUser.setTableViewTask();

                textFieldLogin.setText("");

                primaryStage.setTitle("Task Manager");
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