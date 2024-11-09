package javawizzards.shopngo.services.Order;

import javawizzards.shopngo.dtos.Order.CreateOrderDto;
import javawizzards.shopngo.dtos.Order.OrderDto;
import javawizzards.shopngo.dtos.Order.OrderStatusUpdateDto;
import javawizzards.shopngo.entities.*;
import javawizzards.shopngo.exceptions.OrderCustomException;
import javawizzards.shopngo.mappers.OrderMapper;
import javawizzards.shopngo.repositories.*;
import javawizzards.shopngo.services.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(UUID userId, CreateOrderDto orderDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OrderCustomException.UserNotFoundException());

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new OrderCustomException.EmptyCartException());

        if (cart.getItems().isEmpty()) {
            throw new OrderCustomException.EmptyCartException();
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setDeliveryAddress(orderDto.getDeliveryAddress());
        order.setDeliveryPhoneNumber(orderDto.getDeliveryPhoneNumber());
        order.setDeliveryName(orderDto.getDeliveryName());
        order.setDeliveryEmail(orderDto.getDeliveryEmail());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();

        // Convert cart items to order items and validate stock
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new OrderCustomException.InsufficientStockException(product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setTotalPrice(product.getPrice().multiply(java.math.BigDecimal.valueOf(cartItem.getQuantity())));
            orderItems.add(orderItem);

            // Update product stock
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setOrderItems(orderItems);
        order.calculateTotalPrice();
        Order savedOrder = orderRepository.save(order);

        // Clear the cart after successful order creation
        cartService.clearCart(userId);

        return orderMapper.toOrderDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderById(UUID orderId, UUID userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderCustomException.OrderNotFoundException());

        if (!order.getUser().getId().equals(userId)) {
            throw new OrderCustomException.UnauthorizedAccessException();
        }

        return orderMapper.toOrderDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> getUserOrders(UUID userId, int page, int size) {
        Page<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
        return orders.map(orderMapper::toOrderDto);
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(UUID orderId, OrderStatusUpdateDto statusUpdate) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderCustomException.OrderNotFoundException());

        // Validate status transition
        validateStatusTransition(order.getStatus(), statusUpdate.getStatus());

        order.setStatus(statusUpdate.getStatus());
        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toOrderDto(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrdersByDateRange(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByUserIdAndCreatedAtBetween(userId, startDate, endDate);
        return orders.stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    @Transactional
    public void cancelOrder(UUID orderId, UUID userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderCustomException.OrderNotFoundException());

        if (!order.getUser().getId().equals(userId)) {
            throw new OrderCustomException.UnauthorizedAccessException();
        }

        if (order.getStatus() != Order.Status.PENDING && order.getStatus() != Order.Status.PROCESSING) {
            throw new OrderCustomException.OrderNotCancellableException();
        }

        // Restore product quantities
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setQuantity(product.getQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(Order.Status.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> findPendingOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus(Order.Status.PENDING);
        return pendingOrders.stream()
                .map(orderMapper::toOrderDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDto> findAllOrders(int page, int size) {
        Page<Order> orders = orderRepository.findAll(PageRequest.of(page, size));
        return orders.map(orderMapper::toOrderDto);
    }

    private void validateStatusTransition(Order.Status currentStatus, Order.Status newStatus) {
        // Add your status transition validation logic here
        // Example: PENDING -> PROCESSING -> SHIPPED -> DELIVERED
        if (currentStatus == Order.Status.CANCELLED || currentStatus == Order.Status.SHIPPED) {
            throw new OrderCustomException.InvalidOrderStatusException();
        }

        // Add more specific validation rules as needed
    }
}