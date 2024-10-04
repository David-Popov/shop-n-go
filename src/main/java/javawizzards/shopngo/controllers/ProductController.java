package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Product.Request.ProductDto;
import javawizzards.shopngo.dtos.Product.Response.ProductResponse;
import javawizzards.shopngo.dtos.Product.Response.ProductsListResponse;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.services.Product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<ProductsListResponse> getAllProducts() {
        try{
            var productsList = this.productMapper.toProductDtoList(this.productService.GetAllProducts());

            var response = new ProductsListResponse(LocalDateTime.now(), "", "", "", productsList);
            return ResponseEntity.ok(response);

        }catch (Exception e){
            var response = new ProductsListResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ProductsListResponse> createProduct(@RequestBody List<ProductDto> request) {
        try{
            if (request == null){
                var response = new ProductsListResponse(LocalDateTime.now(), "Invalid request!", "", "", null);
                return ResponseEntity.badRequest().body(response);
            }

            var productsList = this.productMapper.toProductDtoList(this.productService.createProducts(request));
            var response = new ProductsListResponse(LocalDateTime.now(), "", "", "", productsList);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            var response = new ProductsListResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.internalServerError().body(response);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        try{
            if (id < 0){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product id!", "", "", null);
                return ResponseEntity.badRequest().body(response);
            }

            var product = this.productMapper.toProductDto(this.productService.FindProductById(id));

            var response = new ProductResponse(LocalDateTime.now(), "", "", "Product deleted successfully", product);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var response = new ProductResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.internalServerError().body(response);
        }


    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductDto request) {
        try{
            if (id < 0){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product id!", "", "", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (request == null){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product details!", "", "", null);
                return ResponseEntity.badRequest().body(response);
            }

            var product = this.productMapper.toProductDto(this.productService.UpdateProduct(id, request));
            var response = new ProductResponse(LocalDateTime.now(), "", "", "Product deleted successfully", product);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            var response = new ProductResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        try{
            if (id < 0){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product id!", "", "", null);
                return ResponseEntity.badRequest().body(response);
            }

            this.productService.DeleteProduct(id);
            var response = new Response(LocalDateTime.now(), "", "", "Product deleted successfully");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            var response = new ProductResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
