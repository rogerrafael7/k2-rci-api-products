package k2.rci.api.products.domain.dto;

import k2.rci.api.products.domain.models.product.ProductModel;

import java.util.List;
import java.util.UUID;

public record GetProductsByIdsResponse(
        List<ProductModel> productsFound,
        List<UUID> productIdsNotFound
) {
}
