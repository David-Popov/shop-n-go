package javawizzards.shopngo.dtos.Order;

import javawizzards.shopngo.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateDto {
    private Order.Status status;
    private String comment;
}
