package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.PriceType;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

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
                get(PriceType.class);
    }

    @ApiEndpoint
    public List<PriceType> updatePriceTypes(List<PriceType> priceTypes) throws IOException, ApiClientException {
        priceTypes.forEach(pt -> MetaHrefUtils.fillMeta(pt, api.getHost() + API_PATH));
        return HttpRequestExecutor.
                path(api(), path()).
                body(priceTypes).
                postList(PriceType.class);
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PriceType.class;
    }
}
