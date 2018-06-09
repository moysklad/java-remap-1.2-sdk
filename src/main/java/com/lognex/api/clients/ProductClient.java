package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ProductClient implements GetListEndpoint<ProductEntity>, PostEndpoint<ProductEntity>, DeleteByIdEndpoint<ProductEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/product/";
    }

    ProductClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Товаров
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListEntity<ProductEntity> get() throws IOException, LognexApiException {
        return get(api, ProductEntity.class);
    }

    /**
     * Создание нового Товара
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(ProductEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, ProductEntity.class);
    }

    /**
     * Удаление Товара
     *
     * @param id идентификатор сущности
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void delete(String id) throws IOException, LognexApiException {
        delete(api, id);
    }
}
