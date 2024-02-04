package lk.ijse.javaeethogakade.dao.custom.impl;

import lk.ijse.javaeethogakade.dao.DBConnectionPool;
import lk.ijse.javaeethogakade.dao.custom.ItemDAO;
import lk.ijse.javaeethogakade.entity.Items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Items> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Items entity) throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Items VALUES (?, ?, ?, ?)");
            preparedStatement.setObject(1, entity.getItemCode());
            preparedStatement.setObject(2, entity.getItemName());
            preparedStatement.setObject(3, entity.getItemPrice());
            preparedStatement.setObject(4, entity.getItemQuantity());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Items entity) throws SQLException, ClassNotFoundException {
        try(Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Items SET ItemName=?, ItemPrice=?, ItemQty=? WHERE itemCode=?");
            preparedStatement.setObject(1, entity.getItemName());
            preparedStatement.setObject(2, entity.getItemPrice());
            preparedStatement.setObject(3, entity.getItemQuantity());
            preparedStatement.setObject(4, entity.getItemCode());
            return preparedStatement.executeUpdate() > 0;
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
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Items WHERE ItemCode=?");
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public Items search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
