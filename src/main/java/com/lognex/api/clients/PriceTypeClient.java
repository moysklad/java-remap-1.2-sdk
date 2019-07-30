package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceType;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;

import static com.lognex.api.utils.Constants.API_PATH;

public final class PriceTypeClient
        extends EntityClientBase
        implements GetByIdEndpoint<PriceType>,
        GetPlainListEndpoint<PriceType> {

    public PriceTypeClient(ApiClient api, String path) {
        super(api, path + "pricetype/");
    }

    @ApiEndpoint
    public PriceType getDefault() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "default/").
                apiParams().
                get(PriceType.class);
    }

    @ApiEndpoint
    public List<PriceType> updatePriceTypes(List<PriceType> priceTypes) throws IOException, ApiClientException {
        priceTypes.forEach(pt -> MetaHrefUtils.fillMeta(pt, api.getHost() + API_PATH));
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams().
                body(priceTypes).
                postList(PriceType.class);
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PriceType.class;
    }
}
