package lk.ijse.javaeethogakade.dao.custom.impl;

import lk.ijse.javaeethogakade.dao.custom.OrderDAO;
import lk.ijse.javaeethogakade.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Orders entity) throws SQLException, ClassNotFoundException {
        try(Connection connection = DBConnection.getDbConnection().getConnection()){
            String sql = "INSERT INTO Orders VALUES(?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,entity.getOrderID());
            pstm.setDate(2,entity.getOrderDate());
            pstm.setString(3,entity.getCustomerID());
            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
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
    public Orders search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
