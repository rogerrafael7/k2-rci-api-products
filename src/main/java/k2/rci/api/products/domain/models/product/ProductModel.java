package k2.rci.api.products.domain.models.product;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductModel {
    private UUID id;
    private String name;
    private Double price;
    private String description;
    private String type;
}