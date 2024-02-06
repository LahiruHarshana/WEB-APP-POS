package lk.ijse.javaeethogakade.dao.custom.impl;

import lk.ijse.javaeethogakade.dao.DBConnectionPool;
import lk.ijse.javaeethogakade.dao.custom.ItemDAO;
import lk.ijse.javaeethogakade.entity.Items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Items> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Items> allItems = new ArrayList<>();
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Items");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                allItems.add(new Items(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4)));
            }
        }
        return allItems;
    }

    @Override
    public boolean add(Items entity) throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Items VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, entity.getItemCode());
            preparedStatement.setString(2, entity.getItemName());
            preparedStatement.setDouble(3, entity.getItemPrice());
            preparedStatement.setInt(4, entity.getItemQuantity());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Items entity) throws SQLException, ClassNotFoundException {
        try(Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Items SET ItemName=?, ItemPrice=?, ItemQuantity=? WHERE ItemCode=?");
            preparedStatement.setString(1, entity.getItemName());
            preparedStatement.setDouble(2, entity.getItemPrice());
            preparedStatement.setInt(3, entity.getItemQuantity());
            preparedStatement.setString(4, entity.getItemCode());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ItemCode FROM Items WHERE ItemCode=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ItemCode FROM Items ORDER BY ItemCode DESC LIMIT 1");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                int newId = Integer.parseInt(id.replace("I00-", "")) + 1;
                return String.format("I00-%03d", newId);
            } else {
                return "I00-001";
            }
        }
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
        try (Connection connection = DBConnectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Items WHERE ItemCode=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Items(resultSet.getString(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
        }
    }

    @Override
    public Boolean updateQty(String id,int qty) throws Exception {
        try(Connection connection = DBConnectionPool.getConnection()){
            PreparedStatement pstm = connection.prepareStatement("UPDATE Items SET ItemQuantity=ItemQuantity-? WHERE ItemCode=?");
            pstm.setObject(1,qty);
            pstm.setObject(2,id);
            return pstm.executeUpdate()>0;
        }
    }
}
