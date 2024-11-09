package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByIsDeletedFalse(Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice " +
            "AND p.rating >= :minRating AND p.isDeleted = false")
    List<Product> findProductsByPriceRangeAndRating(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minRating") double minRating);

    @Query(value = "SELECT p FROM Product p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND p.isDeleted = false")
    Page<Product> searchProducts(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.quantity <= :threshold AND p.isDeleted = false")
    List<Product> findLowStockProducts(@Param("threshold") int threshold);

    @Query(value = "SELECT DISTINCT p FROM Product p JOIN p.categories c " +
            "WHERE c.name IN :categories AND p.isDeleted = false")
    Page<Product> findByCategories(@Param("categories") List<String> categories, Pageable pageable);
}