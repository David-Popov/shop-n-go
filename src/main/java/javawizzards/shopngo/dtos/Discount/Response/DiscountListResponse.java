package javawizzards.shopngo.dtos.Discount.Response;

import javawizzards.shopngo.dtos.Discount.Request.DiscountDto;
import javawizzards.shopngo.dtos.Response;

import java.time.LocalDateTime;
import java.util.List;

public class DiscountListResponse extends Response {
    private List<DiscountDto> discounts;

    public DiscountListResponse(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }

    public DiscountListResponse(LocalDateTime date, String errorDescription, String errorCode, String description, List<DiscountDto> discounts) {
        super(date, errorDescription, errorCode, description);
        this.discounts = discounts;
    }

    public DiscountListResponse(LocalDateTime date, String errorDescription, List<DiscountDto> discounts) {
        super(date, errorDescription);
        this.discounts = discounts;
    }

    public DiscountListResponse(LocalDateTime date, List<DiscountDto> discounts) {
        super(date);
        this.discounts = discounts;
    }

    public List<DiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }
}
