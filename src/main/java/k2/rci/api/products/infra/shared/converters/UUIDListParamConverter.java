package k2.rci.api.products.infra.shared.converters;

import jakarta.ws.rs.ext.ParamConverter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UUIDListParamConverter implements ParamConverter<List<UUID>> {

    @Override
    public List<UUID> fromString(String value) {
        return Arrays.stream(value.split(","))
                .map((str) -> UUID.fromString(str.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString(List<UUID> value) {
        return value.stream()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
    }
}