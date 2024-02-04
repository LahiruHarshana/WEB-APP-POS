package lk.ijse.javaeethogakade.dao.custom.impl;

import lk.ijse.javaeethogakade.dao.DBConnectionPool;
import lk.ijse.javaeethogakade.dao.custom.CustomerDAO;
import lk.ijse.javaeethogakade.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        try(Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            pstm.setString(1, entity.getCusID());
            pstm.setString(2, entity.getCusName());
            pstm.setString(3, entity.getCusAddress());
            pstm.setDouble(4, entity.getCusSalary());

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("UPDATE customer SET cusName=?, cusAddress=?, cusSalary=? WHERE cusID=?");
            pstm.setString(1, entity.getCusName());
            pstm.setString(2, entity.getCusAddress());
            pstm.setDouble(3, entity.getCusSalary());
            pstm.setString(4, entity.getCusID());

            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
