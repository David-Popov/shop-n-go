package javawizzards.shopngo.mappers;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
        public ProductDto toProductDto(Product product) {

            return new ProductDto(
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getImageUrl(),
                    product.getRating()
            );
        }

        public Product toProductFromProductDto(ProductDto productDTO) {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setQuantity(productDTO.getQuantity());
            product.setImageUrl(productDTO.getImageUrl());
            product.setRating(productDTO.getRating());

            return product;
        }

        public List<ProductDto> toProductDtoList(List<Product> products) {
            return products.stream()
                    .map(this::toProductDto)
                    .collect(Collectors.toList());
        }

        public List<Product> toProductListFromProductDtoList(List<ProductDto> productDTOs) {
            return productDTOs.stream()
                    .map(this::toProductFromProductDto)
                    .collect(Collectors.toList());
    }
}
