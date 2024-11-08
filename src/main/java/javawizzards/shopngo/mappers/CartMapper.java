package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Cart.CartDto;
import javawizzards.shopngo.dtos.Cart.CartItemDto;
import javawizzards.shopngo.entities.Cart;
import javawizzards.shopngo.entities.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDto toCartDto(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartDto dto = new CartDto();
        dto.setId(cart.getId());
        dto.setUserId(cart.getUser().getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setItems(cart.getItems().stream()
                .map(this::toCartItemDto)
                .collect(Collectors.toList()));

        return dto;
    }

    public CartItemDto toCartItemDto(CartItem item) {
        if (item == null) {
            return null;
        }

        return new CartItemDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }
}