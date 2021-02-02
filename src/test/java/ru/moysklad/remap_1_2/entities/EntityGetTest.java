package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

public abstract class EntityGetTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        doGetTest();
    }

    public void doGetTest() throws IOException, ApiClientException {
        MetaEntity createdEntity = simpleEntityManager.createSimple(entityClass());

        MetaEntity retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        getAsserts(createdEntity, retrievedEntity);

        retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity);
        getAsserts(createdEntity, retrievedEntity);
    }

    protected abstract void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity);
}