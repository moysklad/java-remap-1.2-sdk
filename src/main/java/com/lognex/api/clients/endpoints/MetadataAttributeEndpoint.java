package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface MetadataAttributeEndpoint extends Endpoint {
    @ApiEndpoint
    default AttributeEntity metadataAttributes(String id) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "metadata/attributes/" + id).
                get(AttributeEntity.class);
    }
}
