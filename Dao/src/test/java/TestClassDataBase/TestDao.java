package TestClassDataBase;

import DaoClass.DaoDB;
import DaoClass.UserDao;

import java.io.IOException;
import java.sql.SQLException;

public class TestDao {
    public static void main(String [] args) throws SQLException, IOException {
      //  DataBaseOLD dataBase = new DataBaseOLD();
        //Для запуска
      /*  OldVisual.Visualization visualization =  new OldVisual.Visualization(dataBase);
        visualization.mainMenu();*/

      /*  Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(2019, (2 - 1), 16, 22, 00, 00);
        Date dateTask = calendar.getTime();

        dataBase.addTask(2,"q1","q1",dateTask,new Time(dateTask.getTime()));
        dataBase.addTask(2,"q1","q1",dateTask,new Time(dateTask.getTime()));
        dataBase.addTask(2,"q1","q1",dateTask,new Time(dateTask.getTime()));

        User user = new User();*/

        DaoDB daoDB = new DaoDB();
        UserDao userDao = new UserDao(daoDB);
        userDao.addUser("qw");
    }
}