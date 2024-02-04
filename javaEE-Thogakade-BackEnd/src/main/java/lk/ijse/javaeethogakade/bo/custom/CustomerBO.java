package lk.ijse.javaeethogakade.bo.custom;

import lk.ijse.javaeethogakade.bo.SuperBO;
import lk.ijse.javaeethogakade.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException;

    public boolean addCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException ;

    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException ;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    public String generateNewCustomerID() throws SQLException, ClassNotFoundException;

    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
}
