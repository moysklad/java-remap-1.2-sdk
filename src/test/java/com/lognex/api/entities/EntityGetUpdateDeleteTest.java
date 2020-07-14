package com.lognex.api.entities;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.responses.MassDeleteResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class EntityGetUpdateDeleteTest extends EntityGetDeleteTest {
    protected String getFieldNameToUpdate() {
        return "Name";
    }

    @Test
    public void putTest() throws IOException, ApiClientException {
        doPutTest(getFieldNameToUpdate());
    }

    @SuppressWarnings("unchecked")
    public void doPutTest(String fieldName) throws IOException, ApiClientException {
        MetaEntity createdEntity = simpleEntityManager.createSimple(entityClass());

        // update by entity
        MetaEntity originalEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        Object changedField = updateField(fieldName, createdEntity);
        ((PutByIdEndpoint) entityClient()).update(createdEntity);
        MetaEntity retrievedEntity = ((GetByIdEndpoint) entityClient()).get(createdEntity.getId());
        putAsserts(originalEntity, retrievedEntity, changedField);

        // update by id and entity
        originalEntity.set(retrievedEntity);
        changedField = updateField(fieldName, createdEntity);
        ((PutByIdEndpoint) entityClient()).update(createdEntity.getId(), createdEntity);
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

    @Test
    public void massCreateUpdateDelete() throws IOException, ApiClientException {
        MetaEntity entity1 = simpleEntityManager.createSimple(entityClass(), true);
        MetaEntity entity2 = simpleEntityManager.createSimple(entityClass(), true);
        MetaEntity entity3 = simpleEntityManager.createSimple(entityClass(), true);

        // отсоединяем от базы, чтобы проверить массовое создание
        Stream.of(entity1, entity2, entity3).forEach(entity -> entity.setId(null));

        MassCreateUpdateEndpoint client = (MassCreateUpdateEndpoint) entityClient();

        List<MetaEntity> created = client.createOrUpdate(Arrays.asList(entity1, entity2));
        assertEquals(2, created.size());
        assertEquals(2, created.stream().filter(p -> p.getId() != null).count());

        Object newValue = updateField(getFieldNameToUpdate(), created.get(0));
        List<MetaEntity> updated = client.createOrUpdate(Arrays.asList(created.get(0), created.get(1), entity3));
        assertEquals(3, updated.size());
        assertEquals(3, updated.stream().filter(p -> p.getId() != null).count());

        MetaEntity firstUpdated = updated.stream()
                .filter(p -> p.getId().equals(created.get(0).getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Сущность не найдена среди обновлённых"));
        entity1.setId(created.get(0).getId());
        putAsserts(entity1, firstUpdated, newValue);

        if (client instanceof MassCreateUpdateDeleteEndpoint) {
            List<MassDeleteResponse> deleteResult = ((MassCreateUpdateDeleteEndpoint)client).delete(updated);
            assertEquals(updated.size(), deleteResult.size());
            Set<String> entityIds = updated.stream().map(MetaEntity::getId).collect(Collectors.toSet());
            ((GetListEndpoint<MetaEntity>) entityClient()).get().getRows().stream()
                    .filter(item -> entityIds.contains(item.getId()))
                    .findAny()
                    .ifPresent(id -> fail("Сущность найдена после массового удаления"));
        }
    }
}
