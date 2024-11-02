package javawizzards.shopngo.dtos.Cart;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private UUID id;
    private UUID userId;
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
}
