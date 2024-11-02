package javawizzards.shopngo.dtos.Cart;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private UUID id;
    private UUID productId;
    private String productName;
    private int quantity;
    private BigDecimal totalPrice;
}
