package Compound_DB_and_pojo;

import DaoClass.DaoDB;
import DaoClass.TaskDao;
import DaoClass.UserDao;
import InterfaceDao.TaskInterface;
import InterfaceDao.UserInterface;
import PojoClass.User;
import PojoClass.Task;

import java.sql.SQLException;
import java.util.ArrayList;

//Класс для взаимодействия с классом бд, а сам класс бд переделать в простой дао класс
public class Compound {
    private DaoDB daoDB;
    private UserInterface userInterface;
    private TaskInterface taskInterface;

    public Compound() throws SQLException {
        daoDB = new DaoDB();
        userInterface = new UserDao(daoDB);
        taskInterface = new TaskDao(daoDB);
    }

    //Добавление листа записей для User
    //При старте приложения
    public void createListForUsers() throws SQLException {
        ArrayList<User> userArrayList = userInterface.getListUsers();
        ArrayList<Task> taskArrayList = taskInterface.getListTasks();
        for (int i = 0; i < userArrayList.size(); i++) {
            ArrayList<Task> taskArrayListForUser = new ArrayList<Task>();
            for (int j = 0; j < taskArrayList.size(); j++) {
                if (userArrayList.get(i).getIdUser() == taskArrayList.get(j).getIdUser()) {
                    taskArrayListForUser.add(taskArrayList.get(j));
                }
            }
            userArrayList.get(i).setTaskList(taskArrayListForUser);
        }
    }

    //Получение листа записей для конкретного пользователя
    //При изменении 5-ти записей, уточнение данных у пользователя, а так,
    //у пользователя и в бд одно и тоже
    public void createListForUserId(User user) throws SQLException {
        ArrayList<Task> taskArrayList = taskInterface.getListTasks();
        ArrayList<Task> taskArrayListForUser = new ArrayList<Task>();
        for (int j = 0; j < taskArrayList.size(); j++) {
            if (user.getIdUser() == taskArrayList.get(j).getIdUser()) {
                taskArrayListForUser.add(taskArrayList.get(j));
            }
        }
        user.setTaskList(taskArrayListForUser);
    }

    public boolean getEqualsUserName(String nameUser) throws SQLException {
        boolean flag = false;
        for (int i = 0; i < userInterface.getListUsers().size(); i++) {
            User user = userInterface.getListUsers().get(i);
            if (user.getUserName().equals(nameUser)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int getIdUserInName(String nameUser) throws SQLException {
        int idUser = 0;
        for (int i = 0; i < userInterface.getListUsers().size(); i++) {
            User user = userInterface.getListUsers().get(i);
            if (user.getUserName().equals(nameUser)) {
                idUser = user.getIdUser();
                break;
            }
        }
        return idUser;
    }

    public int getMaxIdTask() throws SQLException {
        int maxId = 0;
        for (int i = 0; i < taskInterface.getListTasks().size(); i++) {
            if (maxId < taskInterface.getListTasks().get(i).getIdTask()) {
                maxId = taskInterface.getListTasks().get(i).getIdTask();
            }
        }
        return maxId;
    }

    public TaskInterface getTaskInterface() {
        return taskInterface;
    }

    public void setTaskInterface(TaskInterface taskInterface) {
        this.taskInterface = taskInterface;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public DaoDB getDaoDB() {
        return daoDB;
    }

    public void setDaoDB(DaoDB daoDB) {
        this.daoDB = daoDB;
    }
}
