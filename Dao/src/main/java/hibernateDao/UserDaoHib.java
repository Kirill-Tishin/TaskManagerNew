package hibernateDao;

import entityH.TaskEntity;
import entityH.UserEntity;
import forWorkHibernate.HibernateSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UserDaoHib {
    public UserEntity getUser(int idUser){
//        Session session = HibernateSession.getSession().openSession();
//        UserEntity userEntity = session.get(UserEntity.class,idUser);
//        session.close();
//        return userEntity;

        return HibernateSession.getSession().openSession().get(UserEntity.class,idUser);
    }

    public void addUser(UserEntity userEntity){
        Session session = HibernateSession.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
      //  session.close();
    }

    public void deleteUser(UserEntity userEntity){
        Session session = HibernateSession.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(userEntity);
        transaction.commit();
      //  session.close();
    }

    public void updateUser(UserEntity userEntity){
        Session session = HibernateSession.getSession().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(userEntity);
        transaction.commit();
     //   session.close();
    }

    public ArrayList<TaskEntity> getListTasks(int idUser){
        ArrayList<TaskEntity> listTasks = new ArrayList<TaskEntity>();
        Session session = HibernateSession.getSession().getCurrentSession();
        Query query =  session.createQuery("from TaskEntity where idUser=:idUser");
        query.setParameter("idUser",idUser);
        listTasks = (ArrayList<TaskEntity>) query.list();
       // session.close();
        return listTasks;
    }

    public ArrayList<UserEntity> getListUsers(){
        /*Session session = HibernateSession.getSession().openSession();
        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>)session.createQuery("from UserEntity ").list();
        session.close();
        return userEntities;*/

        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>)HibernateSession.getSession().openSession().createQuery("from UserEntity ").list();
        return userEntities;

      /*  Session session = HibernateSession.getSession().getCurrentSession();
        session.getTransaction();
        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>)session.createQuery("from UserEntity ").list();
        return userEntities;*/
    }
}
