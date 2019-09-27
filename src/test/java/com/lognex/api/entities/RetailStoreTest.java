package com.lognex.api.entities;

import com.google.common.collect.ImmutableList;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailStoreTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailStore retailStore = new RetailStore();
        retailStore.setName("retailstore_" + randomStringTail());
        retailStore.setDescription(randomString());
        retailStore.setExternalCode(randomString());
        retailStore.setAddressFull(randomAddress(api));
        retailStore.setAllowCreateProducts(true);
        retailStore.setControlShippingStock(false);
        retailStore.setActive(false);
        retailStore.setControlCashierChoice(true);
        retailStore.setDiscountEnable(true);
        retailStore.setDiscountMaxPercent(20);
        retailStore.setPriceType(api.entity().companysettings().pricetype().getDefault());
        retailStore.setOrganization(simpleEntityManager.getOwnOrganization());
        retailStore.setStore(simpleEntityManager.getMainStore());
        retailStore.setAcquire(simpleEntityManager.createSimpleOrganization());
        retailStore.setBankPercent(randomDouble(10, 2, 2));
        retailStore.setIssueOrders(true);
        retailStore.setSellReserves(true);
        retailStore.setOfdEnabled(true);
        retailStore.setPriorityOfdSend(RetailStore.PriorityOfdSend.PHONE);
        retailStore.setAllowCustomPrice(true);
        retailStore.setDemandPrefix(randomString(3) + "_");
        retailStore.setAllowSellTobaccoWithoutMRC(true);
        retailStore.setProductFolders(new ListEntity<>());
        retailStore.getProductFolders().setRows(new ArrayList<>());
        retailStore.getProductFolders().getRows().add(simpleEntityManager.createSimpleProductFolder());
        retailStore.setCreateAgentsTags(ImmutableList.of(randomString(), randomString()));
        retailStore.setFilterAgentsTags(ImmutableList.of(randomString()));
        retailStore.setPrintAlways(true);
        retailStore.setCreatePaymentInOnRetailShiftClosing(true);
        retailStore.setCreateCashInOnRetailShiftClosing(true);
        retailStore.setReturnFromClosedShiftEnabled(true);
        retailStore.setEnableReturnsWithNoReason(true);
        retailStore.setReservePrepaidGoods(true);
        retailStore.setDefaultTaxSystem(TaxSystem.PRESUMPTIVE_TAX_SYSTEM);
        retailStore.setOrderTaxSystem(TaxSystem.UNIFIED_AGRICULTURAL_TAX);

        api.entity().retailstore().create(retailStore);

        ListEntity<RetailStore> updatedEntitiesList = api.entity().retailstore().get(filterEq("name", retailStore.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailStore retrievedEntity = updatedEntitiesList.getRows().get(0);

        assertEquals(retailStore.getName(), retrievedEntity.getName());
        assertEquals(retailStore.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailStore.getExternalCode(), retrievedEntity.getExternalCode());
        assertAddressFull(retailStore.getAddressFull(), retrievedEntity.getAddressFull());
        assertEquals(retailStore.getAllowCreateProducts(), retrievedEntity.getAllowCreateProducts());
        assertEquals(retailStore.getControlShippingStock(), retrievedEntity.getControlShippingStock());
        assertEquals(retailStore.getActive(), retrievedEntity.getActive());
        assertEquals(retailStore.getControlCashierChoice(), retrievedEntity.getControlCashierChoice());
        assertEquals(retailStore.getDiscountEnable(), retrievedEntity.getDiscountEnable());
        assertEquals(retailStore.getDiscountMaxPercent(), retrievedEntity.getDiscountMaxPercent());
        assertEquals(retailStore.getPriceType(), retrievedEntity.getPriceType());
        assertEquals(retailStore.getOrganization(), retrievedEntity.getOrganization());
        assertEquals(retailStore.getStore(), retrievedEntity.getStore());
        assertEquals(retailStore.getAcquire(), retrievedEntity.getAcquire());
        assertEquals(retailStore.getBankPercent(), retrievedEntity.getBankPercent());
        assertEquals(retailStore.getIssueOrders(), retrievedEntity.getIssueOrders());
        assertEquals(retailStore.getSellReserves(), retrievedEntity.getSellReserves());
        assertEquals(retailStore.getOfdEnabled(), retrievedEntity.getOfdEnabled());
        assertEquals(retailStore.getPriorityOfdSend(), retrievedEntity.getPriorityOfdSend());
        assertEquals(retailStore.getAllowCustomPrice(), retrievedEntity.getAllowCustomPrice());
        assertEquals(retailStore.getDemandPrefix(), retrievedEntity.getDemandPrefix());
        assertEquals(retailStore.getAllowSellTobaccoWithoutMRC(), retrievedEntity.getAllowSellTobaccoWithoutMRC());
        assertEquals(retailStore.getProductFolders(), retrievedEntity.getProductFolders());
        assertEquals(retailStore.getCreateAgentsTags(), retrievedEntity.getCreateAgentsTags());
        assertEquals(retailStore.getFilterAgentsTags(), retrievedEntity.getFilterAgentsTags());
        assertEquals(retailStore.getPrintAlways(), retrievedEntity.getPrintAlways());
        assertEquals(retailStore.getCreatePaymentInOnRetailShiftClosing(), retrievedEntity.getCreatePaymentInOnRetailShiftClosing());
        assertEquals(retailStore.getCreateCashInOnRetailShiftClosing(), retrievedEntity.getCreateCashInOnRetailShiftClosing());
        assertEquals(retailStore.getReturnFromClosedShiftEnabled(), retrievedEntity.getReturnFromClosedShiftEnabled());
        assertEquals(retailStore.getEnableReturnsWithNoReason(), retrievedEntity.getEnableReturnsWithNoReason());
        assertEquals(retailStore.getReservePrepaidGoods(), retrievedEntity.getReservePrepaidGoods());
        assertEquals(retailStore.getDefaultTaxSystem(), retrievedEntity.getDefaultTaxSystem());
        assertEquals(retailStore.getOrderTaxSystem(), retrievedEntity.getOrderTaxSystem());
    }

    @Test
    public void cashiersTest() throws Exception {
        RetailStore retailStore = simpleEntityManager.createSimple(RetailStore.class, true);
        Employee employee1 = simpleEntityManager.createSimpleEmployee();
        Employee employee2 = simpleEntityManager.createSimpleEmployee();
        List<Employee> employeeList = ImmutableList.of(employee1, employee2);
        retailStore.setCashiers(employeeList);

        api.entity().retailstore().update(retailStore);
        RetailStore updatedRetailStore = api.entity().retailstore().get(retailStore, expand("cashiers"));

        assertEquals(2, updatedRetailStore.getCashiers().getRows().size());
        assertTrue(updatedRetailStore.getCashiers().getRows().stream()
                .allMatch(c -> Meta.Type.CASHIER.equals(c.getMeta().getType()) &&
                        retailStore.getMeta().getHref().equals(c.getRetailStore().getMeta().getHref()) &&
                        employeeList.stream().anyMatch(e -> e.getMeta().getHref().equals(c.getEmployee().getMeta().getHref()))
                )
        );
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        RetailStore retailStore = (RetailStore) originalEntity;
        RetailStore retrievedStore = (RetailStore) retrievedEntity;

        assertEquals(retailStore.getName(), retrievedStore.getName());
        assertEquals(retailStore.getStore(), retrievedStore.getStore());
        assertEquals(retailStore.getOrganization(), retrievedStore.getOrganization());
        assertEquals(retailStore.getPriceType(), retrievedStore.getPriceType());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailStore originalStore = (RetailStore) originalEntity;
        RetailStore retrievedStore = (RetailStore) updatedEntity;

        assertNotEquals(originalStore.getName(), retrievedStore.getName());
        assertEquals(changedField, retrievedStore.getName());
        assertEquals(originalStore.getStore(), retrievedStore.getStore());
        assertEquals(originalStore.getOrganization(), retrievedStore.getOrganization());
        assertEquals(originalStore.getPriceType(), retrievedStore.getPriceType());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().retailstore();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return RetailStore.class;
    }
}
