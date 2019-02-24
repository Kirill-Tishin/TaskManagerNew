package TaskLog;

import DataBase.DataBase;
import PojoClass.Task;
import PojoClass.User;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class TaskLog {
    private List<Task> taskList;
    private User user;
    private DataBase dataBase;

    public TaskLog(DataBase dataBase, User user) throws SQLException {
        this.dataBase = dataBase;
        this.taskList = dataBase.getTasksUser(user.getIdUser(),dataBase); //Задачи одного пользователя одного
        this.user=user;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public Task getTaskdUser(int idTask) throws SQLException {
        Task task = null;
        if (dataBase.getEqualdTaskUser(idTask, user.getIdUser())) {
            task = dataBase.getTaskUser(this.user.getIdUser(), idTask, dataBase);
        }
        return task;
    }

    public void setTaskUser(Task task) throws SQLException {
        dataBase.updateTask(user.getIdUser(), task.getIdTask(), task.getNameTask(), task.getDescriptionTask(), task.getDateTask(), task.getTimeTask());
        this.taskList = dataBase.getListTasks(dataBase);
    }

    public void addTaskUser(String nameTask, String descriptionTask, Date date, Time time) throws SQLException {
        int idTask = dataBase.countTask() + 1;
        dataBase.addTask(user.getIdUser(), nameTask, descriptionTask, date, time);
        this.taskList = dataBase.getListTasks(dataBase); //Обновление листа задач в этом классе
    }

    public void deleteTask(int idTask) throws SQLException {
        dataBase.deleteTask(idTask);
        this.taskList = dataBase.getListTasks(dataBase);
    }

    public void daleteAllTaskUser() throws SQLException {
        dataBase.deleteAllTaskUser(user.getIdUser());

        //TODO: do not get taskList from DB every time. Add syncronization button instead.
        this.taskList = dataBase.getListTasks(dataBase);
    }
}