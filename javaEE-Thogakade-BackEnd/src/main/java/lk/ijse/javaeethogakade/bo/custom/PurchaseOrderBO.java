package lk.ijse.javaeethogakade.bo.custom;

import lk.ijse.javaeethogakade.bo.SuperBO;
import lk.ijse.javaeethogakade.dto.CustomerDto;
import lk.ijse.javaeethogakade.dto.ItemDTO;
import lk.ijse.javaeethogakade.dto.OrderDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {

    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException ;

    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException ;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException ;

    public String generateOrderID() throws SQLException, ClassNotFoundException ;

    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public boolean purchaseOrder(OrderDto dto)throws SQLException, ClassNotFoundException;

    public ItemDTO findItem(String code)throws SQLException, ClassNotFoundException;
}
