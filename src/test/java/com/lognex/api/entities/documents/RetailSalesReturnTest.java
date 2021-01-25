package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.State;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailSalesReturnTest extends EntityGetUpdateDeleteTest {
    @Ignore
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailSalesReturn retailSalesReturn = new RetailSalesReturn();
        retailSalesReturn.setName("retailsalesreturn_" + randomString(3) + "_" + new Date().getTime());
        retailSalesReturn.setDescription(randomString());
        retailSalesReturn.setVatEnabled(true);
        retailSalesReturn.setVatIncluded(true);
        retailSalesReturn.setMoment(LocalDateTime.now());

        retailSalesReturn.setOrganization(simpleEntityManager.getOwnOrganization());
        retailSalesReturn.setAgent(simpleEntityManager.createSimpleCounterparty());
        retailSalesReturn.setStore(simpleEntityManager.getMainStore());

        api.entity().retailsalesreturn().create(retailSalesReturn);

        ListEntity<RetailSalesReturn> updatedEntitiesList = api.entity().retailsalesreturn().get(filterEq("name", retailSalesReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailSalesReturn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailSalesReturn.getName(), retrievedEntity.getName());
        assertEquals(retailSalesReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailSalesReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(retailSalesReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(retailSalesReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailSalesReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailSalesReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(retailSalesReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().retailsalesreturn().states().create(state);

        List<State> retrievedStates = api.entity().retailsalesreturn().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Ignore
    @Test
    @Override
    public void putTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void deleteTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void getTest() throws IOException, ApiClientException {
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse response = api.entity().retailsalesreturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().retailsalesreturn().metadataAttributes();
        assertNotNull(attributes);
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SalesReturn originalSalesReturn = (SalesReturn) originalEntity;
        SalesReturn retrievedSalesReturn = (SalesReturn) retrievedEntity;

        assertEquals(originalSalesReturn.getName(), retrievedSalesReturn.getName());
        assertEquals(originalSalesReturn.getDescription(), retrievedSalesReturn.getDescription());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), retrievedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), retrievedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), retrievedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        SalesReturn originalSalesReturn = (SalesReturn) originalEntity;
        SalesReturn updatedSalesReturn = (SalesReturn) updatedEntity;

        assertNotEquals(originalSalesReturn.getName(), updatedSalesReturn.getName());
        assertEquals(changedField, updatedSalesReturn.getName());
        assertEquals(originalSalesReturn.getDescription(), updatedSalesReturn.getDescription());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), updatedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), updatedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), updatedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().salesreturn();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return SalesReturn.class;
    }
}
