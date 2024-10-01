package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Product.AllProductsResponse;
import javawizzards.shopngo.dtos.Product.Create.CreateProductRequest;
import javawizzards.shopngo.dtos.Product.Create.CreateProductResponse;
import javawizzards.shopngo.services.Product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<AllProductsResponse> getAllProducts() {
        try{
            var data = this.productService.GetAllProducts();
            var response = new AllProductsResponse(data);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AllProductsResponse(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        try{
            var data = this.productService.CreateProducts(request);
            var response = new CreateProductResponse(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateProductResponse(e.getMessage()));
        }
    }

//    @PostMapping("/batch")
//    public List<Product> createProducts(@RequestBody List<Product> products) {
//        return productRepository.saveAll(products);
//    }
//
//    @GetMapping("/{id}")
//    public Product getProductById(@PathVariable Long id) {
//        return productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//    }
//
//    @PutMapping("/{id}")
//    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        product.setName(productDetails.getName());
//        product.setDescription(productDetails.getDescription());
//        product.setPrice(productDetails.getPrice());
//        product.setQuantity(productDetails.getQuantity());
//        return productRepository.save(product);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteProduct(@PathVariable Long id) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        productRepository.delete(product);
//    }
}
