package com.lognex.api.entities.documents;

import com.lognex.api.entities.ContractEntity;
import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.SearchParam.search;
import static org.junit.Assert.*;

public class PaymentInDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = new PaymentInDocumentEntity();
        e.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        e.setMoment(LocalDateTime.now());
        e.setSum(randomLong(10, 10000));

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().paymentin().post(e);

        ListEntity<PaymentInDocumentEntity> updatedEntitiesList = api.entity().paymentin().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = createSimpleDocumentPaymentIn();

        PaymentInDocumentEntity retrievedEntity = api.entity().paymentin().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().paymentin().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PaymentInDocumentEntity e = createSimpleDocumentPaymentIn();

        PaymentInDocumentEntity retrievedOriginalEntity = api.entity().paymentin().get(e.getId());
        String name = "paymentin_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().paymentin().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "paymentin_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().paymentin().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = createSimpleDocumentPaymentIn();

        ListEntity<PaymentInDocumentEntity> entitiesList = api.entity().paymentin().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().paymentin().delete(e.getId());

        entitiesList = api.entity().paymentin().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = createSimpleDocumentPaymentIn();

        ListEntity<PaymentInDocumentEntity> entitiesList = api.entity().paymentin().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().paymentin().delete(e);

        entitiesList = api.entity().paymentin().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().paymentin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = api.entity().paymentin().newDocument();
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        Optional<OrganizationEntity> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(OrganizationEntity::getCreated));

        OrganizationEntity org = null;
        if (orgOptional.isPresent()) {
            org = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        assertEquals(e.getOrganization().getMeta().getHref(), org.getMeta().getHref());

        ListEntity<GroupEntity> group = api.entity().group().get(search("Основной"));
        assertEquals(1, group.getRows().size());
        assertEquals(e.getGroup().getMeta().getHref(), group.getRows().get(0).getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = new CustomerOrderDocumentEntity();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        customerOrder.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        customerOrder.setAgent(agent);

        api.entity().customerorder().post(customerOrder);

        PaymentInDocumentEntity e = api.entity().paymentin().newDocument("operations", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(customerOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(customerOrder.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = new PurchaseReturnDocumentEntity();
        purchaseReturn.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        purchaseReturn.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        purchaseReturn.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        purchaseReturn.setStore(store.getRows().get(0));

        api.entity().purchasereturn().post(purchaseReturn);

        PaymentInDocumentEntity e = api.entity().paymentin().newDocument("operations", Collections.singletonList(purchaseReturn));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(purchaseReturn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(purchaseReturn.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByDemandsTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        demand.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        demand.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        demand.setStore(store.getRows().get(0));

        api.entity().demand().post(demand);

        PaymentInDocumentEntity e = api.entity().paymentin().newDocument("operations", Collections.singletonList(demand));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(demand.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(demand.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesOutTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = new InvoiceOutDocumentEntity();
        invoiceOut.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceOut.setVatEnabled(true);
        invoiceOut.setVatIncluded(true);
        invoiceOut.setPayedSum(randomLong(1, 10000));
        invoiceOut.setSum(randomLong(1, 10000));

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        invoiceOut.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        invoiceOut.setAgent(agent);

        api.entity().invoiceout().post(invoiceOut);

        PaymentInDocumentEntity e = api.entity().paymentin().newDocument("operations", Collections.singletonList(invoiceOut));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(invoiceOut.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(invoiceOut.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceOut.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsInTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = new CommissionReportInDocumentEntity();
        commissionReportIn.setName("commissionreportin_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        commissionReportIn.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        commissionReportIn.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(orgList.getRows().get(0));
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        commissionReportIn.setContract(contract);

        commissionReportIn.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportIn.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportin().post(commissionReportIn);

        PaymentInDocumentEntity e = api.entity().paymentin().newDocument("operations", Collections.singletonList(commissionReportIn));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(commissionReportIn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(commissionReportIn.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportIn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), e.getContract().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private PaymentInDocumentEntity createSimpleDocumentPaymentIn() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = new PaymentInDocumentEntity();
        e.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().paymentin().post(e);

        return e;
    }

    private void getAsserts(PaymentInDocumentEntity e, PaymentInDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(PaymentInDocumentEntity e, PaymentInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PaymentInDocumentEntity retrievedUpdatedEntity = api.entity().paymentin().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }
}
