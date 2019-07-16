package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.ApiEndpoint;
import com.lognex.api.entities.GlobalMetadata;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public class MetadataClient extends EntityClientBase {
    public MetadataClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/metadata/");
    }

    @ApiEndpoint
    public GlobalMetadata get(ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                get(GlobalMetadata.class);
    }
}
