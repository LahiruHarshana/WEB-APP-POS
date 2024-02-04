package lk.ijse.javaeethogakade.dao.custom.impl;

import lk.ijse.javaeethogakade.dao.DBConnectionPool;
import lk.ijse.javaeethogakade.dao.custom.CustomerDAO;
import lk.ijse.javaeethogakade.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        try(Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM customer");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                allCustomers.add(new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4)));
            }
        }
        return allCustomers;
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
        try(Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("SELECT cusID FROM customer WHERE cusID=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();
            return rst.next();
        }
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        try(Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("SELECT cusID FROM customer ORDER BY cusID DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                String id = rst.getString(1);
                int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
                return String.format("C00-%03d", newCustomerId);
            }else {
                return "C00-001";
            }
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        try(Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM customer WHERE cusID=?");
            pstm.setString(1, id);
            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        try(Connection conn = DBConnectionPool.getConnection()){
            PreparedStatement pstm = conn.prepareStatement("SELECT * FROM customer WHERE cusID=?");
            pstm.setString(1, id);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4));
            }
        }
        return null;
    }
}
