package javawizzards.shopngo.controllers;

import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.enumerations.ProductMessages;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.services.Product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<Response<?>> getAllProducts() {
        try {
            var productsList = this.productMapper.toProductDtoList(this.productService.GetAllProducts());

            var response = new Response<>(productsList, HttpStatus.OK, "");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            var response = new Response<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Response<List<ProductDto>>> createProduct(@RequestBody List<ProductDto> request) {
        try {
            if (request == null) {
                return ResponseEntity.badRequest()
                        .body(new Response<>(ProductMessages.INVALID_PRODUCT_DETAILS.getMessage(), HttpStatus.BAD_REQUEST, ""));
            }

            var productsList = this.productMapper.toProductDtoList(this.productService.createProducts(request));
            return ResponseEntity.ok(new Response<>(productsList, HttpStatus.OK, ProductMessages.PRODUCT_CREATION_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_CREATION_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductDto>> getProductById(@PathVariable UUID id) {
        try {
            if (id == null || id.toString().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new Response<>(ProductMessages.INVALID_PRODUCT_ID.getMessage(), HttpStatus.BAD_REQUEST, ""));
            }

            var product = this.productMapper.toProductDto(this.productService.FindProductById(id));
            return ResponseEntity.ok(new Response<>(product, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_NOT_FOUND.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductDto>> updateProduct(@PathVariable UUID id, @RequestBody ProductDto request) {
        try {
            if (id == null || id.toString().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new Response<>(ProductMessages.INVALID_PRODUCT_ID.getMessage(), HttpStatus.BAD_REQUEST, ""));
            }
            if (request == null) {
                return ResponseEntity.badRequest()
                        .body(new Response<>(ProductMessages.INVALID_PRODUCT_DETAILS.getMessage(), HttpStatus.BAD_REQUEST, ""));
            }

            var product = this.productMapper.toProductDto(this.productService.UpdateProduct(id, request));
            return ResponseEntity.ok(new Response<>(product, HttpStatus.OK, ProductMessages.PRODUCT_UPDATE_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_UPDATE_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteProduct(@PathVariable UUID id) {
        try {
            if (id == null || id.toString().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new Response<>(ProductMessages.INVALID_PRODUCT_ID.getMessage(), HttpStatus.BAD_REQUEST, ""));
            }

            this.productService.DeleteProduct(id);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK, ProductMessages.PRODUCT_DELETE_SUCCESS.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_DELETE_FAILED.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}
