package PojoClass;

import DataBase.DataBase;
import TaskLog.TaskLog;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;

//TODO: use lombok instead of boilerplate code (Не работатет)

//@Data
@Getter
@Setter
public class User {
  //  @Getter
  //  @Setter
    private DataBase dataBase;
  //  @Getter
  //  @Setter
    private int idUser;
  //  @Getter
  //  @Setter
    private String userName;
  //  @Getter
 //   @Setter
    private List<Task> taskList;
 //   @Getter
  //  @Setter
    private TaskLog taskLog;

    public User(){

    }

    public User(int idUser, String userName, DataBase dataBase) throws SQLException {
        this.idUser=idUser;
        this.userName=userName;
        this.dataBase = dataBase;
        this.taskLog = new TaskLog(dataBase,this);
        this.taskList = taskLog.getTaskList();
    }

    public void updateNameUser(String userNameNew) throws SQLException {
        if(dataBase.getEqualsUserName(userNameNew)){
            System.out.println("Такой пользователь уже есть");
        }else{
            dataBase.updateUser(this.idUser,userNameNew);
            this.userName = userNameNew;
        }
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}