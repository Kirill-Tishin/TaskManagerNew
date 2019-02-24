package DataBase;

import PojoClass.Task;
import PojoClass.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public DataBase() throws SQLException { //Конструктор и соединение с бд
        connectionDB();
    }

    private void connectionDB() { //connection DB
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("JDBC:sqlite:F:\\SQlite\\Windows\\TaskManager.db");
            statement = connection.createStatement(); //Для sql запросов
            System.out.println("Connection DataBase.DataBase");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void createDB() throws SQLException {
        statement.execute("CREATE TABLE PojoClass.User (id_user INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tname_user VARCHAR(50) NOT NULL);");

        statement.execute("CREATE TABLE PojoClass.Task (id_task INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tname_task VARCHAR(50) NOT NULL,\n" +
                "\tDescriptionTask VARCHAR(150),\n" +
                "\tdateTask DATE,\n" +
                "\ttimeTask TIME,\n" +
                "\tid_user INTEGER NOT NULL,\n" +
                "\tFOREIGN KEY (id_user) REFERENCES User(id_user));");

        System.out.println("Table created");
    }

    public void closeDB() throws SQLException { //Close DB
        connection.close();
        statement.close();
        resultSet.close();
        preparedStatement.close();
    }

    public void deleteDB(String nameDB) throws SQLException { //delete DB
        nameDB.toLowerCase();
        if (nameDB.equals("user")) {
            statement.execute("drop TABLE User");
        } else {
            if (nameDB.equals("PojoClass.Task")) {
                statement.execute("drop TABLE Task");
            } else {
                System.out.println("Такой базы дынных не существует");
            }
        }
    }

    public void addUser(String nameUser) throws SQLException { //Добавление пользователя
        if (getEqualsUserName(nameUser)) {
            System.out.println("Пользователь с таким именнем уже существует");
        } else {
            preparedStatement = connection.prepareStatement("INSERT into User(id_user,name_user)" + "VALUES (?,?)");
            preparedStatement.setInt(1, getMaxIdUser() + 1);
            preparedStatement.setString(2, nameUser);
            preparedStatement.execute();
            System.out.println("PojoClass.User add");
        }
    }

    public void updateUser(int idUser, String nameUser) throws SQLException { //Изменение имени пользователя
        preparedStatement = connection.prepareStatement("UPDATE User set name_user = ? where id_user=?");
        preparedStatement.setString(1, nameUser);
        preparedStatement.setInt(2, idUser);
        preparedStatement.execute();
        System.out.println("PojoClass.User update");
    }

    public void deleteUser(int idUser) throws SQLException { //Удаление пользователя
        preparedStatement = connection.prepareStatement("DELETE from User where id_user = ?");
        preparedStatement.setInt(1, idUser);
        preparedStatement.execute();
        System.out.println("Пользователь удален");
    }

    public User getUserInDB(int idUser, DataBase dataBase) throws SQLException { //Получение PojoClass.User
        User user = null;
        preparedStatement = connection.prepareStatement("SELECT name_user FROM User where id_user= ? ");
        preparedStatement.setInt(1, idUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            user = new User(idUser, resultSet.getString("name_user"), dataBase);
        }
        return user;
    }

    public void printTableUser() throws SQLException { //Вывод таблицы PojoClass.User
        resultSet = statement.executeQuery("SELECT * FROM User");
        System.out.println("    " + "idUser" + "  " + "nameUser");
        while (resultSet.next()) {
            int idUser = resultSet.getInt("id_user");
            String nameUser = resultSet.getString("name_user");
            System.out.println("    " + idUser + "  " + nameUser);
        }
        System.out.println("БД выведена");
    }

    //Добавление задачи
    public void addTask(int idUser, String nameTask, String descriptionTask, java.util.Date date, Time time) throws SQLException { //data and time добавить
        java.sql.Date dateNew = new java.sql.Date(date.getTime());
        preparedStatement = connection.prepareStatement("INSERT into Task(name_task,DescriptionTask,dateTask,timeTask,id_user) values (?,?,?,?,?)");//id_task,
        //  preparedStatement.setInt(1, getMaxIdTask()+1);
        preparedStatement.setString(1, nameTask);
        preparedStatement.setString(2, descriptionTask);
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, time);
        preparedStatement.setInt(5, idUser);
        preparedStatement.execute();
        System.out.println("PojoClass.Task add");
    }

    //Обновление задачи
    public void updateTask(int idUser, int idTask, String nameTask, String descriptionTask, java.util.Date date, Time time) throws SQLException { //data and time
        java.sql.Date dateNew = new java.sql.Date(date.getTime());
        preparedStatement = connection.prepareStatement("UPDATE Task set name_task = ?,DescriptionTask=?,dateTask=?,timeTask=? where id_user=? and id_task=?");
        preparedStatement.setString(1, nameTask);
        preparedStatement.setString(2, descriptionTask);
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, time);
        preparedStatement.setInt(5, idUser);
        preparedStatement.setInt(6, idTask);
        preparedStatement.execute();
        System.out.println("PojoClass.Task update");
    }

    //Удаление задачи
    public void deleteTask(int idTask) throws SQLException {
        preparedStatement = connection.prepareStatement("DELETE from Task where id_task = ?");
        preparedStatement.setInt(1, idTask);
        preparedStatement.execute(); //Выполнение запроса
        System.out.println("Задача удалена");
    }

    //delete all task in user
    public void deleteAllTaskUser(int idUser) throws SQLException {
        preparedStatement = connection.prepareStatement("delete from Task where id_user=?");
        preparedStatement.setInt(1, idUser);
        preparedStatement.execute(); //Выполнение запроса
        System.out.println("Все задачи пользователя " + idUser + " удалены");
    }

    //Вывод таблицы task
    public void printTableTask() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM Task");
        System.out.printf("idTask\tnameTask\tDescriptionTask\tData\tTime\tidUsr\n");
        while (resultSet.next()) {
            int idTask = resultSet.getInt("id_task");
            String nameTask = resultSet.getString("name_task");
            String description = resultSet.getString("DescriptionTask");
            Data data = (Data) resultSet.getDate("dateTask");
            Time time = resultSet.getTime("timeTask");
            int idUser = resultSet.getInt("id_user");
            System.out.printf(idTask + "\t" + nameTask + "\t" + description + "\t" + data + "\t" + time + "\t" + idUser + "\n");
        }
        System.out.println("БД выведена");
    }

    //Вывод записей пользователя
    public void printTasksUser(int idUser) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * from Task where id_user=?");
        preparedStatement.setInt(1, idUser);
        resultSet = preparedStatement.executeQuery();
        System.out.printf("idTask\tnameTask\tDescriptionTask\tData\tTime\n");
        while (resultSet.next()) {
            int idTask = resultSet.getInt("id_task");
            String nameTask = resultSet.getString("name_task");
            String description = resultSet.getString("DescriptionTask");
            Date date = resultSet.getDate("dateTask");
            Time time = resultSet.getTime("timeTask");
            System.out.printf(idTask + "\t" + nameTask + "\t" + description + "\t" + date + "\t" + time + "\n");
        }
    }

    //Получение листа записей
    public List<Task> getListTasks(DataBase dataBase) throws SQLException {
        ArrayList<Task> listTask = new ArrayList<Task>();
        resultSet = statement.executeQuery("select * from Task");
        while (resultSet.next()) {
            int idTask = resultSet.getInt("id_task");
            int idUser = resultSet.getInt("id_user");
            String nameTask = resultSet.getString("name_task");
            String description = resultSet.getString("DescriptionTask");
            java.util.Date date = resultSet.getDate("dateTask");
            Time time = resultSet.getTime("timeTask");
            listTask.add(new Task(idTask, idUser, nameTask, description, date, time, dataBase));
        }
        return listTask;
    }

    //Получение записей конкретного пользователя
    public List<Task> getTasksUser(int idUser, DataBase dataBase) throws SQLException {
        ArrayList<Task> listTask = new ArrayList<Task>();
        int[] masIdTasks = getMassIdTasks(idUser);
        for (int i = 0; i < masIdTasks.length; i++) {
            preparedStatement = connection.prepareStatement("SELECT * from Task where id_user=? and id_task=?");
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, masIdTasks[i]);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idTask = resultSet.getInt("id_task");
                String nameTask = resultSet.getString("name_task");
                String description = resultSet.getString("DescriptionTask");
                java.util.Date date = resultSet.getDate("dateTask");
                Time time = resultSet.getTime("timeTask");
                Task task = new Task(idTask, idUser, nameTask, description, date, time, dataBase);
                listTask.add(task);
            }
        }
        return listTask;
    }

    public int[] getMassIdTasks(int idUser) throws SQLException {
        int[] masIdTasks = new int[getCountTaskUser(idUser)];
        int i = 0;
        preparedStatement = connection.prepareStatement("select id_task from Task where id_user=?");
        preparedStatement.setInt(1, idUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            masIdTasks[i] = resultSet.getInt("id_task");
            i++;
        }
        return masIdTasks;
    }

    public int getCountTaskUser(int idUser) throws SQLException {
        int count = 0;
        preparedStatement = connection.prepareStatement("select id_task from Task where id_user=?");
        preparedStatement.setInt(1, idUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    //Получение одной записи
    public Task getTask(int idTask, DataBase dataBase) throws SQLException {
        Task task = null;
        preparedStatement = connection.prepareStatement("SELECT * from Task where id_task=?");
        preparedStatement.setInt(1, idTask);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int idUser = resultSet.getInt("id_user");
            String nameTask = resultSet.getString("name_task");
            String description = resultSet.getString("DescriptionTask");
            java.util.Date date = resultSet.getDate("dateTask");
            Time time = resultSet.getTime("timeTask");
            task = new Task(idTask, idUser, nameTask, description, date, time, dataBase);
        }
        return task;
    }

    //Получение задачи у пользователя
    public Task getTaskUser(int idUser, int idTask, DataBase dataBase) throws SQLException {
        Task task = null;
        preparedStatement = connection.prepareStatement("SELECT * from Task where id_task=? and id_user=?");
        preparedStatement.setInt(1, idTask);
        preparedStatement.setInt(2, idUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String nameTask = resultSet.getString("name_task");
            String description = resultSet.getString("DescriptionTask");
            java.util.Date date = resultSet.getDate("dateTask");
            Time time = resultSet.getTime("timeTask");
            task = new Task(idTask, idUser, nameTask, description, date, time, dataBase);
        }
        return task;
    }

    //проверка, есть ли пользователь в системе
    public Boolean getEqualsUserId(int idUser) throws SQLException {
        boolean flag = false;
        preparedStatement = connection.prepareStatement("select * from User where id_user=?");
        preparedStatement.setInt(1, idUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            flag = true;
        }
        return flag;
    }

    //Существует ли такая задача
    public Boolean getEqualdTask(int idTask) throws SQLException {
        boolean flag = false;
        preparedStatement = connection.prepareStatement("select  * From Task where id_task=?");
        preparedStatement.setInt(1, idTask);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            flag = true;
        }
        return flag;
    }

    //Проверка, есть ли у пользователя такакя задача
    public Boolean getEqualdTaskUser(int idTask, int idUser) throws SQLException {
        boolean flag = false;
        preparedStatement = connection.prepareStatement("select  * From Task where id_task=? and id_user=?");
        preparedStatement.setInt(1, idTask);
        preparedStatement.setInt(2, idUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            flag = true;
        }
        return flag;
    }

    //Подсчет кол-ва задач
    public int countTask() throws SQLException {
        int countTask = 0;
        resultSet = statement.executeQuery("select id_task from Task");
        while (resultSet.next()) {
            countTask++;
        }
        return countTask;
    }

    //Подсчет кол-ва пользователей
    public int countUser() throws SQLException {
        int countUser = 0;
        resultSet = statement.executeQuery("select id_user from User");
        while (resultSet.next()) {
            countUser++;
        }
        return countUser;
    }

    public boolean getEqualsUserName(String nameUser) throws SQLException {
        boolean flag = false;
        preparedStatement = connection.prepareStatement("select * from User where name_user=?");
        preparedStatement.setString(1, nameUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            flag = true;
        }
        return flag;
    }

    public int getIdUserInName(String nameUser) throws SQLException {
        int idUser = 0;
        preparedStatement = connection.prepareStatement("select * from User where name_user=?");
        preparedStatement.setString(1, nameUser);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            idUser = resultSet.getInt("id_user");
        }
        return idUser;
    }

    public int getMaxIdUser() throws SQLException {
        int maxId = 0;
        resultSet = statement.executeQuery("SELECT id_user from User");
        while (resultSet.next()) {
            int idUser = resultSet.getInt("id_user");
            if (maxId < idUser) {
                maxId = idUser;
            }
        }
        return maxId;
    }

    public int getMaxIdTask() throws SQLException {
        int maxId = 0;
        resultSet = statement.executeQuery("SELECT id_task from Task");
        while (resultSet.next()) {
            int idTask = resultSet.getInt("id_task");
            if (maxId < idTask) {
                maxId = idTask;
            }
        }
        return maxId;
    }
}