package javawizzards.shopngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import javawizzards.shopngo.dtos.Product.ProductDto;
import javawizzards.shopngo.dtos.Response;
import javawizzards.shopngo.enumerations.ProductMessages;
import javawizzards.shopngo.mappers.ProductMapper;
import javawizzards.shopngo.services.Product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @Operation(summary = "Get all products", description = "Returns a list of all active products")
    public ResponseEntity<Response<?>> getAllProducts(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") String direction) {
        try {
            Page<ProductDto> productsList = this.productService.getAllProducts(page, size, sortBy, direction);
            return ResponseEntity.ok(new Response<>(productsList, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ""));
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name or description")
    public ResponseEntity<Response<Page<ProductDto>>> searchProducts(
            @Parameter(description = "Search term") @RequestParam String term,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProductDto> products = productService.searchProducts(term, page, size);
            return ResponseEntity.ok(new Response<>(products, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_SEARCH_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter products", description = "Filter products by price range and minimum rating")
    public ResponseEntity<Response<List<ProductDto>>> filterProducts(
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice,
            @Parameter(description = "Minimum rating") @RequestParam(defaultValue = "0") double minRating) {
        try {
            List<ProductDto> products = productService.findProductsByPriceRangeAndRating(minPrice, maxPrice, minRating);
            return ResponseEntity.ok(new Response<>(products, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_FILTER_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/categories")
    @Operation(summary = "Get products by categories", description = "Returns products filtered by categories")
    public ResponseEntity<Response<Page<ProductDto>>> getProductsByCategories(
            @Parameter(description = "Category names") @RequestParam List<String> categories,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProductDto> products = productService.findByCategories(categories, page, size);
            return ResponseEntity.ok(new Response<>(products, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_RETRIEVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock products", description = "Get products with stock below threshold")
    public ResponseEntity<Response<List<ProductDto>>> getLowStockProducts(
            @Parameter(description = "Stock threshold") @RequestParam(defaultValue = "10") int threshold) {
        try {
            List<ProductDto> products = productService.findLowStockProducts(threshold);
            return ResponseEntity.ok(new Response<>(products, HttpStatus.OK, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new Response<>(ProductMessages.PRODUCT_RETRIEVAL_FAILED.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Create new products", description = "Create one or more new products")
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
    @Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
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
    @Operation(summary = "Update product", description = "Update an existing product by its ID")
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
    @Operation(summary = "Delete product", description = "Soft delete a product by its ID")
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