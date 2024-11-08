package k2.rci.api.products.domain.dto;

import java.util.Optional;

public record UpdateProductRequest(
        Optional<String> name,
        Optional<Double> price,
        Optional<String> type,
        Optional<String> description
) {
}
