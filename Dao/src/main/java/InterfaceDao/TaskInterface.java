package InterfaceDao;

import PojoClass.Task;

import java.sql.SQLException;

public interface TaskInterface {
    void addTask(Task task) throws SQLException;
}
