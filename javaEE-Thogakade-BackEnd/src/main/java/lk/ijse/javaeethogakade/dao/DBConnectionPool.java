package lk.ijse.javaeethogakade.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionPool {
    private static DataSource dataSource;

    static {
        try {
            // Initialize JNDI context
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            // Look up the data source by JNDI name
            dataSource = (DataSource) envContext.lookup("jdbc/TestThogaKade");
        } catch (NamingException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized.");
        }
        return dataSource.getConnection();
    }
}
