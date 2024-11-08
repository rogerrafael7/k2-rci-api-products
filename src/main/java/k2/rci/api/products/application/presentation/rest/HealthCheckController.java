package k2.rci.api.products.application.presentation.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import k2.rci.api.products.domain.dto.HealthCheckResponse;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckController {

    @GET
    public HealthCheckResponse healthCheck() {
        return new HealthCheckResponse("UP");
    }
}
