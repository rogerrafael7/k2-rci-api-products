package k2.rci.api.products.domain.dto;

import jakarta.ws.rs.QueryParam;
import k2.rci.api.products.infra.shared.converters.UUIDListParamConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GetProductsByIdsRequest {
    @QueryParam("productIds")
    private String productIds;

    public List<UUID> getProductIdsAsList() {
        UUIDListParamConverter converter = new UUIDListParamConverter();
        return converter.fromString(productIds);
    }
}
