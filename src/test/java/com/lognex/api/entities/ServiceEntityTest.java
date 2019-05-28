package com.lognex.api.entities;

import com.lognex.api.entities.products.*;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        ListEntity<CurrencyEntity> currencyEntityList = api.entity().currency().get();
        assertNotEquals(0, currencyEntityList.getRows().size());
        minPrice.setCurrency(currencyEntityList.getRows().get(0));
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
        Thread.sleep(500);
        api.entity().service().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "service_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().service().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ServiceEntity e = createSimpleService();

        ListEntity<ServiceEntity> entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().service().delete(e.getId());

        entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ServiceEntity e = createSimpleService();

        ListEntity<ServiceEntity> entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().service().delete(e);

        entitiesList = api.entity().service().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    private ServiceEntity createSimpleService() throws IOException, LognexApiException {
        ServiceEntity e = new ServiceEntity();
        e.setName("service_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        BarcodeEntity barcode = new BarcodeEntity();
        barcode.setType(BarcodeEntity.Type.ean8);
        barcode.setBarcode(randomString(8));
        e.setBarcodes(new ArrayList<>(Arrays.asList(barcode)));

        api.entity().service().post(e);

        return e;
    }

    private void getAsserts(ServiceEntity e, ServiceEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getBarcodes(), retrievedEntity.getBarcodes());
    }

    private void putAsserts(ServiceEntity e, ServiceEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ServiceEntity retrievedUpdatedEntity = api.entity().service().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getArchived(), retrievedUpdatedEntity.getArchived());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getBarcodes(), retrievedUpdatedEntity.getBarcodes());
    }
}
