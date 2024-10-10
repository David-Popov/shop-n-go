package javawizzards.shopngo.services.Discount;

import javawizzards.shopngo.dtos.Discount.Request.DiscountDto;
import javawizzards.shopngo.entities.Discount;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DiscountService {
    List<Discount> getDiscounts();
    Discount getDiscountById(long id);
    Discount createDiscount(DiscountDto discountDto);
    Discount updateDiscount(long id, DiscountDto discountDto);
    Discount deleteDiscount(long id);
}
