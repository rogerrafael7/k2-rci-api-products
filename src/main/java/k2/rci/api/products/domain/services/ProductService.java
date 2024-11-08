package k2.rci.api.products.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import k2.rci.api.products.domain.dto.CreateProductRequest;
import k2.rci.api.products.domain.dto.CreateProductResponse;
import k2.rci.api.products.domain.dto.UpdateProductRequest;
import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.repos.ProductRepositoryDomain;
import k2.rci.api.products.domain.repos.dto.CreateProductModelRequest;
import k2.rci.api.products.domain.repos.dto.UpdateProductModelRequest;
import k2.rci.api.products.infra.shared.exceptions.ServerException;
import k2.rci.api.products.infra.shared.types.PaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepositoryDomain productRepository;

    public PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest) {
        return productRepository.getProducts(paginationRequest);
    }

    public ProductModel getProductById(Long id) throws ServerException {
        return productRepository.getProductByIdOrFail(id);
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        var productToSave = new CreateProductModelRequest(request.name(), request.price(), request.type(), request.description());
        var productSaved = productRepository.createProduct(productToSave);
        return new CreateProductResponse(productSaved.getId());
    }

    public void updateProduct(Long id, UpdateProductRequest request) throws ServerException {
        var productToUpdate = new UpdateProductModelRequest(request.name(), request.price(), request.type(), request.description());
        productRepository.updateProduct(id, productToUpdate);
    }

    public void deleteProduct(Long id) throws ServerException {
        productRepository.deleteProduct(id);
    }
}
