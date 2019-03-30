package TestClassDataBase;

import hibernateDao.TaskDaoHib;
import hibernateDao.UserDaoHib;

import java.util.ArrayList;

public class TestHibernate {
    public static void main(String [] args){
        UserEntity userEntity = new UserEntity();
        userEntity.setNameUser("user1");

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setNameTask("task1");
        taskEntity.setUserByIdUser(userEntity);

        ArrayList<TaskEntity> taskEntities = new ArrayList<>();
        taskEntities.add(taskEntity);
        userEntity.setTasksByIdUser(taskEntities);

        UserDaoHib userDaoHib = new UserDaoHib();
        userDaoHib.addUser(userEntity);

        TaskDaoHib taskDaoHib = new TaskDaoHib();
        taskDaoHib.addTask(taskEntity);


    }
}