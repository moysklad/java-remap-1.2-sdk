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
        ConsignmentEntity e = new ConsignmentEntity();
        e.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        e.setAssortment(product);

        api.entity().consignment().post(e);

        ListEntity<ConsignmentEntity> updatedEntitiesList = api.entity().consignment().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ConsignmentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getLabel(), retrievedEntity.getLabel());
        assertEquals(e.getAssortment(), retrievedEntity.getAssortment());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ConsignmentEntity e = createSimpleConsignment();

        ConsignmentEntity retrievedEntity = api.entity().consignment().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().consignment().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ConsignmentEntity e = createSimpleConsignment();

        ConsignmentEntity retrievedOriginalEntity = api.entity().consignment().get(e.getId());
        String label = "consignment_" + randomString(3) + "_" + new Date().getTime();
        e.setLabel(label);
        api.entity().consignment().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, label);

        e = createSimpleConsignment();
        retrievedOriginalEntity = api.entity().consignment().get(e.getId());
        label = "consignment_" + randomString(3) + "_" + new Date().getTime();
        e.setLabel(label);
        api.entity().consignment().put(e);
        putAsserts(e, retrievedOriginalEntity, label);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ConsignmentEntity e = createSimpleConsignment();

        ListEntity<ConsignmentEntity> entitiesList = api.entity().consignment().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().consignment().delete(e.getId());

        entitiesList = api.entity().consignment().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ConsignmentEntity e = createSimpleConsignment();

        ListEntity<ConsignmentEntity> entitiesList = api.entity().consignment().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().consignment().delete(e);

        entitiesList = api.entity().consignment().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    private ConsignmentEntity createSimpleConsignment() throws IOException, LognexApiException {
        ConsignmentEntity e = new ConsignmentEntity();
        e.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        e.setAssortment(product);

        api.entity().consignment().post(e);

        return e;
    }

    private void getAsserts(ConsignmentEntity e, ConsignmentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getLabel(), retrievedEntity.getLabel());
        assertEquals(e.getAssortment(), retrievedEntity.getAssortment());
    }

    private void putAsserts(ConsignmentEntity e, ConsignmentEntity retrievedOriginalEntity, String label) throws IOException, LognexApiException {
        ConsignmentEntity retrievedUpdatedEntity = api.entity().consignment().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertNotEquals(retrievedOriginalEntity.getLabel(), retrievedUpdatedEntity.getLabel());
        assertEquals(label, retrievedUpdatedEntity.getLabel());
        // Баг: не обновляется поле updated со стороны API
        assertEquals(retrievedOriginalEntity.getAssortment(), retrievedUpdatedEntity.getAssortment());
    }
}

