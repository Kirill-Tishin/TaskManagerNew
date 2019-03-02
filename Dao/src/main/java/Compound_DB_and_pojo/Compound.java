package Compound_DB_and_pojo;

import DataBase.DataBaseOLD;

import java.sql.SQLException;

//Класс для взаимодействия с классом бд, а сам класс бд переделать в простой дао класс
public class Compound {
    private DataBaseOLD dataBaseOLD;

    public Compound() throws SQLException {
        dataBaseOLD = new DataBaseOLD();
    }

    //Добавление листа записей для User
    //При старте приложения
/*    public void createListForUsers() throws SQLException {
        ArrayList<User> userArrayList = dataBaseOLD.getListUser();
        ArrayList<Task> taskArrayList = dataBaseOLD.getListTasks();
        for(int i=0;i<userArrayList.size();i++){
            ArrayList<Task> taskArrayListForUser = new ArrayList<Task>();
            for(int j=0;j<taskArrayList.size();j++){
                if(userArrayList.get(i).getIdUser()==taskArrayList.get(j).getIdUser()){
                    taskArrayListForUser.add(taskArrayList.get(j));
                }
            }
            userArrayList.get(i).setTaskList(taskArrayListForUser);
        }
    }*/

    //Получение листа записей для конкретного пользователя
    //При изменении 5-ти записей, уточнение данных у пользователя, а так,
    //у пользователя и в бд одно и тоже
  /*  public void createListForUserId(int idUser) throws SQLException {
        ArrayList<User> userArrayList = dataBaseOLD.getListUser();
        ArrayList<Task> taskArrayList = dataBaseOLD.getListTasks();
        for(int i=0;i<userArrayList.size();i++){
            if(userArrayList.get(i).getIdUser()==idUser){
                ArrayList<Task> taskArrayListForUser = new ArrayList<Task>();
                for(int j=0;j<taskArrayList.size();j++){
                    if(userArrayList.get(i).getIdUser()==taskArrayList.get(j).getIdUser()){
                        taskArrayListForUser.add(taskArrayList.get(j));
                    }
                }
                userArrayList.get(i).setTaskList(taskArrayListForUser);
            }
        }
    }*/
}
