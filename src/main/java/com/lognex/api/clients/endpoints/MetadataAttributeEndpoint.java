package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.Attribute;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.ApiClientException;

import java.io.IOException;

public interface MetadataAttributeEndpoint extends Endpoint {
    @ApiEndpoint
    default Attribute metadataAttributes(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                get(Attribute.class);
    }
}
