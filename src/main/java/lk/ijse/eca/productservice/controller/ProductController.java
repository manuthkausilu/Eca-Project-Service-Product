package lk.ijse.eca.productservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lk.ijse.eca.productservice.dto.ProductDto;
import lk.ijse.eca.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProductController {

    private final ProductService productService;

    private static final String PRODUCT_ID_REGEXP = "^[A-Z]+$";

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDto> createProduct(
            @Validated({Default.class, ProductDto.OnCreate.class})
            @RequestBody ProductDto dto) {
        log.info("POST /api/v1/products - productId: {}", dto.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(dto));
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> getProduct(
            @PathVariable
            @Pattern(regexp = PRODUCT_ID_REGEXP, message = "Product ID must contain uppercase letters only (A-Z)")
            String productId) {
        log.info("GET /api/v1/products/{}", productId);
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.info("GET /api/v1/products - retrieving all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping(
            value = "/{productId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable
            @Pattern(regexp = PRODUCT_ID_REGEXP, message = "Product ID must contain uppercase letters only (A-Z)")
            String productId,
            @Valid @RequestBody ProductDto dto) {
        log.info("PUT /api/v1/products/{}", productId);
        return ResponseEntity.ok(productService.updateProduct(productId, dto));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable
            @Pattern(regexp = PRODUCT_ID_REGEXP, message = "Product ID must contain uppercase letters only (A-Z)")
            String productId) {
        log.info("DELETE /api/v1/products/{}", productId);
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
