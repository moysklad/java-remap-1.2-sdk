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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().customerorder().metadata().get();

        assertEquals(7, response.getStates().size());
        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CustomerOrderDocumentEntity originalCustomerOrder = (CustomerOrderDocumentEntity) originalEntity;
        CustomerOrderDocumentEntity retrievedCustomerOrder = (CustomerOrderDocumentEntity) retrievedEntity;

        assertEquals(originalCustomerOrder.getName(), retrievedCustomerOrder.getName());
        assertEquals(originalCustomerOrder.getDescription(), retrievedCustomerOrder.getDescription());
        assertEquals(originalCustomerOrder.getSum(), retrievedCustomerOrder.getSum());
        assertEquals(originalCustomerOrder.getMoment(), retrievedCustomerOrder.getMoment());
        assertEquals(originalCustomerOrder.getOrganization().getMeta().getHref(), retrievedCustomerOrder.getOrganization().getMeta().getHref());
        assertEquals(originalCustomerOrder.getAgent().getMeta().getHref(), retrievedCustomerOrder.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CustomerOrderDocumentEntity originalCustomerOrder = (CustomerOrderDocumentEntity) originalEntity;
        CustomerOrderDocumentEntity updatedCustomerOrder = (CustomerOrderDocumentEntity) updatedEntity;

        assertNotEquals(originalCustomerOrder.getName(), updatedCustomerOrder.getName());
        assertEquals(changedField, updatedCustomerOrder.getName());
        assertEquals(originalCustomerOrder.getDescription(), updatedCustomerOrder.getDescription());
        assertEquals(originalCustomerOrder.getSum(), updatedCustomerOrder.getSum());
        assertEquals(originalCustomerOrder.getMoment(), updatedCustomerOrder.getMoment());
        assertEquals(originalCustomerOrder.getOrganization().getMeta().getHref(), updatedCustomerOrder.getOrganization().getMeta().getHref());
        assertEquals(originalCustomerOrder.getAgent().getMeta().getHref(), updatedCustomerOrder.getAgent().getMeta().getHref());
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
