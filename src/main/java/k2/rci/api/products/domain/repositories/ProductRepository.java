package k2.rci.api.products.domain.repositories;

import k2.rci.api.products.domain.dto.GetProductsByIdsResponse;
import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.repositories.dto.CreateProductModelRequest;
import k2.rci.api.products.domain.repositories.dto.UpdateProductModelRequest;
import k2.rci.api.products.infra.shared.exceptions.ServerException;
import k2.rci.api.products.infra.shared.types.PaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;

import java.util.List;
import java.util.UUID;

public abstract class ProductRepository {
    public abstract ProductModel getProductByIdOrFail(UUID id) throws ServerException;
    public abstract GetProductsByIdsResponse getProductModelsByIds(List<UUID> id);
    public abstract PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest);
    public abstract ProductModel createProduct(CreateProductModelRequest product);
    public abstract void updateProduct(UUID id, UpdateProductModelRequest product);
    public abstract void deleteProduct(UUID id);
}
