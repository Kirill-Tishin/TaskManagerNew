package PojoClass;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

//TODO: use lombok instead of boilerplate code *******************************

@Getter
@Setter
public class User {
    private int idUser;
    private String userName;
    private ArrayList<Task> taskList;

    public User(int idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
    }
}