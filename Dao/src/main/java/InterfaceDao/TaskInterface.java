package InterfaceDao;

import PojoClass.Task;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public interface TaskInterface {
    void addTask(Task task) throws SQLException;

    void addTask(int idUser, String nameTask, String descriptionTask, java.util.Date date, Time time) throws SQLException;

    void deleteTask(int idTask) throws SQLException;

    void deleteTask(Task task) throws SQLException;

    void updateTask(int idUser, int idTask, String nameTask, String descriptionTask, java.util.Date date, Time time) throws SQLException;

    void updateTask(Task task) throws SQLException;

    //Получение записей конкретного пользователя
    List<Task> getTasksUser(int idUser) throws SQLException;

    Task getTask(int idTask) throws SQLException;

    ArrayList<Task> getListTasks() throws SQLException;
}
