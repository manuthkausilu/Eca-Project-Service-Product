package lk.ijse.eca.productservice.service.impl;

import lk.ijse.eca.productservice.dto.ProductDto;
import lk.ijse.eca.productservice.entity.Product;
import lk.ijse.eca.productservice.exception.DuplicateProductException;
import lk.ijse.eca.productservice.exception.ProductNotFoundException;
import lk.ijse.eca.productservice.mapper.ProductMapper;
import lk.ijse.eca.productservice.repository.ProductRepository;
import lk.ijse.eca.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto dto) {
        log.debug("Creating product with ID: {}", dto.getProductId());

        if (productRepository.existsById(dto.getProductId())) {
            log.warn("Duplicate product ID detected: {}", dto.getProductId());
            throw new DuplicateProductException(dto.getProductId());
        }

        Product saved = productRepository.save(productMapper.toEntity(dto));
        log.info("Product created successfully: {}", saved.getProductId());
        return productMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProduct(String productId) {
        log.debug("Fetching product with ID: {}", productId);
        return productRepository.findById(productId)
                .map(productMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Product not found: {}", productId);
                    return new ProductNotFoundException(productId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        log.debug("Fetching all products");
        List<ProductDto> products = productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
        log.debug("Fetched {} product(s)", products.size());
        return products;
    }

    @Override
    @Transactional
    public ProductDto updateProduct(String productId, ProductDto dto) {
        log.debug("Updating product with ID: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found for update: {}", productId);
                    return new ProductNotFoundException(productId);
                });

        productMapper.updateEntity(dto, product);
        Product updated = productRepository.save(product);
        log.info("Product updated successfully: {}", updated.getProductId());
        return productMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(String productId) {
        log.debug("Deleting product with ID: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Product not found for deletion: {}", productId);
                    return new ProductNotFoundException(productId);
                });

        productRepository.delete(product);
        log.info("Product deleted successfully: {}", productId);
    }
}
