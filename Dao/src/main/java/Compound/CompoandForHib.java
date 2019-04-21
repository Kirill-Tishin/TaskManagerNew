package Compound;

import entityH.UserEntity;
import hibernateDao.TaskDaoHib;
import hibernateDao.UserDaoHib;

public class CompoandForHib {
    private TaskDaoHib taskDaoHib = new TaskDaoHib();
    private UserDaoHib userDaoHib = new UserDaoHib();

    public TaskDaoHib getTaskDaoHib() {
        return taskDaoHib;
    }

    public void setTaskDaoHib(TaskDaoHib taskDaoHib) {
        this.taskDaoHib = taskDaoHib;
    }

    public UserDaoHib getUserDaoHib() {
        return userDaoHib;
    }

    public void setUserDaoHib(UserDaoHib userDaoHib) {
        this.userDaoHib = userDaoHib;
    }


    public int getIdUserInName(String nameUser) {
        int idUser = 0;
        for (int i = 0; i < userDaoHib.getListUsers().size(); i++) {
            UserEntity user = userDaoHib.getListUsers().get(i);
            if (user.getNameUser().equals(nameUser)) {
                idUser = user.getIdUser();
                break;
            }
        }
        return idUser;
    }

    public boolean getEqualsUserName(String nameUser) {
        boolean flag = false;
        for (int i = 0; i < userDaoHib.getListUsers().size(); i++) {
            UserEntity user = userDaoHib.getListUsers().get(i);
            if (user.getNameUser().equals(nameUser)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}