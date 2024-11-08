package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Order.OrderDto;
import javawizzards.shopngo.dtos.Order.OrderItemDto;
import javawizzards.shopngo.entities.Order;
import javawizzards.shopngo.entities.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDto toOrderDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice()); // Fixed: Removed BigDecimal.valueOf()
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setDeliveryPhoneNumber(order.getDeliveryPhoneNumber());
        dto.setDeliveryName(order.getDeliveryName());
        dto.setDeliveryEmail(order.getDeliveryEmail());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> itemDtos = order.getOrderItems().stream() // Fixed: Changed getProducts() to getOrderItems()
                .map(this::toOrderItemDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        return dto;
    }

    private OrderItemDto toOrderItemDto(OrderItem orderItem) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPriceAtPurchase());
        dto.setTotalPrice(orderItem.getTotalPrice());
        return dto;
    }
}