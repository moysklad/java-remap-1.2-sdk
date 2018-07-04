package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.ProductFolderEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ProductFolderClient implements GetListEndpoint<ProductFolderEntity>, PostEndpoint<ProductFolderEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/productfolder/";
    }

    ProductFolderClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Групп Товаров
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<ProductFolderEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, ProductFolderEntity.class, expand);
    }

    /**
     * Создание нового Группы Товаров
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ProductFolderEntity post(ProductFolderEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, ProductFolderEntity.class);
    }
}
