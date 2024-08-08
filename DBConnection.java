package oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final String connectionURL = "jdbc:oracle:thin:@localhost:1521/xe";
    private final String username = "WEBMASTER";
    private final String password = "webmaster";

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");
        return DriverManager.getConnection(connectionURL, username, password);
    }
}
