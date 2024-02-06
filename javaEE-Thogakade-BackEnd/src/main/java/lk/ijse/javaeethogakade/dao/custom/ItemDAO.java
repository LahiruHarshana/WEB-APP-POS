package lk.ijse.javaeethogakade.dao.custom;

import lk.ijse.javaeethogakade.dao.CrudDAO;
import lk.ijse.javaeethogakade.entity.Items;

public interface ItemDAO extends CrudDAO<Items>{
    public String updateQty(String id) throws Exception;
}
