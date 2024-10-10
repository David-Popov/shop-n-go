package javawizzards.shopngo.dtos.Discount.Response;

import javawizzards.shopngo.dtos.Discount.Request.DiscountDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.entities.Discount;

import java.time.LocalDateTime;

public class DiscountResponse extends Response {
    private DiscountDto discount;

    public DiscountResponse(DiscountDto discount) {
        this.discount = discount;
    }

    public DiscountResponse(LocalDateTime date, String errorDescription, String errorCode, String description, DiscountDto discount) {
        super(date, errorDescription, errorCode, description);
        this.discount = discount;
    }

    public DiscountResponse(LocalDateTime date, String errorDescription, DiscountDto discount) {
        super(date, errorDescription);
        this.discount = discount;
    }

    public DiscountResponse(LocalDateTime date, DiscountDto discount) {
        super(date);
        this.discount = discount;
    }

    public DiscountDto getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountDto discount) {
        this.discount = discount;
    }
}
