package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Product.FetchData.ListOfProductsResponse;
import javawizzards.shopngo.dtos.Product.Create.CreateProductRequest;
import javawizzards.shopngo.dtos.Product.Create.CreateProductResponse;
import javawizzards.shopngo.dtos.Product.FetchData.ProductResponse;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Product.Update.UpdateProductDto;
import javawizzards.shopngo.dtos.Response;
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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ListOfProductsResponse> getAllProducts() {
        List<ProductDto> productsList = null;

        try{
            productsList = this.productService.GetAllProducts();
        }catch (Exception e){
            var response = new ListOfProductsResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        var response = new ListOfProductsResponse(LocalDateTime.now(), "", "", "", productsList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ListOfProductsResponse> createProduct(@RequestBody CreateProductRequest request) {
        List<ProductDto> productsList = null;
        try{
            if (request == null){
                var response = new ListOfProductsResponse(LocalDateTime.now(), "Invalid request!", "", "", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            productsList = this.productService.CreateProducts(request);
        } catch (Exception e) {
            var response = new ListOfProductsResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        var response = new ListOfProductsResponse(LocalDateTime.now(), "", "", "", productsList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductDto product = null;
        try{
            if (id < 0){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product id!", "", "", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            product = this.productService.FindProductById(id);

        } catch (Exception e) {
            var response = new ProductResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        var response = new ProductResponse(LocalDateTime.now(), "", "", "Product deleted successfully", product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDto productDetails) {
        try{
            if (id < 0){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product id!", "", "", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if (productDetails == null){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product details!", "", "", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            this.productService.UpdateProduct(productDetails);
        } catch (Exception e) {
            var response = new ProductResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        var response = new ProductResponse(LocalDateTime.now(), "", "", "Product deleted successfully", productDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        try{
            if (id < 0){
                var response = new ProductResponse(LocalDateTime.now(), "Invalid product id!", "", "", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            this.productService.DeleteProduct(id);
        } catch (Exception e) {
            var response = new ProductResponse(LocalDateTime.now(), e.getMessage(), "", "", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        var response = new Response(LocalDateTime.now(), "", "", "Product deleted successfully");
        return ResponseEntity.ok(response);
    }
}
