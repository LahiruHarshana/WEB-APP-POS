package lk.ijse.javaeethogakade.entity;
import lombok.*;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String orderID;
    private Date orderDate;
    private String cusID;

}
