package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

/**
 * Интерфейс, определяющий, может ли данный объект дозапрашивать у API данные
 * с помощью ссылки в метаданных
 */
public interface Fetchable {
    default void fetch(LognexApi api) throws IOException, LognexApiException {
        if (this instanceof MetaEntity) {
            MetaEntity current = (MetaEntity) this;
            MetaEntity fetched = HttpRequestExecutor.url(api, current.getMeta().getHref()).get(current.getClass());
            current.set(fetched);
        }
    }
}
