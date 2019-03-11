package PojoClass;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

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