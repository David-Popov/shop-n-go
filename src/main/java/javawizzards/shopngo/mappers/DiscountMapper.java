package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Discount.Request.DiscountDto;
import javawizzards.shopngo.entities.Discount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscountMapper {
    public DiscountDto toDTO(Discount discount) {
        return new DiscountDto(
                discount.getId(),
                discount.getName(),
                discount.getDescription(),
                discount.getPrice()
        );
    }

    public Discount toEntity(DiscountDto DiscountDto) {
        Discount discount = new Discount();
        discount.setId(DiscountDto.getId());
        discount.setName(DiscountDto.getName());
        discount.setDescription(DiscountDto.getDescription());
        discount.setPrice(DiscountDto.getPrice());
        return discount;
    }

    public List<DiscountDto> toDiscountDtoList(List<Discount> discounts) {
        return discounts.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
