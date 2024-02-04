package lk.ijse.javaeethogakade.bo.custom;

import lk.ijse.javaeethogakade.bo.SuperBO;
import lk.ijse.javaeethogakade.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException ;

    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existItem(String code) throws SQLException, ClassNotFoundException ;

    public String generateNewCode() throws SQLException, ClassNotFoundException ;
}
