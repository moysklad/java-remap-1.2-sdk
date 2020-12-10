package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.State;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InvoiceOutTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        InvoiceOut invoiceOut = new InvoiceOut();
        invoiceOut.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceOut.setVatEnabled(true);
        invoiceOut.setVatIncluded(true);
        invoiceOut.setMoment(LocalDateTime.now());
        invoiceOut.setSum(randomLong(10, 10000));
        invoiceOut.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        invoiceOut.setStore(simpleEntityManager.getMainStore());
        api.entity().invoiceout().create(invoiceOut);

        ListEntity<InvoiceOut> updatedEntitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(invoiceOut.getName(), retrievedEntity.getName());
        assertEquals(invoiceOut.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(invoiceOut.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(invoiceOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(invoiceOut.getSum(), retrievedEntity.getSum());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse response = api.entity().invoiceout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException {
        ListEntity<Attribute> attributes = api.entity().invoiceout().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().invoiceout().states().create(state);

        List<State> retrievedStates = api.entity().invoiceout().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceOut originalInvoiceOut = (InvoiceOut) originalEntity;
        InvoiceOut retrievedInvoiceOut = (InvoiceOut) retrievedEntity;

        assertEquals(originalInvoiceOut.getName(), retrievedInvoiceOut.getName());
        assertEquals(originalInvoiceOut.getOrganization().getMeta().getHref(), retrievedInvoiceOut.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceOut.getAgent().getMeta().getHref(), retrievedInvoiceOut.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceOut originalInvoiceOut = (InvoiceOut) originalEntity;
        InvoiceOut updatedInvoiceOut = (InvoiceOut) updatedEntity;

        assertNotEquals(originalInvoiceOut.getName(), updatedInvoiceOut.getName());
        assertEquals(changedField, updatedInvoiceOut.getName());
        assertEquals(originalInvoiceOut.getOrganization().getMeta().getHref(), updatedInvoiceOut.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceOut.getAgent().getMeta().getHref(), updatedInvoiceOut.getAgent().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().invoiceout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InvoiceOut.class;
    }
}
