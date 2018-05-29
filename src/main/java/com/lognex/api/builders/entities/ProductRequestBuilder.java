package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Product;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ProductRequestBuilder {
    private final LognexApi api;

    public ProductRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получить список всех Товаров
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public ListResponse<Product> get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/product").
                getListResponse(Product.class);
    }

    /**
     * Создать новый Товар
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public void post(Product newEntity) throws IOException, LognexApiException {
        Product responseEntity = HttpRequestBuilder.
                path(api, "/entity/product").
                body(newEntity).
                post(Product.class);

        newEntity.set(responseEntity);
    }

    /**
     * Удаление Товара
     *
     * @param id идентификатор сущности
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public void delete(String id) throws IOException, LognexApiException {
        HttpRequestBuilder.
                path(api, "/entity/product/" + id).
                delete();
    }
}
