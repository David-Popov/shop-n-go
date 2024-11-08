package javawizzards.shopngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javawizzards.shopngo.dtos.Order.CreateOrderDto;
import javawizzards.shopngo.dtos.Order.OrderDto;
import javawizzards.shopngo.dtos.Order.OrderStatusUpdateDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.enumerations.OrderMessages;
import javawizzards.shopngo.services.Order.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create new order", description = "Creates a new order for the specified user")
    public ResponseEntity<Response<OrderDto>> createOrder(
            @PathVariable UUID userId,
            @RequestBody CreateOrderDto orderDto) {
        try {
            OrderDto createdOrder = orderService.createOrder(userId, orderDto);
            return ResponseEntity.ok(new Response<>(createdOrder, HttpStatus.CREATED,
                    OrderMessages.ORDER_CREATION_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_CREATION_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{userId}/{orderId}")
    @Operation(summary = "Get order details", description = "Retrieves details of a specific order")
    public ResponseEntity<Response<OrderDto>> getOrder(
            @PathVariable UUID userId,
            @PathVariable UUID orderId) {
        try {
            OrderDto order = orderService.getOrderById(orderId, userId);
            return ResponseEntity.ok(new Response<>(order, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_RETRIEVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user orders", description = "Retrieves all orders for a specific user")
    public ResponseEntity<Response<Page<OrderDto>>> getUserOrders(
            @PathVariable UUID userId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        try {
            Page<OrderDto> orders = orderService.getUserOrders(userId, page, size);
            return ResponseEntity.ok(new Response<>(orders, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_RETRIEVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/status")
    @Operation(summary = "Update order status", description = "Updates the status of a specific order")
    public ResponseEntity<Response<OrderDto>> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody OrderStatusUpdateDto statusUpdate) {
        try {
            OrderDto updatedOrder = orderService.updateOrderStatus(orderId, statusUpdate);
            return ResponseEntity.ok(new Response<>(updatedOrder, HttpStatus.OK,
                    OrderMessages.ORDER_UPDATE_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_UPDATE_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{userId}/date-range")
    @Operation(summary = "Get orders by date range", description = "Retrieves orders within a specific date range")
    public ResponseEntity<Response<List<OrderDto>>> getOrdersByDateRange(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<OrderDto> orders = orderService.getOrdersByDateRange(userId, startDate, endDate);
            return ResponseEntity.ok(new Response<>(orders, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_RETRIEVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PostMapping("/{userId}/{orderId}/cancel")
    @Operation(summary = "Cancel order", description = "Cancels a specific order")
    public ResponseEntity<Response<Void>> cancelOrder(
            @PathVariable UUID userId,
            @PathVariable UUID orderId) {
        try {
            orderService.cancelOrder(orderId, userId);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK,
                    OrderMessages.ORDER_CANCELLATION_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_CANCELLATION_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending orders", description = "Retrieves all pending orders")
    public ResponseEntity<Response<List<OrderDto>>> getPendingOrders() {
        try {
            List<OrderDto> pendingOrders = orderService.findPendingOrders();
            return ResponseEntity.ok(new Response<>(pendingOrders, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(OrderMessages.ORDER_RETRIEVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}