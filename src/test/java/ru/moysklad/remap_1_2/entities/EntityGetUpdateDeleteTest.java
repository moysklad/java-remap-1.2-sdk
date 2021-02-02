package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.responses.MassDeleteResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

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
    public void massUpdateTest() throws IOException, ApiClientException {
        List<MetaEntity> entities = Arrays.asList(
                simpleEntityManager.createSimple(entityClass()),
                simpleEntityManager.createSimple(entityClass())
        );

        MetaEntity origin1 = ((GetByIdEndpoint) entityClient()).get(entities.get(0).getId());

        Object newValue = updateField(getFieldNameToUpdate(), entities.get(0));
        List<MetaEntity> updated = ((MassCreateUpdateEndpoint) entityClient()).createOrUpdate(entities);
        assertEquals(entities.size(), updated.size());
        assertEquals(entities.size(), updated.stream().filter(p -> p.getId() != null).count());

        MetaEntity firstUpdated = updated.stream()
                .filter(p -> p.getId().equals(origin1.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Сущность не найдена среди обновлённых"));
        putAsserts(origin1, firstUpdated, newValue);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).set(updated.get(i));
        }
    }

    @Test
    public void massCreateDeleteTest() throws IOException, ApiClientException {
        EntityClientBase client = entityClient();

        List<MetaEntity> entities = Arrays.asList(
                simpleEntityManager.createSimple(entityClass(), true),
                simpleEntityManager.createSimple(entityClass(), true)
        );
        entities.forEach(simpleEntityManager::removeSimpleFromPool);

        Set<String> entityIds = doMassDelete((MassCreateUpdateDeleteEndpoint) client, entities);
        ((GetListEndpoint<MetaEntity>) entityClient()).get().getRows().stream()
                .filter(item -> entityIds.contains(item.getId()))
                .findAny()
                .ifPresent(id -> fail("Сущность найдена после массового удаления"));

        entities.forEach(entity -> {
            entity.setId(null);
            entity.setMeta(null);
        });
        List<MetaEntity> created = ((MassCreateUpdateEndpoint) client).createOrUpdate(entities);
        assertEquals(entities.size(), created.size());
        assertEquals(entities.size(), created.stream().filter(p -> p.getId() != null).count());

        //подчистка базы
        doMassDelete((MassCreateUpdateDeleteEndpoint) client, created);
    }

    private Set<String> doMassDelete(MassCreateUpdateDeleteEndpoint client, List<MetaEntity> entities) throws IOException, ApiClientException {
        List<MassDeleteResponse> deleteResult = client.delete(entities);
        assertEquals(entities.size(), deleteResult.size());
        assertEquals(0, deleteResult.stream().filter(x -> x.getErrors() != null).count());
        return entities.stream().map(MetaEntity::getId).collect(Collectors.toSet());
    }
}
