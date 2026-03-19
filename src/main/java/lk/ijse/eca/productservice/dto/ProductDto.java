package lk.ijse.eca.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    public interface OnCreate {}

    @NotBlank(groups = OnCreate.class, message = "Product ID is required")
    @Pattern(groups = OnCreate.class, regexp = "^[A-Z]+$", message = "Product ID must contain uppercase letters only (A-Z)")
    private String productId;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
