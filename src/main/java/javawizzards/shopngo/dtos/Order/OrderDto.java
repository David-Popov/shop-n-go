package javawizzards.shopngo.dtos.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javawizzards.shopngo.entities.Order.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private UUID id;
    private UUID userId;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private Status status;
    private String deliveryAddress;
    private String deliveryPhoneNumber;
    private String deliveryName;
    private String deliveryEmail;
    private LocalDateTime createdAt;
}

