package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.ApiEndpoint;
import ru.moysklad.remap_1_2.entities.GlobalMetadata;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;

public class MetadataClient extends EntityClientBase {
    public MetadataClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/metadata/");
    }

    @ApiEndpoint
    public GlobalMetadata get(ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                get(GlobalMetadata.class);
    }
}
