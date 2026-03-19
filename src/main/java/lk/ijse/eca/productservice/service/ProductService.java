package lk.ijse.eca.productservice.service;

import lk.ijse.eca.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto dto);

    ProductDto getProduct(String productId);

    List<ProductDto> getAllProducts();

    ProductDto updateProduct(String productId, ProductDto dto);

    void deleteProduct(String productId);
}
