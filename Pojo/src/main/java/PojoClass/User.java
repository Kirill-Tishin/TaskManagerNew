package PojoClass;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

//TODO: use lombok instead of boilerplate code (Не работатет)

@Getter
@Setter
public class User {
    private int idUser;
    private String userName;
  //  private List<Task> taskList;

    public User(int idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

  /*  public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }*/
}