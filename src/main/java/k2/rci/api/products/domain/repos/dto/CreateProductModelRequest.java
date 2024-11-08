package k2.rci.api.products.domain.repos.dto;

import java.util.Optional;

public record CreateProductModelRequest(String name, Double price, String type, Optional<String> description) {
}
