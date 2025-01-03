package k2.rci.api.products.application.presentation.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import k2.rci.api.products.domain.dto.CreateProductRequest;
import k2.rci.api.products.domain.dto.GetProductsByIdsRequest;
import k2.rci.api.products.domain.dto.GetProductsByIdsResponse;
import k2.rci.api.products.domain.dto.UpdateProductRequest;
import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.services.ProductService;
import k2.rci.api.products.infra.shared.types.DefaultPaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;

import java.util.UUID;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    ProductService productService;

    @GET
    public PaginationResponse<ProductModel> getProducts(
            @BeanParam
            DefaultPaginationRequest paginationParams
    ) {
        return productService.getProducts(paginationParams);
    }

    @GET
    @Path("/by-ids")
    public GetProductsByIdsResponse getProductsByIds(
            @BeanParam
            GetProductsByIdsRequest request
    ) {
        var ids = request.getProductIdsAsList();
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException("productIds query parameter is required");
        }
        return productService.getProductsByIds(ids);
    }

    @GET
    @Path("/{id}")
    public ProductModel getProductById(@PathParam("id") UUID id) {
        return productService.getProductById(id);
    }

    @POST
    public ProductModel createProduct(CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PATCH
    @Path("/{id}")
    public void updateProduct(@PathParam("id") UUID id, UpdateProductRequest request) {
        productService.updateProduct(id, request);
    }

    @DELETE
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") UUID id) {
        productService.deleteProduct(id);
    }
}
