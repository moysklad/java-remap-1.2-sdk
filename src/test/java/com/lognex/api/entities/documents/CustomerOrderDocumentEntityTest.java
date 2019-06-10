package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CustomerOrderDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = new CustomerOrderDocumentEntity();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        customerOrder.setDescription(randomString());
        customerOrder.setOrganization(simpleEntityFactory.getOwnOrganization());
        customerOrder.setAgent(simpleEntityFactory.createSimpleCounterparty());

        api.entity().customerorder().post(customerOrder);

        ListEntity<CustomerOrderDocumentEntity> updatedEntitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CustomerOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(customerOrder.getName(), retrievedEntity.getName());
        assertEquals(customerOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(customerOrder.getSum(), retrievedEntity.getSum());
        assertEquals(customerOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        CustomerOrderDocumentEntity retrievedEntity = api.entity().customerorder().get(customerOrder.getId());
        getAsserts(customerOrder, retrievedEntity);

        retrievedEntity = api.entity().customerorder().get(customerOrder);
        getAsserts(customerOrder, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        CustomerOrderDocumentEntity retrievedOriginalEntity = api.entity().customerorder().get(customerOrder.getId());
        String name = "customerorder_" + randomString(3) + "_" + new Date().getTime();
        customerOrder.setName(name);
        api.entity().customerorder().put(customerOrder.getId(), customerOrder);
        putAsserts(customerOrder, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(customerOrder);

        name = "customerorder_" + randomString(3) + "_" + new Date().getTime();
        customerOrder.setName(name);
        api.entity().customerorder().put(customerOrder);
        putAsserts(customerOrder, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        ListEntity<CustomerOrderDocumentEntity> entitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().customerorder().delete(customerOrder.getId());

        entitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        ListEntity<CustomerOrderDocumentEntity> entitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().customerorder().delete(customerOrder);

        entitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().customerorder().metadata().get();

        assertEquals(7, response.getStates().size());
        assertFalse(response.getCreateShared());
    }

    private void getAsserts(CustomerOrderDocumentEntity customerOrder, CustomerOrderDocumentEntity retrievedEntity) {
        assertEquals(customerOrder.getName(), retrievedEntity.getName());
        assertEquals(customerOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(customerOrder.getSum(), retrievedEntity.getSum());
        assertEquals(customerOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(CustomerOrderDocumentEntity customerOrder, CustomerOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CustomerOrderDocumentEntity retrievedUpdatedEntity = api.entity().customerorder().get(customerOrder.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getSum(), retrievedUpdatedEntity.getSum());
        assertEquals(retrievedOriginalEntity.getMoment(), retrievedUpdatedEntity.getMoment());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().customerorder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CustomerOrderDocumentEntity.class;
    }
}
