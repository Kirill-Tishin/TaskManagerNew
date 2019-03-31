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
        return HibernateSession.getSession().openSession().get(UserEntity.class,idUser);
    }

    public void addUser(UserEntity userEntity){
        Session session = HibernateSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
        session.close();
    }

    public void deleteUser(UserEntity userEntity){
        Session session = HibernateSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(userEntity);
        transaction.commit();
        session.close();
    }

    public void updateUser(UserEntity userEntity){
        Session session = HibernateSession.getSession().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(userEntity);
        transaction.commit();
        session.close();
    }

    public ArrayList<TaskEntity> getListTasks(int idUser){
        ArrayList<TaskEntity> listTasks = new ArrayList<TaskEntity>();
        Session session = HibernateSession.getSession().openSession();
        Query query =  session.createQuery("from TaskEntity where idUser=:idUser");
        query.setParameter("idUser",idUser);
        listTasks = (ArrayList<TaskEntity>) query.list();
        return listTasks;
    }

    public ArrayList<UserEntity> getLiastUsers(){
        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>)
                HibernateSession.getSession().openSession().createQuery("from UserEntity").list();
        return userEntities;
    }
}
