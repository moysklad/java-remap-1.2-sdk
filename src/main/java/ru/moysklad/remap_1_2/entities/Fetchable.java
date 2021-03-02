package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

/**
 * Интерфейс, определяющий, может ли данный объект дозапрашивать у API данные
 * с помощью ссылки в метаданных
 */
public interface Fetchable {
    default void fetch(ApiClient api) throws IOException, ApiClientException {
        if (this instanceof MetaEntity) {
            MetaEntity current = (MetaEntity) this;
            MetaEntity fetched = HttpRequestExecutor.url(api, current.getMeta().getHref()).get(current.getClass());
            current.set(fetched);
        }
    }
}
