package InterfaceDao;

import PojoClass.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserInterface {
    void addUser(User user) throws SQLException;

    void addUser(String nameUser) throws SQLException;

    void deleteUser(User user) throws SQLException;

    void deleteUser(int idUser) throws SQLException;

    void updateUser(User user) throws SQLException;

    void updateUser(int idUser, String nameUser) throws SQLException;

    User getUser(int idUser) throws SQLException;

    ArrayList<User> getListUsers() throws SQLException;
}
