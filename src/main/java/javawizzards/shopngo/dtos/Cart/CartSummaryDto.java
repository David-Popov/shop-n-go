package javawizzards.shopngo.dtos.Cart;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartSummaryDto {
    private int totalItems;
    private BigDecimal totalPrice;
}
