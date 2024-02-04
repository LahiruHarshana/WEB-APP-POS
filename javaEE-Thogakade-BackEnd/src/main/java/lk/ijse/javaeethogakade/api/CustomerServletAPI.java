package lk.ijse.javaeethogakade.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.javaeethogakade.util.SQLUtil;
import lk.ijse.javaeethogakade.dto.CustomerDto;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "customerServlet", value = "/customer/*")
public class CustomerServletAPI extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain");
        try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            CustomerDto customerDto = objectMapper.readValue(jsonInput.toString(), CustomerDto.class);

            String sql = "INSERT INTO customer (cusID, cusName, cusAddress, cusSalary) VALUES (?, ?, ?, ?)";
            Boolean result = SQLUtil.execute(sql, customerDto.getId(), customerDto.getName(), customerDto.getAddress(), customerDto.getSalary());

            if (result) {
                response.getWriter().println("Customer has been saved successfully");
            } else {
                response.getWriter().println("Failed to save customer");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void getAll(String customerId, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            String sql = "SELECT * FROM customer WHERE cusID=?";
            ResultSet rst = SQLUtil.execute(sql, customerId);

            PrintWriter writer = response.getWriter();

            JsonArrayBuilder allCustomer = Json.createArrayBuilder();

            while (rst.next()) {
                String id = rst.getString("cusID");
                String name = rst.getString("cusName");
                String address = rst.getString("cusAddress");
                double salary = rst.getDouble("cusSalary");

                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id", id);
                customer.add("name", name);
                customer.add("address", address);
                customer.add("salary", salary);

                allCustomer.add(customer.build());
            }
            writer.print(allCustomer.build());
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:63342");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.setContentType("application/json");


        String customerId = request.getParameter("customerId");
        if (customerId != null) {
            getAll(customerId, response);
            return;
        }
        try {
            String sql = "SELECT * FROM customer";
            ResultSet rst = SQLUtil.execute(sql);

            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");


            JsonArrayBuilder allCustomer = Json.createArrayBuilder();

            while (rst.next()) {
                String id = rst.getString("cusID");
                String name = rst.getString("cusName");
                String address = rst.getString("cusAddress");
                double salary = rst.getDouble("cusSalary");

                JsonObjectBuilder customer = Json.createObjectBuilder();

                customer.add("id", id);
                customer.add("name", name);
                customer.add("address", address);
                customer.add("salary", salary);

                allCustomer.add(customer.build());
            }
            writer.print(allCustomer.build());
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error in doGet method", e);
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain");
        try {
            BufferedReader reader = request.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            CustomerDto customerDto = objectMapper.readValue(jsonInput.toString(), CustomerDto.class);

            String sql = "UPDATE customer SET cusName=?, cusAddress=?, cusSalary=? WHERE cusID=?";
            Boolean result = SQLUtil.execute(sql, customerDto.getName(), customerDto.getAddress(), customerDto.getSalary(), customerDto.getId());

            if (result) {
                response.getWriter().println("Customer has been updated successfully");
            } else {
                response.getWriter().println("Failed to update customer");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/plain");
        try {
            String customerId = request.getPathInfo().substring(1);
            String sql = "DELETE FROM customer WHERE cusID=?";
            Boolean result = SQLUtil.execute(sql, customerId);

            if (result) {
                response.getWriter().println("Customer has been deleted successfully");
            } else {
                response.getWriter().println("Customer not found or could not be deleted");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error in doDelete method", e);
        }
    }

}
