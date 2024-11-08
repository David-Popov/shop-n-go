package javawizzards.shopngo.dtos.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    private String deliveryAddress;
    private String deliveryPhoneNumber;
    private String deliveryName;
    private String deliveryEmail;
}
