package lk.ijse.javaeethogakade.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaeethogakade.bo.custom.PurchaseOrderBO;
import lk.ijse.javaeethogakade.bo.custom.impl.PurchaseOrderBOImpl;
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

    PurchaseOrderBO purchaseOrderBO = new PurchaseOrderBOImpl();

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
            }
            ObjectMapper objectMapper = new ObjectMapper();
            OrderDto orderDto = objectMapper.readValue(jsonInput.toString(), OrderDto.class);

            Boolean saved = purchaseOrderBO.purchaseOrder(orderDto);

            if (saved) {
                resp.getWriter().write("Order successfully saved.");
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.getWriter().write("Failed to save order.");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            resp.getWriter().write("Error processing request: " + e.getMessage());
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
