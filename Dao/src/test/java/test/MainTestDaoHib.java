package test;

import DaoClass.UserDao;
import InterfaceDao.TaskInterface;
import entityH.TaskEntity;
import entityH.UserEntity;
import hibernateDao.TaskDaoHib;
import hibernateDao.UserDaoHib;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MainTestDaoHib {
    public static void main(String[] args){
        UserDaoHib userDaoHib = new UserDaoHib();
        TaskDaoHib taskDaoHib = new TaskDaoHib();

       /* UserEntity userEntity = new UserEntity();
        userEntity.setNameUser("UserTestHib");

        userDaoHib.addUser(userEntity);

        TaskEntity taskEntity1 = new TaskEntity();
        taskEntity1.setNameTask("taskEntity1");
        taskEntity1.setDescriptionTask("21");
        Date date = new Date();
        taskEntity1.setDateTask(date);
        taskEntity1.setTimeTask(new Time(date.getTime()));
        taskEntity1.setUserByIdUser(userEntity);
        taskEntity1.setIdUser(userEntity.getIdUser());

        TaskEntity taskEntity2 = new TaskEntity();
        taskEntity2.setNameTask("taskEntity2");
        taskEntity2.setDescriptionTask("21");
        taskEntity2.setDateTask(date);
        taskEntity2.setTimeTask(new Time(date.getTime()));
        taskEntity2.setUserByIdUser(userEntity);
        taskEntity2.setIdUser(userEntity.getIdUser());

        Collection<TaskEntity> taskEntities = new ArrayList<TaskEntity>();
        taskEntities.add(taskEntity1);
        taskEntities.add(taskEntity2);

        userEntity.setTaskByIdUser(taskEntities);

        taskDaoHib.addTask(taskEntity1);
        taskDaoHib.addTask(taskEntity2);*/
    }
}
