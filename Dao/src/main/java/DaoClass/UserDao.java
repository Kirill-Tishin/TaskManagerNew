package DaoClass;

import InterfaceDao.UserInterface;
import PojoClass.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao implements UserInterface {
    private DaoDB daoDB;

    public UserDao(DaoDB daoDB) {
        this.daoDB = daoDB;
    }

    @Override
    public void addUser(User user) throws SQLException {

        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into User(id_user,name_user)" + "VALUES (?,?)");
        preparedStatement.setInt(1, user.getIdUser());
        preparedStatement.setString(2, user.getUserName());
        preparedStatement.execute();
        System.out.println("User add");
    }

    @Override
    public void addUser(String nameUser) throws SQLException {
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into User(name_user)" + "VALUES (?)");
        preparedStatement.setString(1, nameUser);
        preparedStatement.execute();
        System.out.println("User add");
    }

    @Override
    public void deleteUser(User user) throws SQLException { //Удаление пользователя
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from User where id_user = ?");
        preparedStatement.setInt(1, user.getIdUser());
        preparedStatement.execute();
        System.out.println("Пользователь удален");
    }

    @Override
    public void deleteUser(int idUser) throws SQLException { //Удаление пользователя
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from User where id_user = ?");
        preparedStatement.setInt(1, idUser);
        preparedStatement.execute();
        System.out.println("Пользователь удален");
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User set name_user = ? where id_user=?");
        preparedStatement.setString(1, user.getUserName());
        preparedStatement.setInt(2, user.getIdUser());
        preparedStatement.execute();
        System.out.println("User update");
    }

    @Override
    public void updateUser(int idUser, String nameUser) throws SQLException { //Изменение имени пользователя
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User set name_user = ? where id_user=?");
        preparedStatement.setString(1, nameUser);
        preparedStatement.setInt(2, idUser);
        preparedStatement.execute();
        System.out.println("User update");
    }

    @Override
    public User getUser(int idUser) throws SQLException {
        Connection connection = daoDB.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name_user FROM User where id_user=?");
        preparedStatement.setInt(1,idUser);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user=null;
        while(resultSet.next()){
            String nameUser = resultSet.getString(1);
            user = new User(idUser,nameUser);
        }
        return user;
    }

    @Override
    public ArrayList<User> getListUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Connection connection = daoDB.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM User");
        while(resultSet.next()){
            int idUser = resultSet.getInt(1);
            String nameUser = resultSet.getString(2);
            users.add(new User(idUser,nameUser));
        }
        return users;
    }
}
