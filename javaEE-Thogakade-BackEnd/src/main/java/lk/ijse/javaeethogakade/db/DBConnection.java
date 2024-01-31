package lk.ijse.javaeethogakade.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/testThogaKade";
        String unicode = "?useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url + unicode, "root", "12345678");
    }

    public static DBConnection getDbConnection() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? dbConnection = new DBConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
