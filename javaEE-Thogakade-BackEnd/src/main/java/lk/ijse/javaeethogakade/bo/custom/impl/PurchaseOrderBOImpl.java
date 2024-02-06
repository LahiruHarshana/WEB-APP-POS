package lk.ijse.javaeethogakade.bo.custom.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.javaeethogakade.bo.custom.PurchaseOrderBO;
import lk.ijse.javaeethogakade.dao.custom.ItemDAO;
import lk.ijse.javaeethogakade.dao.custom.OrderDAO;
import lk.ijse.javaeethogakade.dao.custom.OrderDetailsDAO;
import lk.ijse.javaeethogakade.dao.custom.impl.ItemDAOImpl;
import lk.ijse.javaeethogakade.dao.custom.impl.OrderDAOImpl;
import lk.ijse.javaeethogakade.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.javaeethogakade.db.DBConnection;
import lk.ijse.javaeethogakade.dto.CustomerDto;
import lk.ijse.javaeethogakade.dto.ItemDTO;
import lk.ijse.javaeethogakade.dto.OrderDetailDto;
import lk.ijse.javaeethogakade.dto.OrderDto;
import lk.ijse.javaeethogakade.entity.OrderDetails;
import lk.ijse.javaeethogakade.entity.Orders;
import lk.ijse.javaeethogakade.util.SQLUtil;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    ItemDAO itemDAO = new ItemDAOImpl();
    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean purchaseOrder(OrderDto dto) {
        try (Connection connection = DBConnection.getDbConnection().getConnection()) {
            connection.setAutoCommit(false);

            Boolean orderResult = orderDAO.add(new Orders(dto.getOrderID(), dto.getOrderDate(), dto.getCusID()));
            OrderDto orderDto = new OrderDto(dto.getOrderID(), dto.getOrderDate(), dto.getCusID(), dto.getOrderItems());

            if (orderResult) {
                // Save Order Details
                for (OrderDetailDto orderDetail : orderDto.getOrderItems()) {
                    Boolean orderDetailResult = orderDetailsDAO.add(new OrderDetails(orderDetail.getOrderID(), orderDetail.getItemCode(), orderDetail.getQuantity(), orderDetail.getItemPrice()));

                    if (!orderDetailResult) {
                        System.out.println("Failed to save order details");
                        connection.rollback();
                        return false;
                    }

                    // Update Item Quantity
                    Boolean updateQuantityResult = itemDAO.updateQty(orderDetail.getItemCode(), orderDetail.getQuantity());
                    if (!updateQuantityResult) {
                        connection.rollback();
                        return false;
                    }
                }
                connection.commit();
                return true; // Commit successful
            } else {
                connection.rollback();
                return false; // Rollback due to order failure
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing purchase order", e);
        }
    }


    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
