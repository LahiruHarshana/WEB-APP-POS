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
import lk.ijse.javaeethogakade.bo.custom.CustomerBO;
import lk.ijse.javaeethogakade.bo.custom.impl.CustomerBOImpl;
import lk.ijse.javaeethogakade.entity.Customer;
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
import java.util.List;

@WebServlet(name = "customerServlet", value = "/customer/*")
public class CustomerServletAPI extends HttpServlet {

    CustomerBO customerBO = new CustomerBOImpl();
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

            Boolean result = customerBO.addCustomer(customerDto);

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

            CustomerDto customerDto = customerBO.searchCustomer(customerId);
            if (customerDto != null) {
                JsonObjectBuilder customer = Json.createObjectBuilder();
                customer.add("id", customerDto.getId());
                customer.add("name", customerDto.getName());
                customer.add("address", customerDto.getAddress());
                customer.add("salary", customerDto.getSalary());

                PrintWriter writer = response.getWriter();
                writer.print(customer.build());
            } else {
                PrintWriter writer = response.getWriter();
                writer.print("Customer not found");
            }
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
            List<CustomerDto> allCustomers = customerBO.getAllCustomers();

            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");

            JsonArrayBuilder allCustomer = Json.createArrayBuilder();

            for (CustomerDto c : allCustomers) {
                String id = c.getId();
                String name = c.getName();
                String address = c.getAddress();
                double salary = c.getSalary();

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

            Boolean result = customerBO.updateCustomer(customerDto);
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
            Boolean result = customerBO.deleteCustomer(customerId);

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
