package k2.rci.api.products.infra.shared.types;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class DefaultPaginationRequest implements PaginationRequest {
    @QueryParam("page")
    @DefaultValue("1")
    private int page;

    @QueryParam("limit")
    @DefaultValue("100")
    private int limit;

    public DefaultPaginationRequest() {}

    public DefaultPaginationRequest(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    @Override
    public int getPage() {
        return Math.max(page, 1);
    }

    @Override
    public int getLimit() {
        return Math.max(limit, 1);
    }
}