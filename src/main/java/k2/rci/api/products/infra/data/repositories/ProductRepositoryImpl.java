package k2.rci.api.products.infra.data.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import k2.rci.api.products.domain.dto.GetProductsByIdsResponse;
import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.repositories.ProductRepository;
import k2.rci.api.products.domain.repositories.dto.CreateProductModelRequest;
import k2.rci.api.products.domain.repositories.dto.UpdateProductModelRequest;
import k2.rci.api.products.infra.data.entities.ProductEntity;
import k2.rci.api.products.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import k2.rci.api.products.infra.shared.exceptions.ServerException;
import k2.rci.api.products.infra.shared.types.PaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductRepositoryImpl extends ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ProductModel getProductByIdOrFail(UUID id) throws ServerException {
        var productEntity = entityManager.find(ProductEntity.class, id);
        if (productEntity == null) {
            throw new ServerException("Product not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        return productEntity;
    }

    @Override
    public GetProductsByIdsResponse getProductModelsByIds(List<UUID> ids) {
        var listFromDatabase = entityManager.createQuery("SELECT p FROM products p WHERE p.id IN :ids", ProductModel.class)
                .setParameter("ids", ids)
                .getResultList();

        List<ProductModel> productsFound = new ArrayList<>();
        List<UUID> productIdsNotFound = new ArrayList<>();

        for (var id : ids) {
            var product = listFromDatabase.stream().filter(p -> p.getId().equals(id)).findFirst();
            if (product.isPresent()) {
                productsFound.add(product.get());
            } else {
                productIdsNotFound.add(id);
            }
        }

        return new GetProductsByIdsResponse(productsFound, productIdsNotFound);
    }

    @Override
    public PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest) {
        var query = entityManager.createQuery("SELECT p FROM products p", ProductModel.class);
        var page = paginationRequest.getPage();
        var limit = paginationRequest.getLimit();
        int offset = (page - 1) * limit;

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<ProductModel> products = query.getResultList();

        Long count = entityManager.createQuery("SELECT COUNT(p) FROM products p", Long.class).getSingleResult();

        var totalItems = count.intValue();
        var totalPages = (int) Math.ceil((double) totalItems / limit);
        var hasNextPage = page < totalPages;
        var hasPreviousPage = page > 1;

        return new PaginationResponse<>(
                page,
                limit,
                totalItems,
                totalPages,
                hasNextPage,
                hasPreviousPage,
                products
        );
    }

    @Transactional
    @Override
    public ProductModel createProduct(CreateProductModelRequest product) {
        ProductModel productEntity = new ProductEntity();
        productEntity.setName(product.name());
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setType(product.type());
        productEntity.setDescription(product.description().orElse(null));
        entityManager.persist(productEntity);
        return productEntity;
    }

    @Transactional
    @Override
    public void updateProduct(UUID id, UpdateProductModelRequest product) {
        ProductModel productModel = getProductByIdOrFail(id);
        product.name().ifPresent(productModel::setName);
        product.price().ifPresent(productModel::setPrice);
        product.type().ifPresent(productModel::setType);
        product.description().ifPresent(productModel::setDescription);
        entityManager.persist(productModel);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID id) {
        var productEntity = getProductByIdOrFail(id);
        entityManager.remove(productEntity);
    }
}
