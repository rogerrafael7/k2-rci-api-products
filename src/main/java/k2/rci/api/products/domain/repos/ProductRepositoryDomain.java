package k2.rci.api.products.domain.repos;

import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.repos.dto.CreateProductModelRequest;
import k2.rci.api.products.domain.repos.dto.UpdateProductModelRequest;
import k2.rci.api.products.infra.shared.exceptions.ServerException;
import k2.rci.api.products.infra.shared.types.PaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;

import java.util.Optional;

public abstract class ProductRepositoryDomain {
    public abstract Optional<ProductModel> getProductById(Long id);
    public abstract ProductModel getProductByIdOrFail(Long id) throws ServerException;
    public abstract PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest);
    public abstract ProductModel createProduct(CreateProductModelRequest product);
    public abstract void updateProduct(Long id, UpdateProductModelRequest product);
    public abstract void deleteProduct(Long id);
}
