package lk.ijse.javaeethogakade.dto;


import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderID;
    private Date orderDate;
    private String cusID;
    private OrderDetailDto[] orderItems;
}
