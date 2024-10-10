package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Discount.Request.DiscountDto;
import javawizzards.shopngo.dtos.Discount.Response.DiscountListResponse;
import javawizzards.shopngo.dtos.Discount.Response.DiscountResponse;
import javawizzards.shopngo.mappers.DiscountMapper;
import javawizzards.shopngo.services.Discount.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountService discountService;
    private final DiscountMapper discountMapper;

    public DiscountController(DiscountService discountService, DiscountMapper discountMapper) {
        this.discountService = discountService;
        this.discountMapper = discountMapper;
    }

    @GetMapping
    public ResponseEntity<DiscountListResponse> getDiscounts() {
        try {
            var discounts = this.discountMapper.toDiscountDtoList(discountService.getDiscounts());
            var response = new DiscountListResponse(LocalDateTime.now(), discounts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<DiscountResponse> createDiscount(@RequestBody DiscountDto discountDto) {
        try {
            if (discountDto == null) {
                var response = new DiscountResponse(LocalDateTime.now(), "Invalid Discount Data!", null);
                return ResponseEntity.badRequest().body(response);
            }

            var discount = this.discountMapper.toDTO(discountService.createDiscount(discountDto));
            var response = new DiscountResponse(LocalDateTime.now(), discount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountResponse> updateDiscount(@PathVariable long id, @RequestBody DiscountDto discountDto) {
        try {
            if (discountDto == null) {
                var response = new DiscountResponse(LocalDateTime.now(), "Invalid Discount Data!", null);
                return ResponseEntity.badRequest().body(response);
            }

            var discount = this.discountMapper.toDTO(discountService.updateDiscount(id, discountDto));
            var response = new DiscountResponse(LocalDateTime.now(), discount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DiscountResponse> deleteDiscount(@PathVariable long id) {
        try {
            if (id < 0) {
                var response = new DiscountResponse(LocalDateTime.now(), "Invalid Discount Id!", null);
                return ResponseEntity.badRequest().body(response);
            }

            var discount = this.discountMapper.toDTO(discountService.deleteDiscount(id));
            var response = new DiscountResponse(LocalDateTime.now(), discount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
