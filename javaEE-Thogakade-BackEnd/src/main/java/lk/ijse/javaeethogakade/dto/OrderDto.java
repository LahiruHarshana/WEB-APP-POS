package lk.ijse.javaeethogakade.dto;


import lombok.Data;

@Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class OrderDto {
    private String orderID;
    private String orderDate;
    private String cusID;

}
