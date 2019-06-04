package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.*;
import com.lognex.api.entities.products.BundleEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.entities.products.ServiceEntity;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.*;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.SearchParam.search;
import static org.junit.Assert.*;

public abstract class EntityTestBase implements TestRandomizers, TestAsserts, TestUtils {
    protected LognexApi api, mockApi;
    protected MockHttpClient mockHttpClient;

    @Before
    public void init() {
        api = new LognexApi(
                System.getenv("API_HOST"),
                false, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );

        mockHttpClient = new MockHttpClient();
        mockApi = new LognexApi("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
    }

    @After
    public void antiLimits() throws InterruptedException {
        Thread.sleep(200); // Защита от лимитов
    }

    protected BundleEntity createSimpleBundle() throws IOException, LognexApiException {
        BundleEntity e = new BundleEntity();
        e.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        e.setArticle(randomString());

        ProductEntity product = createSimpleProduct();
        ListEntity<BundleEntity.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new BundleEntity.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        e.setComponents(components);

        api.entity().bundle().post(e);

        return e;
    }

    protected ConsignmentEntity createSimpleConsignment() throws IOException, LognexApiException {
        ConsignmentEntity e = new ConsignmentEntity();
        e.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());

        ProductEntity product = createSimpleProduct();
        e.setAssortment(product);

        api.entity().consignment().post(e);

        return e;
    }

    protected ContractEntity createSimpleContract() throws IOException, LognexApiException {
        ContractEntity e = new ContractEntity();
        e.setName("contract_" + randomString(3) + "_" + new Date().getTime());

        e.setOwnAgent(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().contract().post(e);

        return e;
    }

    protected CounterpartyEntity createSimpleCounterparty() throws IOException, LognexApiException {
        CounterpartyEntity e = new CounterpartyEntity();
        e.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());
        e.setCompanyType(CompanyType.legal);

        e.setInn(randomString());
        e.setOgrn(randomString());
        e.setLegalAddress(randomString());
        e.setLegalTitle(randomString());

        api.entity().counterparty().post(e);

        return e;
    }

    protected CurrencyEntity getFirstCurrency() throws IOException, LognexApiException {
        ListEntity<CurrencyEntity> currencyEntityList = api.entity().currency().get();
        assertNotEquals(0, currencyEntityList.getRows().size());

        return currencyEntityList.getRows().get(0);
    }

    protected CurrencyEntity createSimpleCurrency() throws IOException, LognexApiException {
        CurrencyEntity e = new CurrencyEntity();
        e.setName("currency_" + randomString(3) + "_" + new Date().getTime());
        e.setCode(randomString(3));
        e.setIsoCode(randomString(3));

        api.entity().currency().post(e);

        return e;
    }

    protected CustomEntity createSimpleCustomEntity() throws IOException, LognexApiException {
        CustomEntity e = new CustomEntity();

        e.setName("custom_entity_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().post(e);
        return e;
    }

    protected EmployeeEntity createSimpleEmployee() throws IOException, LognexApiException {
        EmployeeEntity e = new EmployeeEntity();
        e.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        e.setFirstName(randomString());
        e.setMiddleName(randomString());

        api.entity().employee().post(e);

        return e;
    }

    protected ExpenseItemEntity createSimpleExpenseItem() throws IOException, LognexApiException {
        ExpenseItemEntity e = new ExpenseItemEntity();
        e.setName("expenseitem_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        api.entity().expenseitem().post(e);

        return e;
    }

    protected GroupEntity getMainGroup() throws IOException, LognexApiException {
        ListEntity<GroupEntity> group = api.entity().group().get(search("Основной"));
        assertEquals(1, group.getRows().size());

        return group.getRows().get(0);
    }

    protected OrganizationEntity getOwnOrganization() throws IOException, LognexApiException {
        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        Optional<OrganizationEntity> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(OrganizationEntity::getCreated));

        OrganizationEntity organizationEntity = null;
        if (orgOptional.isPresent()) {
            organizationEntity = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        return organizationEntity;
    }

    protected OrganizationEntity createSimpleOrganization() throws IOException, LognexApiException {
        OrganizationEntity e = new OrganizationEntity();
        e.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        e.setCompanyType(CompanyType.legal);
        e.setInn(randomString());
        e.setOgrn(randomString());

        api.entity().organization().post(e);

        return e;
    }

    protected ProductEntity createSimpleProduct() throws IOException, LognexApiException {
        ProductEntity e = new ProductEntity();
        e.setName("product_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        api.entity().product().post(e);

        return e;
    }

    protected ProductFolderEntity createSimpleProductFolder() throws IOException, LognexApiException {
        ProductFolderEntity e = new ProductFolderEntity();
        e.setName("productfolder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        api.entity().productfolder().post(e);

        return e;
    }

    protected ProjectEntity createSimpleProject() throws IOException, LognexApiException {
        ProjectEntity e = new ProjectEntity();
        e.setName("project_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        api.entity().project().post(e);

        return e;
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    protected ServiceEntity createSimpleService() throws IOException, LognexApiException {
        ServiceEntity e = new ServiceEntity();
        e.setName("service_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        api.entity().service().post(e);

        return e;
    }

    protected StoreEntity getMainStore() throws IOException, LognexApiException {
        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());

        return store.getRows().get(0);
    }

    protected StoreEntity createSimpleStore() throws IOException, LognexApiException {
        StoreEntity e = new StoreEntity();
        e.setName("store_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        api.entity().store().post(e);

        return e;
    }

    protected TaskEntity createSimpleTask() throws IOException, LognexApiException {
        EmployeeEntity adminEmpl = api.entity().employee().get(filterEq("name", "Администратор")).getRows().get(0);

        TaskEntity e = new TaskEntity();
        e.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        e.setAssignee(adminEmpl);
        api.entity().task().post(e);
        return e;
    }

    protected UomEntity createSimpleUom() throws IOException, LognexApiException {
        UomEntity e = new UomEntity();
        e.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        e.setCode(randomString());
        e.setExternalCode(randomString());

        api.entity().uom().post(e);

        return e;
    }

    protected VariantEntity createSimpleVariant() throws IOException, LognexApiException {
        VariantEntity e = new VariantEntity();
        e.setProduct(createSimpleProduct());

        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        e.setCharacteristics(new ArrayList<>());
        e.getCharacteristics().add(characteristic);

        api.entity().variant().post(e);

        return e;
    }

    protected CashInDocumentEntity createSimpleCashIn() throws IOException, LognexApiException {
        CashInDocumentEntity e = new CashInDocumentEntity();
        e.setName("cashin_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().cashin().post(e);

        return e;
    }

    protected CashOutDocumentEntity createSimpleCashOut() throws IOException, LognexApiException {
        CashOutDocumentEntity e = new CashOutDocumentEntity();
        e.setName("cashout_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setExpenseItem(createSimpleExpenseItem());

        api.entity().cashout().post(e);

        return e;
    }

    protected CommissionReportInDocumentEntity createSimpleCommissionReportIn() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity e = new CommissionReportInDocumentEntity();
        e.setName("commissionreportin_" + randomString(3) + "_" + new Date().getTime());
        OrganizationEntity ownOrganization = getOwnOrganization();
        e.setOrganization(ownOrganization);
        CounterpartyEntity agent = createSimpleCounterparty();
        e.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        e.setContract(contract);

        e.setCommissionPeriodStart(LocalDateTime.now());
        e.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportin().post(e);

        return e;
    }

    protected CommissionReportOutDocumentEntity createSimpleCommissionReportOut() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = new CommissionReportOutDocumentEntity();
        e.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());
        OrganizationEntity ownOrganization = getOwnOrganization();
        e.setOrganization(ownOrganization);
        CounterpartyEntity agent = createSimpleCounterparty();
        e.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        e.setContract(contract);

        e.setCommissionPeriodStart(LocalDateTime.now());
        e.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().post(e);

        return e;
    }

    protected CustomerOrderDocumentEntity createSimpleCustomerOrder() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity e = new CustomerOrderDocumentEntity();
        e.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().customerorder().post(e);

        return e;
    }

    protected DemandDocumentEntity createSimpleDemand() throws IOException, LognexApiException {
        DemandDocumentEntity e = new DemandDocumentEntity();
        e.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setStore(getMainStore());

        api.entity().demand().post(e);

        return e;
    }

    protected EnterDocumentEntity createSimpleEnter() throws IOException, LognexApiException {
        EnterDocumentEntity e = new EnterDocumentEntity();
        e.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setStore(getMainStore());

        api.entity().enter().post(e);

        return e;
    }

    protected FactureInDocumentEntity createSimpleFactureIn() throws IOException, LognexApiException {
        FactureInDocumentEntity e = new FactureInDocumentEntity();
        e.setName("facturein_" + randomString(3) + "_" + new Date().getTime());
        e.setIncomingNumber(randomString());
        e.setIncomingDate(LocalDateTime.now());

        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        supplies.add(createSimpleSupply());
        e.setSupplies(supplies);

        api.entity().facturein().post(e);

        return e;
    }

    protected FactureOutDocumentEntity createSimpleFactureOut() throws IOException, LognexApiException {
        FactureOutDocumentEntity e = new FactureOutDocumentEntity();
        e.setName("factureout_" + randomString(3) + "_" + new Date().getTime());
        e.setPaymentNumber(randomString());
        e.setPaymentDate(LocalDateTime.now());

        List<DemandDocumentEntity> demands = new ArrayList<>();
        demands.add(createSimpleDemand());
        e.setDemands(demands);

        api.entity().factureout().post(e);

        return e;
    }

    protected InternalOrderDocumentEntity createSimpleInternalOrder() throws IOException, LognexApiException {
        InternalOrderDocumentEntity e = new InternalOrderDocumentEntity();
        e.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setStore(getMainStore());

        api.entity().internalorder().post(e);

        return e;
    }

    protected InventoryDocumentEntity createSimpleInventory() throws IOException, LognexApiException {
        InventoryDocumentEntity e = new InventoryDocumentEntity();
        e.setName("inventory_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setStore(getMainStore());

        api.entity().inventory().post(e);

        return e;
    }

    protected InvoiceInDocumentEntity createSimpleInvoiceIn() throws IOException, LognexApiException {
        InvoiceInDocumentEntity e = new InvoiceInDocumentEntity();
        e.setName("invoicein_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().invoicein().post(e);

        return e;
    }

    protected InvoiceOutDocumentEntity createSimpleInvoiceOut() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = new InvoiceOutDocumentEntity();
        e.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().invoiceout().post(e);

        return e;
    }

    protected LossDocumentEntity createSimpleLoss() throws IOException, LognexApiException {
        LossDocumentEntity e = new LossDocumentEntity();
        e.setName("loss_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setStore(getMainStore());

        api.entity().loss().post(e);

        return e;
    }

    protected MoveDocumentEntity createSimpleMove() throws IOException, LognexApiException {
        MoveDocumentEntity e = new MoveDocumentEntity();
        e.setName("move_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setSourceStore(getMainStore());
        e.setTargetStore(createSimpleStore());

        api.entity().move().post(e);

        return e;
    }

    protected PaymentInDocumentEntity createSimplePaymentIn() throws IOException, LognexApiException {
        PaymentInDocumentEntity e = new PaymentInDocumentEntity();
        e.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().paymentin().post(e);

        return e;
    }

    protected PaymentOutDocumentEntity createSimplePaymentOut() throws IOException, LognexApiException {
        PaymentOutDocumentEntity e = new PaymentOutDocumentEntity();
        e.setName("paymentout_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setExpenseItem(createSimpleExpenseItem());

        api.entity().paymentout().post(e);

        return e;
    }

    protected PricelistDocumentEntity createSimplePricelist() throws IOException, LognexApiException {
        PricelistDocumentEntity e = new PricelistDocumentEntity();
        e.setName("pricelist_" + randomString(3) + "_" + new Date().getTime());

        List<PricelistDocumentEntity.ColumnsItem> columns = new ArrayList<>();
        PricelistDocumentEntity.ColumnsItem item = new PricelistDocumentEntity.ColumnsItem();
        item.setName(randomString());
        item.setPercentageDiscount(randomInteger(1, 10000));
        columns.add(item);
        e.setColumns(columns);

        ProductEntity product = createSimpleProduct();
        ListEntity<PricelistDocumentEntity.PricelistRow> positions = new ListEntity<>();
        positions.setRows(new ArrayList<>());
        positions.getRows().add(new PricelistDocumentEntity.PricelistRow());
        positions.getRows().get(0).setAssortment(product);
        positions.getRows().get(0).setCells(new ArrayList<>());
        positions.getRows().get(0).getCells().add(new PricelistDocumentEntity.PricelistRow.CellsItem());
        positions.getRows().get(0).getCells().get(0).setColumn(columns.get(0).getName());
        positions.getRows().get(0).getCells().get(0).setSum(randomLong(1, 10000));
        e.setPositions(positions);

        api.entity().pricelist().post(e);

        return e;
    }

    protected ProcessingDocumentEntity createSimpleProcessing() throws IOException, LognexApiException {
        ProcessingDocumentEntity e = new ProcessingDocumentEntity();
        e.setName("processing_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());

        e.setMaterials(new ListEntity<>());
        e.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = createSimpleProduct();
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(randomDouble(1, 5, 10));
        e.getMaterials().getRows().add(materialPosition);

        e.setProducts(new ListEntity<>());
        e.getProducts().setRows(new ArrayList<>());
        ProductEntity product = createSimpleProduct();
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(randomDouble(1, 5, 10));
        e.getProducts().getRows().add(productPosition);

        e.setProcessingSum(randomLong(10, 10000));
        e.setQuantity(randomDouble(1, 5, 10));

        StoreEntity store = getMainStore();
        e.setMaterialsStore(store);
        e.setProductsStore(store);

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        e.setProcessingPlan(processingPlan);

        api.entity().processing().post(e);

        return e;
    }

    protected ProcessingOrderDocumentEntity createSimpleProcessingOrder() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity e = new ProcessingOrderDocumentEntity();
        e.setName("processingorder_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 4));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 4));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        e.setProcessingPlan(processingPlan);

        e.setPositions(new ListEntity<>());
        e.getPositions().setRows(new ArrayList<>());
        DocumentPosition position = new DocumentPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        e.getPositions().getRows().add(position);

        api.entity().processingorder().post(e);

        return e;
    }

    protected ProcessingPlanDocumentEntity createSimpleProcessingPlan() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity e = new ProcessingPlanDocumentEntity();
        e.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        e.setMaterials(new ListEntity<>());
        e.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        e.getMaterials().getRows().add(materialItem);

        e.setProducts(new ListEntity<>());
        e.getProducts().setRows(new ArrayList<>());
        ProductEntity product = createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        e.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(e);

        return e;
    }

    protected PurchaseOrderDocumentEntity createSimplePurchaseOrder() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = new PurchaseOrderDocumentEntity();
        e.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());

        api.entity().purchaseorder().post(e);

        return e;
    }

    protected PurchaseReturnDocumentEntity createSimplePurchaseReturn() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity e = new PurchaseReturnDocumentEntity();
        e.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setStore(getMainStore());

        api.entity().purchasereturn().post(e);

        return e;
    }

    protected RetailDemandDocumentEntity createSimpleRetailDemand() throws IOException, LognexApiException {
        RetailDemandDocumentEntity e = new RetailDemandDocumentEntity();
        e.setName("retaildemand_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setStore(getMainStore());

        api.entity().retaildemand().post(e);

        return e;
    }

    protected SalesReturnDocumentEntity createSimpleSalesReturn() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = new SalesReturnDocumentEntity();
        e.setName("salesreturn_" + randomString(3) + "_" + new Date().getTime());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setStore(getMainStore());

        api.entity().salesreturn().post(e);

        return e;
    }

    protected SupplyDocumentEntity createSimpleSupply() throws IOException, LognexApiException {
        SupplyDocumentEntity e = new SupplyDocumentEntity();
        e.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setStore(getMainStore());

        api.entity().supply().post(e);

        return e;
    }
}
