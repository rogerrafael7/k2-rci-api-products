package k2.rci.api.products.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import k2.rci.api.products.domain.dto.CreateProductRequest;
import k2.rci.api.products.domain.dto.GetProductsByIdsRequest;
import k2.rci.api.products.domain.dto.GetProductsByIdsResponse;
import k2.rci.api.products.domain.dto.UpdateProductRequest;
import k2.rci.api.products.domain.models.product.ProductModel;
import k2.rci.api.products.domain.repositories.ProductRepository;
import k2.rci.api.products.infra.shared.exceptions.ServerException;
import k2.rci.api.products.infra.shared.types.DefaultPaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationRequest;
import k2.rci.api.products.infra.shared.types.PaginationResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
public class ProductServiceTest {

    @Inject
    ProductService productService;

    @InjectMock
    ProductRepository productRepository;

    @Inject
    ObjectMapper objectMapper;

    private static String loadJson(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(STR."src/test/resources/\{path}")));
    }

    @Test
    public void testGetProducts() throws IOException {
        String jsonContent = loadJson("mocks/products.json");
        PaginationResponse mockResponse = objectMapper.readValue(jsonContent, PaginationResponse.class);

        PaginationRequest paginationRequest = new DefaultPaginationRequest();
        paginationRequest.setPage(1);
        paginationRequest.setLimit(10);

        when(productRepository.getProducts(paginationRequest)).thenReturn(mockResponse);

        PaginationResponse<ProductModel> response = productService.getProducts(paginationRequest);

        assertNotNull(response);
        assertEquals(14, response.totalItems());
        verify(productRepository, times(1)).getProducts(paginationRequest);
    }

    @Test
    public void testGetProductById() throws ServerException {
        UUID productId = UUID.randomUUID();
        ProductModel mockProduct = new ProductModel();
        mockProduct.setId(productId);
        mockProduct.setName("Product");

        when(productRepository.getProductByIdOrFail(productId)).thenReturn(mockProduct);

        ProductModel product = productService.getProductById(productId);

        assertNotNull(product);
        assertEquals("Product", product.getName());
        verify(productRepository, times(1)).getProductByIdOrFail(productId);
    }

    @Test
    public void testCreateProduct() {
        CreateProductRequest request = new CreateProductRequest("Product", 100.0, "Type", Optional.empty());
        ProductModel mockProduct = new ProductModel();
        mockProduct.setId(UUID.randomUUID());
        mockProduct.setName("Product");

        when(productRepository.createProduct(any())).thenReturn(mockProduct);

        ProductModel product = productService.createProduct(request);

        assertNotNull(product);
        assertEquals("Product", product.getName());
        verify(productRepository, times(1)).createProduct(any());
    }

    @Test
    public void testUpdateProduct() throws ServerException {
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = new UpdateProductRequest(Optional.of("Updated Product"), Optional.of(200.0), Optional.of("Updated Type"), Optional.of("Updated Description"));

        doNothing().when(productRepository).updateProduct(eq(productId), any());

        productService.updateProduct(productId, request);

        verify(productRepository, times(1)).updateProduct(eq(productId), any());
    }

    @Test
    public void testDeleteProduct() throws ServerException {
        UUID productId = UUID.randomUUID();

        doNothing().when(productRepository).deleteProduct(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteProduct(productId);
    }

    @Test
    public void testGetProductsByIds() {
        List<UUID> productIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        GetProductsByIdsResponse mockResponse = new GetProductsByIdsResponse(List.of(), List.of());

        when(productRepository.getProductModelsByIds(productIds)).thenReturn(mockResponse);

        GetProductsByIdsResponse response = productService.getProductsByIds(productIds);

        assertNotNull(response);
        verify(productRepository, times(1)).getProductModelsByIds(productIds);
    }
}