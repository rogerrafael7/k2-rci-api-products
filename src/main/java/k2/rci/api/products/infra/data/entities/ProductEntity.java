package k2.rci.api.products.infra.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import k2.rci.api.products.domain.models.product.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "products")
@Getter
@Setter
public class ProductEntity extends ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Double price;

    private String description;

    private String type;
}
