package lk.ijse.javaeethogakade.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private String itemCode;
    private String orderID;
    private int quantity;
    private double itemPrice;
}
