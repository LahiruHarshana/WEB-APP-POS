package lk.ijse.javaeethogakade.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class Item {
    private String ItemCode;
    private String ItemName;
    private double ItemPrice;
    private int ItemQuantity;
}
