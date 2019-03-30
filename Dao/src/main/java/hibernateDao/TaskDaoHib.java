package hibernateDao;

import forWorkHibernate.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class TaskDaoHib {
    public TaskEntity getTask(int idTask){
        return HibernateSession.getSession().openSession().get(TaskEntity.class,idTask);
    }

    public void addTask(TaskEntity taskEntity){
        Session session = HibernateSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(taskEntity);
        transaction.commit();
        session.close();
    }

    public void deleteTask(TaskEntity taskEntity){
        Session session = HibernateSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(taskEntity);
        transaction.commit();
        session.close();
    }

    public void updateTask(TaskEntity taskEntity){
        Session session = HibernateSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(taskEntity);
        transaction.commit();
        session.close();
    }

    public ArrayList<TaskEntity> getListTasks(){
        ArrayList<TaskEntity> taskEntities = (ArrayList<TaskEntity>)
                HibernateSession.getSession().openSession().createQuery("from TaskEntity").list();
        return taskEntities;
    }
}
