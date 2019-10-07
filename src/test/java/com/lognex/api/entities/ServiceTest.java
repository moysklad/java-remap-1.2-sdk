package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.products.*;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ServiceTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Service service = new Service();
        service.setName("service_" + randomString(3) + "_" + new Date().getTime());
        service.setArchived(false);
        service.setDescription(randomString());
        Price minPrice = new Price();
        minPrice.setValue(randomLong(10, 10000));
        minPrice.setCurrency(simpleEntityManager.getFirstCurrency());
        service.setMinPrice(minPrice);
        service.setPaymentItemType(ServicePaymentItemType.WORK);
        service.setTaxSystem(TaxSystem.GENERAL_TAX_SYSTEM);

        api.entity().service().create(service);

        ListEntity<Service> updatedEntitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Service retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(service.getName(), retrievedEntity.getName());
        assertEquals(service.getArchived(), retrievedEntity.getArchived());
        assertEquals(service.getDescription(), retrievedEntity.getDescription());
        assertEquals(service.getMinPrice().getValue(), retrievedEntity.getMinPrice().getValue());
        assertEquals(service.getMinPrice().getCurrency(), retrievedEntity.getMinPrice().getCurrency());
        assertEquals(service.getPaymentItemType(), retrievedEntity.getPaymentItemType());
        assertEquals(service.getTaxSystem(), retrievedEntity.getTaxSystem());
    }

    @Test
    public void paymentItemTypeTest() {
        Service service = simpleEntityManager.createSimple(Service.class);

        Arrays.stream(ServicePaymentItemType.values()).forEach(servicePaymentItemType -> {
            service.setPaymentItemType(servicePaymentItemType);
            try {
                api.entity().service().update(service);
                assertEquals(servicePaymentItemType, service.getPaymentItemType());
            } catch (IOException | ApiClientException e) {
                fail();
            }
        });
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Service originalService = (Service) originalEntity;
        Service retrievedService = (Service) retrievedEntity;

        assertEquals(originalService.getName(), retrievedService.getName());
        assertEquals(originalService.getDescription(), retrievedService.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Service originalService = (Service) originalEntity;
        Service updatedService = (Service) updatedEntity;

        assertNotEquals(originalService.getName(), updatedService.getName());
        assertEquals(changedField, updatedService.getName());
        assertEquals(originalService.getDescription(), updatedService.getDescription());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().service();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Service.class;
    }
}
