package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.File;
import java.io.IOException;

public interface FileTestBase<T extends MetaEntity> extends IEntityTestBase {
    default File getFile(String relativePath) {
        String path = this
                .getClass()
                .getClassLoader()
                .getResource(relativePath)
                .getPath();
        return new File(path);
    }

    default ApiParam expandFile() {
        return new ApiParam(ApiParam.Type.expand) {
            @Override
            protected String render(String host) {
                return "files";
            }
        };
    }

    default T generateInstance() {
        return (T) getSimpleEntityManager().createSimple(entityClass(), true);
    }

    default T updateInstance(T entity) throws IOException, ApiClientException {
        ((PutByIdEndpoint<T>) entityClient()).update(entity);
        return ((GetByIdEndpoint<T>) entityClient()).get(entity.getId());
    }

    default T getInstance(String entityId, ApiParam... params) throws IOException, ApiClientException {
        return ((GetByIdEndpoint<T>) entityClient()).get(entityId, params);
    }
}
