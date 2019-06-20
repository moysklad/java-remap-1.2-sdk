package com.lognex.api.entities;

import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.PutByIdEndpoint;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import static org.junit.Assert.fail;

public abstract class EntityGetUpdateDeleteTest extends EntityGetDeleteTest {
    @Test
    public void putTest() throws IOException, LognexApiException {
        doPutTest("Name");
    }

    @SuppressWarnings("unchecked")
    public void doPutTest(String fieldName) throws IOException, LognexApiException {
        MetaEntity createdEntity = simpleEntityFactory.createSimple(entityClass());

        // update by entity
        MetaEntity originalEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        Object changedField = updateField(fieldName, createdEntity);
        ((PutByIdEndpoint) entityClient()).put(createdEntity);
        MetaEntity retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        putAsserts(originalEntity, retrievedEntity, changedField);

        // update by id and entity
        originalEntity.set(retrievedEntity);
        changedField = updateField(fieldName, createdEntity);
        ((PutByIdEndpoint) entityClient()).put(createdEntity.getId(), createdEntity);
        retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        putAsserts(originalEntity, retrievedEntity, changedField);
    }

    private Object updateField(String fieldName, MetaEntity entityToUpdate) {
        Object changedField = null;
        try {
            Class<?> clazz = entityToUpdate.getClass().getMethod("get" + fieldName).getReturnType();
            if (clazz == String.class) {
                changedField = "field_" + randomString(3) + "_" + new Date().getTime();
                entityToUpdate.getClass().getMethod("set" + fieldName, clazz).invoke(entityToUpdate, changedField);
            } else {
                fail("Изменяемое поле должно быть одним из типов: String");
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
        return changedField;
    }

    protected abstract void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField);
}
