package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CustomerOrderDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity e = new CustomerOrderDocumentEntity();
        e.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().customerorder().post(e);

        ListEntity<CustomerOrderDocumentEntity> updatedEntitiesList = api.entity().customerorder().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CustomerOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity e = createSimpleDocumentCustomerOrder();

        CustomerOrderDocumentEntity retrievedEntity = api.entity().customerorder().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().customerorder().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        CustomerOrderDocumentEntity e = createSimpleDocumentCustomerOrder();

        CustomerOrderDocumentEntity retrievedOriginalEntity = api.entity().customerorder().get(e.getId());
        String name = "customerorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().customerorder().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "customerorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().customerorder().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity e = createSimpleDocumentCustomerOrder();

        ListEntity<CustomerOrderDocumentEntity> entitiesList = api.entity().customerorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().customerorder().delete(e.getId());

        entitiesList = api.entity().customerorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity e = createSimpleDocumentCustomerOrder();

        ListEntity<CustomerOrderDocumentEntity> entitiesList = api.entity().customerorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().customerorder().delete(e);

        entitiesList = api.entity().customerorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().customerorder().metadata().get();

        assertEquals(7, response.getStates().size());
        assertFalse(response.getCreateShared());
    }

    private CustomerOrderDocumentEntity createSimpleDocumentCustomerOrder() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity e = new CustomerOrderDocumentEntity();
        e.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().customerorder().post(e);

        return e;
    }

    private void getAsserts(CustomerOrderDocumentEntity e, CustomerOrderDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
    }

    private void putAsserts(CustomerOrderDocumentEntity e, CustomerOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CustomerOrderDocumentEntity retrievedUpdatedEntity = api.entity().customerorder().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getSum(), retrievedUpdatedEntity.getSum());
        assertEquals(retrievedOriginalEntity.getMoment(), retrievedUpdatedEntity.getMoment());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertNotEquals(retrievedOriginalEntity.getUpdated(), retrievedUpdatedEntity.getUpdated());
        assertEquals(retrievedOriginalEntity.getCreated(), retrievedUpdatedEntity.getCreated());
    }
}
