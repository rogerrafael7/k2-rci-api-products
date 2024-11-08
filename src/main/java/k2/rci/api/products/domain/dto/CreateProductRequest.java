package k2.rci.api.products.domain.dto;

import java.util.Optional;

public record CreateProductRequest(String name, Double price, String type, Optional<String> description) {
}
