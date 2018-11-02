package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.GlobalMetadataEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;

public class MetadataClient extends ApiClient {
    public MetadataClient(LognexApi api) {
        super(api, "/entity/metadata/");
    }

    public GlobalMetadataEntity get(ApiParam... params) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                get(GlobalMetadataEntity.class);
    }
}
