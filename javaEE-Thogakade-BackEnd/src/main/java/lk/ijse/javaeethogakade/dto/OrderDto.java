package lk.ijse.javaeethogakade.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderID;
    private String orderDate;
    private String cusID;
}
