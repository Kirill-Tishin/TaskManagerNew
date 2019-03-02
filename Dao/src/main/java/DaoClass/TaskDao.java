package DaoClass;

import InterfaceDao.TaskInterface;
import PojoClass.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskDao implements TaskInterface {
    private DaoDB daoDB;
    private Connection connection;

    public TaskDao(DaoDB daoDB) {
        this.daoDB = daoDB;
    }

    @Override
    public void addTask(Task task) throws SQLException {
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into Task(id_task,name_task,DescriptionTask,dateTask,timeTask,id_user) values (?,?,?,?,?)");
        preparedStatement.setString(1, task.getNameTask());
        preparedStatement.setString(2, task.getDescriptionTask());
        java.sql.Date dateNew = new java.sql.Date(task.getDateTask().getTime());
        preparedStatement.setDate(3, dateNew);
        preparedStatement.setTime(4, task.getTimeTask());
        preparedStatement.setInt(5, task.getIdUser());
        preparedStatement.execute();
        System.out.println("Task add");
    }

    
}
