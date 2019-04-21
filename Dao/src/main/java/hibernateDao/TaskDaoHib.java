package hibernateDao;

import entityH.TaskEntity;
import forWorkHibernate.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class TaskDaoHib {
    public TaskEntity getTask(int idTask){
//        Session session = HibernateSession.getSession().openSession();
//        TaskEntity taskEntity = session.get(TaskEntity.class,idTask);
//        session.close();
//        return taskEntity;

        return HibernateSession.getSession().openSession().get(TaskEntity.class,idTask);
    }

    public void addTask(TaskEntity taskEntity){
        Session session = HibernateSession.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(taskEntity);
        transaction.commit();
       // session.close();
    }

    public void deleteTask(TaskEntity taskEntity){
        Session session = HibernateSession.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(taskEntity);
        transaction.commit();
     //   session.close();
    }

//    public void deleteTask(TaskEntity taskEntity){
//        Session session = HibernateSession.getSession().openSession();
//        Query query = session.createQuery("delete TaskEntity where idTask=:idTask");
//        query.setParameter("idTask",taskEntity.getIdTask());
//        query.executeUpdate();
//        session.close();
//    }

    public void updateTask(TaskEntity taskEntity){
        Session session = HibernateSession.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(taskEntity);
        transaction.commit();
      //  session.close();
    }

    public ArrayList<TaskEntity> getListTasks(){
       /* Session session = HibernateSession.getSession().openSession();
        ArrayList<TaskEntity> taskEntities = (ArrayList<TaskEntity>)session.createQuery("from TaskEntity").list();
        session.close();
        return taskEntities;*/

        ArrayList<TaskEntity> taskEntities = (ArrayList<TaskEntity>)HibernateSession.getSession().openSession().createQuery("from TaskEntity").list();
        return taskEntities;

       /* Session session = HibernateSession.getSession().getCurrentSession();
        ArrayList<TaskEntity> taskEntities = (ArrayList<TaskEntity>)session.createQuery("from TaskEntity").list();
        session.close();
        return taskEntities;*/
    }
}
