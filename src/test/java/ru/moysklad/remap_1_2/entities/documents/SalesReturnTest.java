package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class SalesReturnTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = new SalesReturn();
        salesReturn.setName("salesreturn_" + randomString(3) + "_" + new Date().getTime());
        salesReturn.setDescription(randomString());
        salesReturn.setVatEnabled(true);
        salesReturn.setVatIncluded(true);
        salesReturn.setMoment(LocalDateTime.now());
        Organization organization = simpleEntityManager.getOwnOrganization();
        salesReturn.setOrganization(organization);
        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        salesReturn.setAgent(agent);
        Store mainStore = simpleEntityManager.getMainStore();
        salesReturn.setStore(mainStore);

        Demand demand = new Demand();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setOrganization(organization);
        demand.setAgent(agent);
        demand.setStore(mainStore);
        
        api.entity().demand().create(demand);
        salesReturn.setDemand(demand);

        api.entity().salesreturn().create(salesReturn);

        ListEntity<SalesReturn> updatedEntitiesList = api.entity().salesreturn().get(filterEq("name", salesReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        SalesReturn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(salesReturn.getName(), retrievedEntity.getName());
        assertEquals(salesReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(salesReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(salesReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(salesReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(salesReturn.getDemand().getMeta().getHref(), retrievedEntity.getDemand().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().salesreturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().salesreturn().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = api.entity().salesreturn().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", salesReturn.getName());
        assertTrue(salesReturn.getVatEnabled());
        assertTrue(salesReturn.getVatIncluded());
        assertEquals(Long.valueOf(0), salesReturn.getSum());
        assertFalse(salesReturn.getShared());
        assertTrue(salesReturn.getApplicable());
        assertFalse(salesReturn.getPublished());
        assertFalse(salesReturn.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, salesReturn.getMoment()) < 1000);

        assertEquals(salesReturn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
    }

    @Test
    public void newByDemandTest() throws IOException, ApiClientException {
        Demand demand = simpleEntityManager.createSimple(Demand.class);

        SalesReturn salesReturn = api.entity().salesreturn().templateDocument("demand", demand);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", salesReturn.getName());
        assertEquals(demand.getVatEnabled(), salesReturn.getVatEnabled());
        assertEquals(demand.getVatIncluded(), salesReturn.getVatIncluded());
        assertEquals(demand.getPayedSum(), salesReturn.getPayedSum());
        assertEquals(demand.getSum(), salesReturn.getSum());
        assertFalse(salesReturn.getShared());
        assertTrue(salesReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, salesReturn.getMoment()) < 1000);
        assertEquals(demand.getMeta().getHref(), salesReturn.getDemand().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), salesReturn.getAgent().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), salesReturn.getStore().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), salesReturn.getOrganization().getMeta().getHref());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().salesreturn().states().create(state);

        List<State> retrievedStates = api.entity().salesreturn().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SalesReturn originalSalesReturn = (SalesReturn) originalEntity;
        SalesReturn retrievedSalesReturn = (SalesReturn) retrievedEntity;

        assertEquals(originalSalesReturn.getName(), retrievedSalesReturn.getName());
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
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), updatedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), updatedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), updatedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().salesreturn();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SalesReturn.class;
    }
}
