package lk.ijse.javaeethogakade.dao.custom.impl;

import lk.ijse.javaeethogakade.dao.custom.OrderDetailsDAO;
import lk.ijse.javaeethogakade.db.DBConnection;
import lk.ijse.javaeethogakade.entity.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(OrderDetails entity) throws SQLException, ClassNotFoundException {
        try(Connection connection = DBConnection.getDbConnection().getConnection()){
            String sql = "INSERT INTO OrderDetails VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,entity.getOrderID());
            pstm.setString(2,entity.getItemCode());
            pstm.setInt(3,entity.getQuantity());
            pstm.setDouble(4,entity.getUnitPrice());
            return pstm.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
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
    public OrderDetails search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
