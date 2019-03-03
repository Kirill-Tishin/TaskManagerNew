package DaoClass;

import InterfaceDao.TaskInterface;
import PojoClass.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao implements TaskInterface {
    private DaoDB daoDB;

    public TaskDao(DaoDB daoDB) {
        this.daoDB = daoDB;
    }

    @Override
    public void addTask(Task task) throws SQLException {
        java.sql.Date dateNew = new java.sql.Date(task.getDateTask().getTime());
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into Task(id_task,name_task,DescriptionTask,dateTask,timeTask,id_user) values (?,?,?,?,?)");
        preparedStatement.setString(1, task.getNameTask());
        preparedStatement.setString(2, task.getDescriptionTask());
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, task.getTimeTask());
        preparedStatement.setInt(5, task.getIdUser());
        preparedStatement.execute();
        System.out.println("Task add");
    }

    @Override
    public void addTask(int idUser, String nameTask, String descriptionTask, java.util.Date date, Time time) throws SQLException {
        java.sql.Date dateNew = new java.sql.Date(date.getTime());
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into Task(name_task,DescriptionTask,dateTask,timeTask,id_user) values (?,?,?,?,?)");
        preparedStatement.setString(1, nameTask);
        preparedStatement.setString(2, descriptionTask);
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, time);
        preparedStatement.setInt(5, idUser);
        preparedStatement.execute();
        System.out.println("Task add");
    }

    @Override
    public void deleteTask(int idTask) throws SQLException {
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from Task where id_task = ?");
        preparedStatement.setInt(1, idTask);
        preparedStatement.execute(); //Выполнение запроса
        System.out.println("Задача удалена");
    }

    @Override
    public void deleteTask(Task task) throws SQLException {
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from Task where id_task = ?");
        preparedStatement.setInt(1, task.getIdTask());
        preparedStatement.execute(); //Выполнение запроса
        System.out.println("Задача удалена");
    }

    @Override
    public void updateTask(int idUser, int idTask, String nameTask, String descriptionTask, java.util.Date date, Time time) throws SQLException {
        java.sql.Date dateNew = new java.sql.Date(date.getTime());
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Task set name_task = ?,DescriptionTask=?,dateTask=?,timeTask=? where id_user=? and id_task=?");
        preparedStatement.setString(1, nameTask);
        preparedStatement.setString(2, descriptionTask);
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, time);
        preparedStatement.setInt(5, idUser);
        preparedStatement.setInt(6, idTask);
        preparedStatement.execute();
        System.out.println("Task update");
    }

    @Override
    public void updateTask(Task task) throws SQLException {
        java.sql.Date dateNew = new java.sql.Date(task.getDateTask().getTime());
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Task set name_task = ?,DescriptionTask=?,dateTask=?,timeTask=? where id_user=? and id_task=?");
        preparedStatement.setString(1, task.getNameTask());
        preparedStatement.setString(2, task.getDescriptionTask());
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, task.getTimeTask());
        preparedStatement.setInt(5, task.getIdUser());
        preparedStatement.setInt(6, task.getIdTask());
        preparedStatement.execute();
        System.out.println("Task update");
    }

    //Получение записей конкретного пользователя
    @Override
    public List<Task> getTasksUser(int idUser) throws SQLException {
        ArrayList<Task> listTask = new ArrayList<Task>();
        int[] masIdTasks = getMassIdTasks(idUser);
        for (int i = 0; i < masIdTasks.length; i++) {
            Connection connection = daoDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from Task where id_user=? and id_task=?");
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, masIdTasks[i]);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idTask = resultSet.getInt(1);
                String nameTask = resultSet.getString(2);
                String description = resultSet.getString(3);
                java.util.Date date = resultSet.getDate(4);
                Time time = resultSet.getTime(5);
                Task task = new Task(idTask, idUser, nameTask, description, date, time);
                listTask.add(task);
            }
        }
        return listTask;
    }

    private int[] getMassIdTasks(int idUser) throws SQLException {
        int[] masIdTasks = new int[getCountTaskUser(idUser)];
        int i = 0;
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select id_task from Task where id_user=?");
        preparedStatement.setInt(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            masIdTasks[i] = resultSet.getInt("id_task");
            i++;
        }
        return masIdTasks;
    }

    private int getCountTaskUser(int idUser) throws SQLException {
        int count = 0;
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select id_task from Task where id_user=?");
        preparedStatement.setInt(1, idUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    @Override
    public Task getTask(int idTask) throws SQLException {
        Task task = null;
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from Task where id_task=?");
        preparedStatement.setInt(1, idTask);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String nameTask = resultSet.getString(2);
            String description = resultSet.getString(3);
            java.util.Date date = resultSet.getDate(4);
            Time time = resultSet.getTime(5);
            int idUser = resultSet.getInt(6);
            task = new Task(idTask, idUser, nameTask, description, date, time);
        }
        return task;
    }

    @Override
    public ArrayList<Task> getListTasks() throws SQLException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Connection connection = daoDB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Task");
        while(resultSet.next()){
            int idTask = resultSet.getInt(1);
            String nameTask = resultSet.getString(2);
            String description = resultSet.getString(3);
            java.util.Date date = resultSet.getDate(4);
            Time time = resultSet.getTime(5);
            int idUser = resultSet.getInt(6);
            tasks.add(new Task(idTask,idUser,nameTask,description,date,time));
        }
        return tasks;
    }
}