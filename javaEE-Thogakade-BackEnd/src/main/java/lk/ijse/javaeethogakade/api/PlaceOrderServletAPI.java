package lk.ijse.javaeethogakade.api;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "PlaceOrderServletAPI", urlPatterns = "/order/*")
public class PlaceOrderServletAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/plain");

        try {
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDto orderDto = objectMapper.readValue(jsonInput.toString(), OrderDto.class);
            String sqlOrder = "INSERT INTO orders (orderID, orderDate, cusID) VALUES (?, ?, ?)";
            Boolean orderResult = SQLUtil.execute(sqlOrder, orderDto.getOrderId(), orderDto.getOrderDate(), orderDto.getCustomerId());

            if (orderResult) {
                for (OrderDetailDto orderDetail : orderDto.getOrderItems()) {
                    String sqlOrderDetail = "INSERT INTO Order_Detail (itemCode, orderID, quantity, itemPrice) VALUES (?, ?, ?, ?)";
                    Boolean orderDetailResult = SQLUtil.execute(sqlOrderDetail, orderDetail.getItemID(), orderDto.getOrderId(), orderDetail.getQty(), orderDetail.getUnitPrice());

                    // You may want to handle errors or log them appropriately
                    if (!orderDetailResult) {
                        resp.getWriter().println("Failed to save order details");
                        return;
                    }
                }

                resp.getWriter().println("Order and Order Details saved successfully");
            } else {
                resp.getWriter().println("Failed to save order");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

