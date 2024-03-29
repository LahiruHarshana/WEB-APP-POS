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
import lk.ijse.javaeethogakade.bo.custom.ItemBO;
import lk.ijse.javaeethogakade.bo.custom.impl.ItemBOImpl;
import lk.ijse.javaeethogakade.dto.ItemDTO;
import lk.ijse.javaeethogakade.util.SQLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "itemServlet", value = "/item/*")
public class ItemServletAPI extends HttpServlet {

    ItemBO itemBO = new ItemBOImpl();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemId = req.getParameter("itemId");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        if(itemId != null){
            try {
                String sql = "SELECT * FROM Items WHERE ItemCode=?";
                ResultSet rst = SQLUtil.execute(sql, itemId);

                PrintWriter writer = resp.getWriter();
                JsonArrayBuilder allItems = Json.createArrayBuilder();

                while (rst.next()) {
                    String code = rst.getString("ItemCode");
                    String description = rst.getString("ItemName");
                    double unitPrice = rst.getDouble("ItemPrice");
                    int qtyOnHand = rst.getInt("ItemQuantity");

                    JsonObjectBuilder item = Json.createObjectBuilder();

                    item.add("code", code);
                    item.add("description", description);
                    item.add("unitPrice", unitPrice);
                    item.add("qtyOnHand", qtyOnHand);
                    allItems.add(item.build());
                }
                writer.print(allItems.build());
            } catch (ClassNotFoundException | SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }


        try {
            ArrayList<ItemDTO> Items = itemBO.getAllItems();
            resp.setContentType("application/json");

            JsonArrayBuilder allItems = Json.createArrayBuilder();
            PrintWriter writer = resp.getWriter();

            for (ItemDTO item : Items) {
                JsonObjectBuilder items = Json.createObjectBuilder();
                items.add("code", item.getCode());
                items.add("description", item.getDescription());
                items.add("unitPrice", item.getUnitPrice());
                items.add("qtyOnHand", item.getQtyOnHand());

                allItems.add(items.build());
            }
            writer.print(allItems.build());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");
        try {
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ItemDTO itemDTO = objectMapper.readValue(jsonInput.toString(), ItemDTO.class);

            Boolean result = itemBO.saveItem(itemDTO);

            JsonObjectBuilder jsonResponse = Json.createObjectBuilder();

            if (result) {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.add("message", "Item has been inserted successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                jsonResponse.add("error", "Failed to insert item");
            }

            resp.getWriter().println(jsonResponse.build().toString());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObjectBuilder jsonResponse = Json.createObjectBuilder()
                    .add("error", "Internal server error");
            resp.getWriter().println(jsonResponse.build().toString());
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");
        try {
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            ItemDTO itemDTO = objectMapper.readValue(jsonInput.toString(), ItemDTO.class);

            Boolean result = itemBO.updateItem(itemDTO);
            JsonObjectBuilder jsonResponse = Json.createObjectBuilder();

            if (result) {
                resp.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.add("message", "Item has been updated successfully");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse.add("error", "Item not found or could not be updated");
            }

            resp.getWriter().println(jsonResponse.build().toString());

        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObjectBuilder jsonResponse = Json.createObjectBuilder()
                    .add("error", "Internal server error");
            resp.getWriter().println(jsonResponse.build().toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");
        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo != null && pathInfo.length() > 1) {
                String code = pathInfo.substring(1);


                Boolean result = itemBO.deleteItem(code);

                JsonObjectBuilder jsonResponse = Json.createObjectBuilder();

                if (result) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    jsonResponse.add("message", "Item has been deleted successfully");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    jsonResponse.add("error", "Item not found or could not be deleted");
                }

                resp.getWriter().println(jsonResponse.build().toString());
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("{\"error\": \"Invalid item code\"}");
            }
        } catch (SQLException | ClassNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("{\"error\": \"Error processing the request: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }

}
