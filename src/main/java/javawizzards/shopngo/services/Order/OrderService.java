package javawizzards.shopngo.services.Order;

import javawizzards.shopngo.dtos.Order.CreateOrderDto;
import javawizzards.shopngo.dtos.Order.OrderDto;
import javawizzards.shopngo.dtos.Order.OrderStatusUpdateDto;
import javawizzards.shopngo.entities.Order;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(UUID userId, CreateOrderDto orderDto);
    OrderDto getOrderById(UUID orderId, UUID userId);
    Page<OrderDto> getUserOrders(UUID userId, int page, int size);
    OrderDto updateOrderStatus(UUID orderId, OrderStatusUpdateDto statusUpdate);
    List<OrderDto> getOrdersByDateRange(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
    void cancelOrder(UUID orderId, UUID userId);
    List<OrderDto> findPendingOrders();
    Page<OrderDto> findAllOrders(int page, int size);
}