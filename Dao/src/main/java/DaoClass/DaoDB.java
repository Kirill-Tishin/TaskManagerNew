package DaoClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoDB {
    private Connection connection;

    public DaoDB() throws SQLException { //Конструктор и соединение с бд
        connectionDB();
    }

    private void connectionDB() { //connection DB
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("JDBC:sqlite:F:\\SQlite\\Windows\\TaskManager.db");
            System.out.println("Connection DataBaseOLD.DataBaseOLD");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void closeDB() throws SQLException { //Close DB
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
