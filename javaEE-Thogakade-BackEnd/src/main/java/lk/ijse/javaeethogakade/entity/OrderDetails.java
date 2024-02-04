package lk.ijse.javaeethogakade.entity;

import lombok.*;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderDetails {
    private String itemCode;
    private String orderID;
    private int quantity;
    private double itemPrice;
}
