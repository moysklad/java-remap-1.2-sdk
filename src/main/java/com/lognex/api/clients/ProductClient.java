package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ProductClient implements
        GetListEndpoint<ProductEntity>,
        PostEndpoint<ProductEntity>,
        DeleteByIdEndpoint<ProductEntity> {
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
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<ProductEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, ProductEntity.class, expand);
    }

    /**
     * Создание нового Товара
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ProductEntity post(ProductEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, ProductEntity.class);
    }

    /**
     * Удаление Товара
     *
     * @param id идентификатор сущности
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public void delete(String id) throws IOException, LognexApiException {
        delete(api, id);
    }
}
