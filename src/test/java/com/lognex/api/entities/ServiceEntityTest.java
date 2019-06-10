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
        ServiceEntity service = new ServiceEntity();
        service.setName("service_" + randomString(3) + "_" + new Date().getTime());
        service.setArchived(false);
        service.setDescription(randomString());
        PriceEntity minPrice = new PriceEntity();
        minPrice.setValue(randomLong(10, 10000));
        minPrice.setCurrency(simpleEntityFactory.getFirstCurrency());
        service.setMinPrice(minPrice);

        api.entity().service().post(service);

        ListEntity<ServiceEntity> updatedEntitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ServiceEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(service.getName(), retrievedEntity.getName());
        assertEquals(service.getArchived(), retrievedEntity.getArchived());
        assertEquals(service.getDescription(), retrievedEntity.getDescription());
        assertEquals(service.getMinPrice().getValue(), retrievedEntity.getMinPrice().getValue());
        assertEquals(service.getMinPrice().getCurrency(), retrievedEntity.getMinPrice().getCurrency());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ServiceEntity service = simpleEntityFactory.createSimpleService();

        ServiceEntity retrievedEntity = api.entity().service().get(service.getId());
        getAsserts(service, retrievedEntity);

        retrievedEntity = api.entity().service().get(service);
        getAsserts(service, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ServiceEntity service = simpleEntityFactory.createSimpleService();

        ServiceEntity retrievedOriginalEntity = api.entity().service().get(service.getId());
        String name = "service_" + randomString(3) + "_" + new Date().getTime();
        service.setName(name);
        api.entity().service().put(service.getId(), service);
        putAsserts(service, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(service);

        name = "service_" + randomString(3) + "_" + new Date().getTime();
        service.setName(name);
        api.entity().service().put(service);
        putAsserts(service, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ServiceEntity service = simpleEntityFactory.createSimpleService();

        ListEntity<ServiceEntity> entitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().service().delete(service.getId());

        entitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ServiceEntity service = simpleEntityFactory.createSimpleService();

        ListEntity<ServiceEntity> entitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().service().delete(service);

        entitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }



    private void getAsserts(ServiceEntity service, ServiceEntity retrievedEntity) {
        assertEquals(service.getName(), retrievedEntity.getName());
        assertEquals(service.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ServiceEntity service, ServiceEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ServiceEntity retrievedUpdatedEntity = api.entity().service().get(service.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}
