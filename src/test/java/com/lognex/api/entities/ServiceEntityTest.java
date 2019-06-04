package com.lognex.api.entities;

import com.lognex.api.entities.products.*;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ServiceEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ServiceEntity e = new ServiceEntity();
        e.setName("service_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        PriceEntity minPrice = new PriceEntity();
        minPrice.setValue(randomLong(10, 10000));
        minPrice.setCurrency(getFirstCurrency());
        e.setMinPrice(minPrice);

        api.entity().service().post(e);

        ListEntity<ServiceEntity> updatedEntitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ServiceEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMinPrice().getValue(), retrievedEntity.getMinPrice().getValue());
        assertEquals(e.getMinPrice().getCurrency(), retrievedEntity.getMinPrice().getCurrency());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ServiceEntity e = createSimpleService();

        ServiceEntity retrievedEntity = api.entity().service().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().service().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ServiceEntity e = createSimpleService();

        ServiceEntity retrievedOriginalEntity = api.entity().service().get(e.getId());
        String name = "service_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().service().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "service_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().service().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ServiceEntity e = createSimpleService();

        ListEntity<ServiceEntity> entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().service().delete(e.getId());

        entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ServiceEntity e = createSimpleService();

        ListEntity<ServiceEntity> entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().service().delete(e);

        entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }



    private void getAsserts(ServiceEntity e, ServiceEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ServiceEntity e, ServiceEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ServiceEntity retrievedUpdatedEntity = api.entity().service().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}
