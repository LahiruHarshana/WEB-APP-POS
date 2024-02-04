package lk.ijse.javaeethogakade.bo.custom.impl;

import lk.ijse.javaeethogakade.bo.custom.CustomerBO;
import lk.ijse.javaeethogakade.dao.custom.CustomerDAO;
import lk.ijse.javaeethogakade.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.javaeethogakade.dto.CustomerDto;
import lk.ijse.javaeethogakade.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDto> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDto(c.getCusID(),c.getCusName(),c.getCusAddress(),c.getCusSalary()));
        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return new CustomerDto(customer.getCusID(),customer.getCusName(),customer.getCusAddress(),customer.getCusSalary());
    }
}
