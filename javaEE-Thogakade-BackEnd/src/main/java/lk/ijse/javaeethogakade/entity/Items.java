package lk.ijse.javaeethogakade.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    private String ItemCode;
    private String ItemName;
    private double ItemPrice;
    private int ItemQuantity;
}
