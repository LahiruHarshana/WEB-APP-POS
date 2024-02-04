package lk.ijse.javaeethogakade.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class Customer {
    private String cusID;
    private String cusName;
    private String cusAddress;
    private double cusSalary;
}
