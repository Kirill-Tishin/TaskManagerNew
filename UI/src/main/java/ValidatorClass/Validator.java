package ValidatorClass;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Validator implements Initializable {

    public Validator() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private Alert alert;

    @FXML
    private void addAlter(String strOutput, String identification){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(identification);
        alert.setHeaderText(null);
        alert.setContentText(strOutput);
        alert.showAndWait();
    }

    /***
     *Сравнение времени. Устарело или нет.
     * @param dateNow Нынешнее время
     * @param dateTask Время задачи
     * @return Возврашение флага true - если время еще не прошло
     */
    public boolean checkOldTime(Date dateNow, Date dateTask){
        boolean flag = false;
        if (dateNow.getTime() <= dateTask.getTime()) {
            flag = true;
        }else{
            addAlter("Дата уже устарела", "Ошибка");
        }
        return flag;
    }

    //Проверка корректности времени
    public boolean checkTimeMinAndHour(int hour, int minute){
        boolean flag = false;
        if ((hour >= 0) && (hour < 24) && (minute >= 0) && (minute < 60)) {
            flag = true;
        } else {
            addAlter("Время должно быть корректным, т.е. часы от 0 до 23, а минуты от 0 до 59!", "Ошибка");
        }
        return flag;
    }

    public boolean checkNameTask(String nameTask){
        boolean flag = false;
        if (!nameTask.equals("")) {
            flag = true;
        }else{
            addAlter("Введите название задачи", "Ошибка");
        }
        return flag;
    }

    //Проверка, нет ли букв в строке
    public boolean checkNum(String str) {
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
}
