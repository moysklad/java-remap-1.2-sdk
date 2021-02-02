package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.endpoints.DeleteByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class EntityGetDeleteTest extends EntityGetTest {
    @Test
    public void deleteTest() throws IOException, ApiClientException {
        doDeleteTest("Name");
        doDeleteByIdTest("Name");
    }

    @SuppressWarnings("unchecked")
    public void doDeleteTest(String filter) throws IOException, ApiClientException {
        MetaEntity createdEntity = simpleEntityManager.createSimple(entityClass(), true);

        try {
            Method method = createdEntity.getClass().getMethod("get" + filter);

            ListEntity<MetaEntity> entitiesList;
            entitiesList = ((GetListEndpoint) entityClient()).get(filterEq(filter.toLowerCase(),
                    (String) method.invoke(createdEntity)));
            assertEquals((Integer) 1, entitiesList.getMeta().getSize());

            simpleEntityManager.removeSimpleFromPool(createdEntity);
            ((DeleteByIdEndpoint) entityClient()).delete(createdEntity.getId());

            entitiesList = ((GetListEndpoint) entityClient()).get(filterEq(filter.toLowerCase(),
                    (String) method.invoke(createdEntity)));
            assertEquals((Integer) 0, entitiesList.getMeta().getSize());

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    @SuppressWarnings("unchecked")
    public void doDeleteByIdTest(String filter) throws IOException, ApiClientException {
        MetaEntity createdEntity = simpleEntityManager.createSimple(entityClass(), true);

        try {
            Method method = createdEntity.getClass().getMethod("get" + filter);

            ListEntity<MetaEntity> entitiesList;
            entitiesList = ((GetListEndpoint) entityClient()).get(filterEq(filter.toLowerCase(),
                    (String) method.invoke(createdEntity)));
            assertEquals((Integer) 1, entitiesList.getMeta().getSize());

            simpleEntityManager.removeSimpleFromPool(createdEntity);
            ((DeleteByIdEndpoint) entityClient()).delete(createdEntity);

            entitiesList = ((GetListEndpoint) entityClient()).get(filterEq(filter.toLowerCase(),
                    (String) method.invoke(createdEntity)));
            assertEquals((Integer) 0, entitiesList.getMeta().getSize());

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }
}