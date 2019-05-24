package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface DocumentNewEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T newDocument() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(new Object()).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T newDocument(DocumentEntity documentEntity) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(documentEntity).
                put((Class<T>) entityClass());
    }
}
