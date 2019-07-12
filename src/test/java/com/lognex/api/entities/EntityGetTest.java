package com.lognex.api.entities;

import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;

public abstract class EntityGetTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, LognexApiException {
        doGetTest();
    }

    public void doGetTest() throws IOException, LognexApiException {
        MetaEntity createdEntity = simpleEntityManager.createSimple(entityClass());

        MetaEntity retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        getAsserts(createdEntity, retrievedEntity);

        retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity);
        getAsserts(createdEntity, retrievedEntity);
    }

    protected abstract void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity);
}