//package com.lognex.api.entities.documents;
//
//import com.lognex.api.entities.EntityTestBase;
//import com.lognex.api.entities.StoreEntity;
//import com.lognex.api.entities.agents.CounterpartyEntity;
//import com.lognex.api.entities.agents.OrganizationEntity;
//import com.lognex.api.responses.ListEntity;
//import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
//import com.lognex.api.utils.LognexApiException;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//import static com.lognex.api.utils.params.FilterParam.filterEq;
//import static org.junit.Assert.*;
//
//public class RetailShiftDocumentEntityTest extends EntityTestBase {
//    @Test
//    public void createTest() throws IOException, LognexApiException {
//        RetailShiftDocumentEntity e = new RetailShiftDocumentEntity();
//        e.setName("retailshift_" + randomString(3) + "_" + new Date().getTime());
//        e.setDescription(randomString());
//        e.setVatEnabled(true);
//        e.setVatIncluded(true);
//        e.setMoment(LocalDateTime.now());
//
//        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
//        assertNotEquals(0, orgList.getRows().size());
//        e.setOrganization(orgList.getRows().get(0));
//
//        CounterpartyEntity agent = new CounterpartyEntity();
//        agent.setName(randomString());
//        api.entity().counterparty().post(agent);
//        e.setAgent(agent);
//
//        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
//        assertEquals(1, store.getRows().size());
//        e.setStore(store.getRows().get(0));
//
//        api.entity().retailshift().post(e);
//
//        ListEntity<RetailShiftDocumentEntity> updatedEntitiesList = api.entity().retailshift().get(filterEq("name", e.getName()));
//        assertEquals(1, updatedEntitiesList.getRows().size());
//
//        RetailShiftDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
//        assertEquals(e.getName(), retrievedEntity.getName());
//        assertEquals(e.getDescription(), retrievedEntity.getDescription());
//        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
//        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
//        assertEquals(e.getMoment(), retrievedEntity.getMoment());
//        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
//        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
//        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
//    }
//
//    @Test
//    public void getTest() throws IOException, LognexApiException {
//        RetailShiftDocumentEntity e = createSimpleDocumentRetailShift();
//
//        RetailShiftDocumentEntity retrievedEntity = api.entity().retailshift().get(e.getId());
//        getAsserts(e, retrievedEntity);
//
//        retrievedEntity = api.entity().retailshift().get(e);
//        getAsserts(e, retrievedEntity);
//    }
//
//    @Test
//    public void putTest() throws IOException, LognexApiException, InterruptedException {
//        RetailShiftDocumentEntity e = createSimpleDocumentRetailShift();
//
//        RetailShiftDocumentEntity retrievedOriginalEntity = api.entity().retailshift().get(e.getId());
//        String name = "retailshift_" + randomString(3) + "_" + new Date().getTime();
//        e.setName(name);
//        api.entity().retailshift().put(e.getId(), e);
//        putAsserts(e, retrievedOriginalEntity, name);
//
//        retrievedOriginalEntity.set(e);
//
//        name = "retailshift_" + randomString(3) + "_" + new Date().getTime();
//        e.setName(name);
//        api.entity().retailshift().put(e);
//        putAsserts(e, retrievedOriginalEntity, name);
//    }
//
//    @Test
//    public void deleteTest() throws IOException, LognexApiException {
//        RetailShiftDocumentEntity e = createSimpleDocumentRetailShift();
//
//        ListEntity<RetailShiftDocumentEntity> entitiesList = api.entity().retailshift().get(filterEq("name", e.getName()));
//        assertEquals((Integer) 1, entitiesList.getMeta().getSize());
//
//        api.entity().retailshift().delete(e.getId());
//
//        entitiesList = api.entity().retailshift().get(filterEq("name", e.getName()));
//        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
//    }
//
//    @Test
//    public void deleteByIdTest() throws IOException, LognexApiException {
//        RetailShiftDocumentEntity e = createSimpleDocumentRetailShift();
//
//        ListEntity<RetailShiftDocumentEntity> entitiesList = api.entity().retailshift().get(filterEq("name", e.getName()));
//        assertEquals((Integer) 1, entitiesList.getMeta().getSize());
//
//        api.entity().retailshift().delete(e);
//
//        entitiesList = api.entity().retailshift().get(filterEq("name", e.getName()));
//        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
//    }
//
//    @Test
//    public void metadataTest() throws IOException, LognexApiException {
//        MetadataAttributeSharedStatesResponse response = api.entity().retailshift().metadata().get();
//
//        assertFalse(response.getCreateShared());
//    }
//
//    // не работает, так как post не указывает заголовок Content-type при пустом теле
//    @Test
//    public void newTest() throws IOException, LognexApiException {
//        LocalDateTime time = LocalDateTime.now().withNano(0);
//        RetailShiftDocumentEntity e = api.entity().retailshift().newDocument();
//
//        assertEquals("", e.getName());
//        assertTrue(e.getVatEnabled());
//        assertTrue(e.getVatIncluded());
//        assertEquals(Long.valueOf(0), e.getPayedSum());
//        assertEquals(Long.valueOf(0), e.getSum());
//        assertFalse(e.getShared());
//        assertTrue(e.getApplicable());
//
//        ListEntity<OrganizationEntity> org = api.entity().organization().get(filterEq("name", "Администратор"));
//        assertEquals(1, org.getRows().size());
//        assertEquals(e.getOrganization().getMeta().getHref(), org.getRows().get(0).getMeta().getHref());
//
//        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
//        assertEquals(1, store.getRows().size());
//        assertEquals(e.getStore().getMeta().getHref(), store.getRows().get(0).getMeta().getHref());
//    }
//
//    @Test
//    public void newByCustomerOrderTest() throws IOException, LognexApiException {
//
//        CustomerOrderDocumentEntity customerOrder = new CustomerOrderDocumentEntity();
//        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
//        customerOrder.setDescription(randomString());
//
//        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
//        assertNotEquals(0, orgList.getRows().size());
//        customerOrder.setOrganization(orgList.getRows().get(0));
//
//        CounterpartyEntity agent = new CounterpartyEntity();
//        agent.setName(randomString());
//        api.entity().counterparty().post(agent);
//        customerOrder.setAgent(agent);
//        customerOrder.setMoment(LocalDateTime.now().withNano(0));
//
//        api.entity().customerorder().post(customerOrder);
//
//        LocalDateTime time = LocalDateTime.now().withNano(0);
//        RetailShiftDocumentEntity e = api.entity().retailshift().newDocument(customerOrder);
//
//        assertEquals("", e.getName());
//        assertEquals(customerOrder.getVatEnabled(), e.getVatEnabled());
//        assertEquals(customerOrder.getVatIncluded(), e.getVatIncluded());
//        assertEquals(customerOrder.getPayedSum(), e.getPayedSum());
//        assertEquals(customerOrder.getSum(), e.getSum());
//        assertEquals(customerOrder.getShared(), e.getShared());
//        assertEquals(customerOrder.getApplicable(), e.getApplicable());
//        // Баг? при создании по шаблону moment = created, created = null
//        //assertEquals(customerOrder.getMoment(), e.getMoment());
//        assertEquals(customerOrder.getMeta().getHref(), e.getCustomerOrder().getMeta().getHref());
//    }
//
//    @Test
//    public void newByInvoicesOutTest() throws IOException, LognexApiException {
//        //нужно создать объект с полем invoicesOut
//    }
//
//    private RetailShiftDocumentEntity createSimpleDocumentRetailShift() throws IOException, LognexApiException {
//        RetailShiftDocumentEntity e = new RetailShiftDocumentEntity();
//        e.setName("retailshift_" + randomString(3) + "_" + new Date().getTime());
//        e.setDescription(randomString());
//
//        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
//        assertNotEquals(0, orgList.getRows().size());
//        e.setOrganization(orgList.getRows().get(0));
//
//        CounterpartyEntity agent = new CounterpartyEntity();
//        agent.setName(randomString());
//        api.entity().counterparty().post(agent);
//        e.setAgent(agent);
//
//        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
//        assertEquals(1, store.getRows().size());
//        e.setStore(store.getRows().get(0));
//
//        api.entity().retailshift().post(e);
//
//        return e;
//    }
//
//    private void getAsserts(RetailShiftDocumentEntity e, RetailShiftDocumentEntity retrievedEntity) {
//        assertEquals(e.getName(), retrievedEntity.getName());
//        assertEquals(e.getDescription(), retrievedEntity.getDescription());
//        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
//        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
//        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
//    }
//
//    private void putAsserts(RetailShiftDocumentEntity e, RetailShiftDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
//        RetailShiftDocumentEntity retrievedUpdatedEntity = api.entity().retailshift().get(e.getId());
//
//        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
//        assertEquals(name, retrievedUpdatedEntity.getName());
//        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
//        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
//        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
//        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
//    }
//}
