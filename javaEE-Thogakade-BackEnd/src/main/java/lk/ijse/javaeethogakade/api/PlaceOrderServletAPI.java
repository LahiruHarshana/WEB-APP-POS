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
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        resp.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("PlaceOrderServletAPI doPost");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/plain");

        try {

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
    }
}
