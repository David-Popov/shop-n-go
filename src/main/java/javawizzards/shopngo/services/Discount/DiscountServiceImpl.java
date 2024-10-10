package javawizzards.shopngo.services.Discount;

import javawizzards.shopngo.dtos.Discount.Request.DiscountDto;
import javawizzards.shopngo.entities.Discount;
import javawizzards.shopngo.repositories.DiscountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<Discount> getDiscounts() {
        try {
            return discountRepository.findAll();
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Discount getDiscountById(long id) {
        try{
            return discountRepository.findById(id).orElse(null);
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Discount createDiscount(DiscountDto discountDto) {
        try{
            var discount = new Discount();
            discount.setName(discountDto.getName());
            discount.setPrice(discountDto.getPrice());
            discount.setCreatedAt(LocalDateTime.now());
            discount.setUpdatedAt(LocalDateTime.now());

            this.discountRepository.save(discount);

            return discount;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Discount updateDiscount(long id, DiscountDto discountDto) {
        try{
            var discount = this.getDiscountById(id);

            discount.setName(discountDto.getName());
            discount.setPrice(discountDto.getPrice());
            discount.setCreatedAt(LocalDateTime.now());
            discount.setUpdatedAt(LocalDateTime.now());
            this.discountRepository.save(discount);

            return discount;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Discount deleteDiscount(long id) {
        try{
            var discount = this.getDiscountById(id);

            discount.setDeleted(true);

            this.discountRepository.save(discount);

            return discount;
        } catch (Exception e) {
            throw e;
        }
    }
}
