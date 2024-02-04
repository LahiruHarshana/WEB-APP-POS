package lk.ijse.javaeethogakade.bo.custom.impl;

import lk.ijse.javaeethogakade.bo.custom.PurchaseOrderBO;
import lk.ijse.javaeethogakade.dto.CustomerDto;
import lk.ijse.javaeethogakade.dto.ItemDTO;
import lk.ijse.javaeethogakade.dto.OrderDto;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
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
        return false;
    }

    @Override
    public ItemDTO findItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
