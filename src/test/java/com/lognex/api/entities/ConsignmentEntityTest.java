package com.lognex.api.entities;

import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ConsignmentEntityTest extends EntityTestBase {
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

    @Test
    public void getTest() throws IOException, LognexApiException {
        ConsignmentEntity consignment = simpleEntityFactory.createSimpleConsignment();

        ConsignmentEntity retrievedEntity = api.entity().consignment().get(consignment.getId());
        getAsserts(consignment, retrievedEntity);

        retrievedEntity = api.entity().consignment().get(consignment);
        getAsserts(consignment, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ConsignmentEntity consignment = simpleEntityFactory.createSimpleConsignment();

        ConsignmentEntity retrievedOriginalEntity = api.entity().consignment().get(consignment.getId());
        String label = "consignment_" + randomString(3) + "_" + new Date().getTime();
        consignment.setLabel(label);
        api.entity().consignment().put(consignment.getId(), consignment);
        putAsserts(consignment, retrievedOriginalEntity, label);

        consignment = simpleEntityFactory.createSimpleConsignment();
        retrievedOriginalEntity = api.entity().consignment().get(consignment.getId());
        label = "consignment_" + randomString(3) + "_" + new Date().getTime();
        consignment.setLabel(label);
        api.entity().consignment().put(consignment);
        putAsserts(consignment, retrievedOriginalEntity, label);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ConsignmentEntity consignment = simpleEntityFactory.createSimpleConsignment();

        ListEntity<ConsignmentEntity> entitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().consignment().delete(consignment.getId());

        entitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ConsignmentEntity consignment = simpleEntityFactory.createSimpleConsignment();

        ListEntity<ConsignmentEntity> entitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().consignment().delete(consignment);

        entitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(ConsignmentEntity consignment, ConsignmentEntity retrievedEntity) {
        assertEquals(consignment.getName(), retrievedEntity.getName());
        assertEquals(consignment.getLabel(), retrievedEntity.getLabel());
        assertEquals(consignment.getAssortment(), retrievedEntity.getAssortment());
    }

    private void putAsserts(ConsignmentEntity consignment, ConsignmentEntity retrievedOriginalEntity, String label) throws IOException, LognexApiException {
        ConsignmentEntity retrievedUpdatedEntity = api.entity().consignment().get(consignment.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertNotEquals(retrievedOriginalEntity.getLabel(), retrievedUpdatedEntity.getLabel());
        assertEquals(label, retrievedUpdatedEntity.getLabel());
        assertEquals(retrievedOriginalEntity.getAssortment(), retrievedUpdatedEntity.getAssortment());
    }
}

