package lk.ijse.javaeethogakade.dao;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
public class DBConnectionPool {
    private static DataSource dataSource;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/TestThogaKade");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("12345678");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setMaxTotal(20);
        basicDataSource.setMaxIdle(2);
        basicDataSource.setMaxWaitMillis(-1);
        dataSource = basicDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
