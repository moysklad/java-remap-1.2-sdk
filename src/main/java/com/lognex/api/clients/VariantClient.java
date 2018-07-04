package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.VariantMetadataListResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class VariantClient implements GetListEndpoint<VariantEntity>, PostEndpoint<VariantEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/variant/";
    }

    VariantClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Модификаций
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<VariantEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, VariantEntity.class, expand);
    }

    /**
     * Создание новой Модификации
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public VariantEntity post(VariantEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, VariantEntity.class);
    }

    /**
     * Получение списка Метаданных Модификаций
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public VariantMetadataListResponse metadata() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api, "/entity/variant/metadata").
                get(VariantMetadataListResponse.class);
    }
}
