package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ConsignmentTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Consignment consignment = new Consignment();
        consignment.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());
        consignment.setDescription(randomString());
        consignment.setCode(randomString());

        Product product = simpleEntityManager.createSimple(Product.class);
        consignment.setAssortment(product);

        api.entity().consignment().create(consignment);

        ListEntity<Consignment> updatedEntitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Consignment retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(consignment.getName(), retrievedEntity.getName());
        assertEquals(consignment.getLabel(), retrievedEntity.getLabel());
        assertEquals(consignment.getDescription(), retrievedEntity.getDescription());
        assertEquals(consignment.getCode(), retrievedEntity.getCode());
        assertEquals(consignment.getAssortment(), retrievedEntity.getAssortment());
    }

    @Override
    protected String getFieldNameToUpdate() {
        return "Label";
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Consignment originalConsignment = (Consignment) originalEntity;
        Consignment updatedConsignment = (Consignment) updatedEntity;

        assertNotEquals(originalConsignment.getName(), updatedConsignment.getName());
        assertNotEquals(originalConsignment.getLabel(), updatedConsignment.getLabel());
        assertEquals(changedField, updatedConsignment.getLabel());
        assertEquals(originalConsignment.getAssortment(), updatedConsignment.getAssortment());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Consignment originalConsignment = (Consignment) originalEntity;
        Consignment retrievedConsignment = (Consignment) retrievedEntity;

        assertEquals(originalConsignment.getName(), retrievedConsignment.getName());
        assertEquals(originalConsignment.getLabel(), retrievedConsignment.getLabel());
        assertEquals(originalConsignment.getAssortment(), retrievedConsignment.getAssortment());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().consignment();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Consignment.class;
    }
}

