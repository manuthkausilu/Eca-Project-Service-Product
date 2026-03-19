package lk.ijse.eca.productservice.exception;

public class DuplicateProductException extends RuntimeException {

    public DuplicateProductException(String productId) {
        super("Product already exists with ID: " + productId);
    }
}
