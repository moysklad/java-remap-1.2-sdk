package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Variant;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.responses.VariantMetadataListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class VariantRequestBuilder {
    private final LognexApi api;

    VariantRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Модификаций
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListResponse<Variant> get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/variant").
                list(Variant.class);
    }

    /**
     * Создание новой Модификации
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(Variant newEntity) throws IOException, LognexApiException {
        Variant responseEntity = HttpRequestBuilder.
                path(api, "/entity/variant").
                body(newEntity).
                post(Variant.class);

        newEntity.set(responseEntity);
    }

    /**
     * Получение списка Метаданных Модификаций
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public VariantMetadataListResponse metadata() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/variant/metadata").
                get(VariantMetadataListResponse.class);
    }
}
