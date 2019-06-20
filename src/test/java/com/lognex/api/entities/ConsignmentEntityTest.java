package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ConsignmentEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ConsignmentEntity consignment = new ConsignmentEntity();
        consignment.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());

        ProductEntity product = simpleEntityFactory.createSimpleProduct();
        consignment.setAssortment(product);

        api.entity().consignment().post(consignment);

        ListEntity<ConsignmentEntity> updatedEntitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ConsignmentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(consignment.getName(), retrievedEntity.getName());
        assertEquals(consignment.getLabel(), retrievedEntity.getLabel());
        assertEquals(consignment.getAssortment(), retrievedEntity.getAssortment());
    }

    @Override
    @Test
    public void putTest() throws IOException, LognexApiException {
        doPutTest("Label");
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ConsignmentEntity originalConsignment = (ConsignmentEntity) originalEntity;
        ConsignmentEntity updatedConsignment = (ConsignmentEntity) updatedEntity;

        assertNotEquals(originalConsignment.getName(), updatedConsignment.getName());
        assertNotEquals(originalConsignment.getLabel(), updatedConsignment.getLabel());
        assertEquals(changedField, updatedConsignment.getLabel());
        assertEquals(originalConsignment.getAssortment(), updatedConsignment.getAssortment());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ConsignmentEntity originalConsignment = (ConsignmentEntity) originalEntity;
        ConsignmentEntity retrievedConsignment = (ConsignmentEntity) retrievedEntity;

        assertEquals(originalConsignment.getName(), retrievedConsignment.getName());
        assertEquals(originalConsignment.getLabel(), retrievedConsignment.getLabel());
        assertEquals(originalConsignment.getAssortment(), retrievedConsignment.getAssortment());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().consignment();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ConsignmentEntity.class;
    }
}

