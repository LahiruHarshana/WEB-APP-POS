package lk.ijse.javaeethogakade.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaeethogakade.dto.OrderDetailDto;
import lk.ijse.javaeethogakade.dto.OrderDto;
import lk.ijse.javaeethogakade.util.SQLUtil;
import lk.ijse.javaeethogakade.db.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "PlaceOrderServletAPI", urlPatterns = "/order/*")
public class PlaceOrderServletAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/plain");

        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDto orderDto = objectMapper.readValue(jsonInput.toString(), OrderDto.class);

            // Save Order
            String sqlOrder = "INSERT INTO orders (orderID, orderDate, cusID) VALUES (?, ?, ?)";
            Boolean orderResult = SQLUtil.execute( sqlOrder, orderDto.getOrderID(), orderDto.getOrderDate(), orderDto.getCusID());

            if (orderResult) {
                // Save Order Details
                for (OrderDetailDto orderDetail : orderDto.getOrderItems()) {
                    String sqlOrderDetail = "INSERT INTO Order_Detail (itemCode, orderID, quantity, itemPrice) VALUES (?, ?, ?, ?)";
                    Boolean orderDetailResult = SQLUtil.execute(sqlOrderDetail, orderDetail.getItemCode(), orderDto.getOrderID(), orderDetail.getQuantity(), orderDetail.getItemPrice());

                    if (!orderDetailResult) {
                        connection.rollback(); // Rollback transaction if any detail fails
                        resp.getWriter().println("Failed to save order details");
                        return;
                    }

                    // Update item quantity in the Items table
                    String sqlUpdateQuantity = "UPDATE Items SET ItemQuantity = ItemQuantity - ? WHERE ItemCode = ?";
                    Boolean updateQuantityResult = SQLUtil.execute(sqlUpdateQuantity, orderDetail.getQuantity(), orderDetail.getItemCode());

                    if (!updateQuantityResult) {
                        connection.rollback(); // Rollback transaction if quantity update fails
                        resp.getWriter().println("Failed to update item quantity");
                        return;
                    }
                }

                connection.commit(); // Commit the transaction if everything is successful
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
}
