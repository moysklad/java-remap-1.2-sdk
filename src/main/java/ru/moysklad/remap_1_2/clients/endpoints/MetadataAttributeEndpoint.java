package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

public interface MetadataAttributeEndpoint extends Endpoint {
    @ApiEndpoint
    default Attribute metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                get(Attribute.class);
    }

    @ApiEndpoint
    default ListEntity<Attribute> metadataAttributes() throws IOException, ApiClientException {
        ListEntity<Attribute> attributes = HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/").
                list(Attribute.class);
        return attributes;
    }
}
