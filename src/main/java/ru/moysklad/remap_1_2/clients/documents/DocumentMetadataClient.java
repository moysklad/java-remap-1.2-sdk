package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.ApiEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.MetadataTemplatesEndpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

public class DocumentMetadataClient<T extends MetaEntity>
        extends EntityClientBase
        implements
        MetadataTemplatesEndpoint {

    private final Class<T> metaResponseEntityClass;

    public DocumentMetadataClient(ApiClient api, String path, Class<T> metaResponseEntityClass) {
        super(api, path + "metadata/");
        this.metaResponseEntityClass = metaResponseEntityClass;
    }

    @ApiEndpoint
    public T get() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                get(metaEntityClass());
    }

    @Override
    public Class<T> metaEntityClass() {
        return metaResponseEntityClass;
    }
}
