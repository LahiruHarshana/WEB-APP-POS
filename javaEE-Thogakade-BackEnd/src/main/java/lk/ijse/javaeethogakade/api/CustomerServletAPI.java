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
import lk.ijse.javaeethogakade.dto.CustomerDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "customerServlet", value = "/customer/*")
public class CustomerServletAPI extends HttpServlet {

    private final CustomerBO customerBO = new CustomerBOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String customerId = request.getParameter("customerId");
        if (customerId != null) {
            getAll(customerId, response);
            return;
        }
        try {
            List<CustomerDto> allCustomers = customerBO.getAllCustomers();

            JsonArrayBuilder allCustomersArray = Json.createArrayBuilder();

            for (CustomerDto customer : allCustomers) {
                JsonObjectBuilder customerObject = Json.createObjectBuilder()
                        .add("id", customer.getId())
                        .add("name", customer.getName())
                        .add("address", customer.getAddress())
                        .add("salary", customer.getSalary());
                allCustomersArray.add(customerObject);
            }

            PrintWriter writer = response.getWriter();
            writer.print(allCustomersArray.build());
        } catch (Exception e) {
            throw new ServletException("Error in doGet method", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String customerId = request.getPathInfo().substring(1);
            Boolean result = customerBO.deleteCustomer(customerId);

            if (result) {
                response.getWriter().println("Customer has been deleted successfully");
            } else {
                response.getWriter().println("Customer not found or could not be deleted");
            }
        } catch (Exception e) {
            throw new ServletException("Error in doDelete method", e);
        }
    }

    private void getAll(String customerId, HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            // Retrieve customer data and construct JSON response
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
