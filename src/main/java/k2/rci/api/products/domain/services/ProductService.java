package k2.rci.api.products.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import k2.rci.api.products.domain.dto.CreateProductRequest;
import k2.rci.api.products.domain.dto.GetProductsByIdsResponse;
import k2.rci.api.products.domain.dto.UpdateProductRequest;
import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.repositories.ProductRepository;
import k2.rci.api.products.domain.repositories.dto.CreateProductModelRequest;
import k2.rci.api.products.domain.repositories.dto.UpdateProductModelRequest;
import k2.rci.api.products.infra.shared.exceptions.ServerException;
import k2.rci.api.products.infra.shared.types.PaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository productRepository;

    public PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest) {
        return productRepository.getProducts(paginationRequest);
    }

    public ProductModel getProductById(UUID id) throws ServerException {
        return productRepository.getProductByIdOrFail(id);
    }

    public ProductModel createProduct(CreateProductRequest request) {
        var productToSave = new CreateProductModelRequest(request.name(), request.price(), request.type(), request.description());
        var productSaved = productRepository.createProduct(productToSave);
        return productSaved;
    }

    public void updateProduct(UUID id, UpdateProductRequest request) throws ServerException {
        var productToUpdate = new UpdateProductModelRequest(request.name(), request.price(), request.type(), request.description());
        productRepository.updateProduct(id, productToUpdate);
    }

    public void deleteProduct(UUID id) throws ServerException {
        productRepository.deleteProduct(id);
    }

    public GetProductsByIdsResponse getProductsByIds(List<UUID> ids) {
        return productRepository.getProductModelsByIds(ids);
    }
}
