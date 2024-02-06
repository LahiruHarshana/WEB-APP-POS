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
import lk.ijse.javaeethogakade.util.SQLUtil;

import java.io.BufferedReader;
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
    public boolean purchaseOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        try {
            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

            System.out.println("PlaceOrderServletAPI doPost");

            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
                System.out.println("PlaceOrderServletAPI doPost");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDto orderDto = objectMapper.readValue(jsonInput.toString(), OrderDto.class);

            System.out.println(orderDto);

            // Save Order
            String sqlOrder = "INSERT INTO orders (orderID, orderDate, cusID) VALUES (?, ?, ?)";
            Boolean orderResult = SQLUtil.execute( sqlOrder, orderDto.getOrderID(), orderDto.getOrderDate(), orderDto.getCusID());
            System.out.println(orderResult);

            if (orderResult) {
                // Save Order Details
                for (OrderDetailDto orderDetail : orderDto.getOrderItems()) {
                    String sqlOrderDetail = "INSERT INTO Order_Detail (itemCode, orderID, quantity, itemPrice) VALUES (?, ?, ?, ?)";
                    Boolean orderDetailResult = SQLUtil.execute(sqlOrderDetail, orderDetail.getItemCode(), orderDto.getOrderID(), orderDetail.getQuantity(), orderDetail.getItemPrice());

                    if (!orderDetailResult) {
                        System.out.println("Failed to save order details");
                        connection.rollback();
                        resp.getWriter().println("Failed to save order details");
                        return;
                    }

                    // Update item quantity in the Items table
                    String sqlUpdateQuantity = "UPDATE Items SET ItemQuantity = ItemQuantity - ? WHERE ItemCode = ?";
                    Boolean updateQuantityResult = SQLUtil.execute(sqlUpdateQuantity, orderDetail.getQuantity(), orderDetail.getItemCode());

                    if (!updateQuantityResult) {
                        connection.rollback();
                        resp.getWriter().println("Failed to update item quantity");
                        return;
                    }
                }

                connection.commit();
                resp.getWriter().println("Order, Order Details, and Item Quantity updated successfully");
            } else {
                resp.getWriter().println("Failed to save order");
            }
        } catch (ClassNotFoundException | SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
        }
    }

    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
