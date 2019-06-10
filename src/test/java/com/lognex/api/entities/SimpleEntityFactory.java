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
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.TestRandomizers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.SearchParam.search;
import static org.junit.Assert.*;

@SuppressWarnings("unused")
public class SimpleEntityFactory implements TestRandomizers {
    private LognexApi api;

    public SimpleEntityFactory(LognexApi api) {
        this.api = api;
    }

    /**
     * Создает объект переданного класса с заполнением необходимых полей
     * путем вызова соответствующего createSimple* метода
     */
    public <T extends MetaEntity> T createSimple(Class<T> entityClass) {
        Method method;
        String methodName = "createSimple" + entityClass.getSimpleName().
                replace("Entity", "").
                replace("Document", "");

        Object entity = null;
        try {
            method = this.getClass().getMethod(methodName);
            entity = method.invoke(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail("Невозможно получить метод " + methodName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail("Невозможно вызвать метод " + methodName);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            fail("Запрашиваемый метод выдал исключение: " + e.getMessage());
        }

        return entityClass.cast(entity);
    }

    public BundleEntity createSimpleBundle() throws IOException, LognexApiException {
        BundleEntity bundle = new BundleEntity();
        bundle.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundle.setArticle(randomString());

        ProductEntity product = createSimpleProduct();
        ListEntity<BundleEntity.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new BundleEntity.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        bundle.setComponents(components);

        api.entity().bundle().post(bundle);

        return bundle;
    }

    public ConsignmentEntity createSimpleConsignment() throws IOException, LognexApiException {
        ConsignmentEntity consignment = new ConsignmentEntity();
        consignment.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());

        ProductEntity product = createSimpleProduct();
        consignment.setAssortment(product);

        api.entity().consignment().post(consignment);

        return consignment;
    }

    public ContractEntity createSimpleContract() throws IOException, LognexApiException {
        ContractEntity contract = new ContractEntity();
        contract.setName("contract_" + randomString(3) + "_" + new Date().getTime());

        contract.setOwnAgent(getOwnOrganization());
        contract.setAgent(createSimpleCounterparty());

        api.entity().contract().post(contract);

        return contract;
    }

    public CounterpartyEntity createSimpleCounterparty() throws IOException, LognexApiException {
        CounterpartyEntity counterparty = new CounterpartyEntity();
        counterparty.setName("counterparty_" + randomString(3) + "_" + new Date().getTime());
        counterparty.setCompanyType(CompanyType.legal);

        counterparty.setInn(randomString());
        counterparty.setOgrn(randomString());
        counterparty.setLegalAddress(randomString());
        counterparty.setLegalTitle(randomString());

        api.entity().counterparty().post(counterparty);

        return counterparty;
    }

    public CurrencyEntity getFirstCurrency() throws IOException, LognexApiException {
        ListEntity<CurrencyEntity> currencyEntityList = api.entity().currency().get();
        assertNotEquals(0, currencyEntityList.getRows().size());

        return currencyEntityList.getRows().get(0);
    }

    public CurrencyEntity createSimpleCurrency() throws IOException, LognexApiException {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setName("currency_" + randomString(3) + "_" + new Date().getTime());
        currency.setCode(randomString(3));
        currency.setIsoCode(randomString(3));

        api.entity().currency().post(currency);

        return currency;
    }

    public CustomEntity createSimpleCustomEntity() throws IOException, LognexApiException {
        CustomEntity customEntity = new CustomEntity();

        customEntity.setName("custom_entity_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().post(customEntity);
        return customEntity;
    }

    public CustomEntityElement createSimpleCustomElement(CustomEntity customEntity) throws IOException, LognexApiException {
        CustomEntityElement customEntityElement = new CustomEntityElement();

        customEntityElement.setName("custom_entity_element_" + randomString(3) + "_" + new Date().getTime());
        customEntityElement.setDescription("custom_entity_desc_" + randomString(3));
        customEntityElement.setExternalCode(randomString(3));

        api.entity().customentity().postCustomEntityElement(customEntity.getId(), customEntityElement);
        return customEntityElement;
    }

    public EmployeeEntity createSimpleEmployee() throws IOException, LognexApiException {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        employee.setFirstName(randomString());
        employee.setMiddleName(randomString());

        api.entity().employee().post(employee);

        return employee;
    }

    public ExpenseItemEntity createSimpleExpenseItem() throws IOException, LognexApiException {
        ExpenseItemEntity expenseItem = new ExpenseItemEntity();
        expenseItem.setName("expenseitem_" + randomString(3) + "_" + new Date().getTime());
        expenseItem.setDescription(randomString());

        api.entity().expenseitem().post(expenseItem);

        return expenseItem;
    }

    public GroupEntity getMainGroup() throws IOException, LognexApiException {
        ListEntity<GroupEntity> group = api.entity().group().get(search("Основной"));
        assertEquals(1, group.getRows().size());

        return group.getRows().get(0);
    }

    public OrganizationEntity getOwnOrganization() throws IOException, LognexApiException {
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

    public OrganizationEntity createSimpleOrganization() throws IOException, LognexApiException {
        OrganizationEntity organization = new OrganizationEntity();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        organization.setCompanyType(CompanyType.legal);
        organization.setInn(randomString());
        organization.setOgrn(randomString());

        api.entity().organization().post(organization);

        return organization;
    }

    public ProductEntity createSimpleProduct() throws IOException, LognexApiException {
        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setDescription(randomString());

        api.entity().product().post(product);

        return product;
    }

    public ProductFolderEntity createSimpleProductFolder() throws IOException, LognexApiException {
        ProductFolderEntity productFolder = new ProductFolderEntity();
        productFolder.setName("productfolder_" + randomString(3) + "_" + new Date().getTime());
        productFolder.setDescription(randomString());

        api.entity().productfolder().post(productFolder);

        return productFolder;
    }

    public ProjectEntity createSimpleProject() throws IOException, LognexApiException {
        ProjectEntity project = new ProjectEntity();
        project.setName("project_" + randomString(3) + "_" + new Date().getTime());
        project.setDescription(randomString());

        api.entity().project().post(project);

        return project;
    }

    public ServiceEntity createSimpleService() throws IOException, LognexApiException {
        ServiceEntity service = new ServiceEntity();
        service.setName("service_" + randomString(3) + "_" + new Date().getTime());
        service.setDescription(randomString());

        api.entity().service().post(service);

        return service;
    }

    public StoreEntity getMainStore() throws IOException, LognexApiException {
        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());

        return store.getRows().get(0);
    }

    public StoreEntity createSimpleStore() throws IOException, LognexApiException {
        StoreEntity store = new StoreEntity();
        store.setName("store_" + randomString(3) + "_" + new Date().getTime());
        store.setDescription(randomString());

        api.entity().store().post(store);

        return store;
    }

    public TaskEntity createSimpleTask() throws IOException, LognexApiException {
        EmployeeEntity adminEmpl = api.entity().employee().get(filterEq("name", "Администратор")).getRows().get(0);

        TaskEntity task = new TaskEntity();
        task.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        task.setAssignee(adminEmpl);
        api.entity().task().post(task);
        return task;
    }

    public UomEntity createSimpleUom() throws IOException, LognexApiException {
        UomEntity uomEntity = new UomEntity();
        uomEntity.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        uomEntity.setCode(randomString());
        uomEntity.setExternalCode(randomString());

        api.entity().uom().post(uomEntity);

        return uomEntity;
    }

    public VariantEntity createSimpleVariant() throws IOException, LognexApiException {
        VariantEntity variant = new VariantEntity();
        variant.setProduct(createSimpleProduct());

        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        variant.setCharacteristics(new ArrayList<>());
        variant.getCharacteristics().add(characteristic);

        api.entity().variant().post(variant);

        return variant;
    }

    public CashInDocumentEntity createSimpleCashIn() throws IOException, LognexApiException {
        CashInDocumentEntity cashIn = new CashInDocumentEntity();
        cashIn.setName("cashin_" + randomString(3) + "_" + new Date().getTime());
        cashIn.setDescription(randomString());
        cashIn.setOrganization(getOwnOrganization());
        cashIn.setAgent(createSimpleCounterparty());

        api.entity().cashin().post(cashIn);

        return cashIn;
    }

    public CashOutDocumentEntity createSimpleCashOut() throws IOException, LognexApiException {
        CashOutDocumentEntity cashOut = new CashOutDocumentEntity();
        cashOut.setName("cashout_" + randomString(3) + "_" + new Date().getTime());
        cashOut.setDescription(randomString());
        cashOut.setOrganization(getOwnOrganization());
        cashOut.setAgent(createSimpleCounterparty());
        cashOut.setExpenseItem(createSimpleExpenseItem());

        api.entity().cashout().post(cashOut);

        return cashOut;
    }

    public CommissionReportInDocumentEntity createSimpleCommissionReportIn() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = new CommissionReportInDocumentEntity();
        commissionReportIn.setName("commissionreportin_" + randomString(3) + "_" + new Date().getTime());
        OrganizationEntity ownOrganization = getOwnOrganization();
        commissionReportIn.setOrganization(ownOrganization);
        CounterpartyEntity agent = createSimpleCounterparty();
        commissionReportIn.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        commissionReportIn.setContract(contract);

        commissionReportIn.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportIn.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportin().post(commissionReportIn);

        return commissionReportIn;
    }

    public CommissionReportOutDocumentEntity createSimpleCommissionReportOut() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = new CommissionReportOutDocumentEntity();
        commissionReportOut.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());
        OrganizationEntity ownOrganization = getOwnOrganization();
        commissionReportOut.setOrganization(ownOrganization);
        CounterpartyEntity agent = createSimpleCounterparty();
        commissionReportOut.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        commissionReportOut.setContract(contract);

        commissionReportOut.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportOut.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().post(commissionReportOut);

        return commissionReportOut;
    }

    public CustomerOrderDocumentEntity createSimpleCustomerOrder() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = new CustomerOrderDocumentEntity();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        customerOrder.setDescription(randomString());
        customerOrder.setOrganization(getOwnOrganization());
        customerOrder.setAgent(createSimpleCounterparty());

        api.entity().customerorder().post(customerOrder);

        return customerOrder;
    }

    public DemandDocumentEntity createSimpleDemand() throws IOException, LognexApiException {
        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setOrganization(getOwnOrganization());
        demand.setAgent(createSimpleCounterparty());
        demand.setStore(getMainStore());

        api.entity().demand().post(demand);

        return demand;
    }

    public EnterDocumentEntity createSimpleEnter() throws IOException, LognexApiException {
        EnterDocumentEntity enter = new EnterDocumentEntity();
        enter.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        enter.setDescription(randomString());
        enter.setOrganization(getOwnOrganization());
        enter.setStore(getMainStore());

        api.entity().enter().post(enter);

        return enter;
    }

    public FactureInDocumentEntity createSimpleFactureIn() throws IOException, LognexApiException {
        FactureInDocumentEntity factureIn = new FactureInDocumentEntity();
        factureIn.setName("facturein_" + randomString(3) + "_" + new Date().getTime());
        factureIn.setIncomingNumber(randomString());
        factureIn.setIncomingDate(LocalDateTime.now());

        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        supplies.add(createSimpleSupply());
        factureIn.setSupplies(supplies);

        api.entity().facturein().post(factureIn);

        return factureIn;
    }

    public FactureOutDocumentEntity createSimpleFactureOut() throws IOException, LognexApiException {
        FactureOutDocumentEntity factureOut = new FactureOutDocumentEntity();
        factureOut.setName("factureout_" + randomString(3) + "_" + new Date().getTime());
        factureOut.setPaymentNumber(randomString());
        factureOut.setPaymentDate(LocalDateTime.now());

        List<DemandDocumentEntity> demands = new ArrayList<>();
        demands.add(createSimpleDemand());
        factureOut.setDemands(demands);

        api.entity().factureout().post(factureOut);

        return factureOut;
    }

    public InternalOrderDocumentEntity createSimpleInternalOrder() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = new InternalOrderDocumentEntity();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setDescription(randomString());
        internalOrder.setOrganization(getOwnOrganization());
        internalOrder.setStore(getMainStore());

        api.entity().internalorder().post(internalOrder);

        return internalOrder;
    }

    public InventoryDocumentEntity createSimpleInventory() throws IOException, LognexApiException {
        InventoryDocumentEntity inventory = new InventoryDocumentEntity();
        inventory.setName("inventory_" + randomString(3) + "_" + new Date().getTime());
        inventory.setOrganization(getOwnOrganization());
        inventory.setStore(getMainStore());

        api.entity().inventory().post(inventory);

        return inventory;
    }

    public InvoiceInDocumentEntity createSimpleInvoiceIn() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = new InvoiceInDocumentEntity();
        invoiceIn.setName("invoicein_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setOrganization(getOwnOrganization());
        invoiceIn.setAgent(createSimpleCounterparty());

        api.entity().invoicein().post(invoiceIn);

        return invoiceIn;
    }

    public InvoiceOutDocumentEntity createSimpleInvoiceOut() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = new InvoiceOutDocumentEntity();
        invoiceOut.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceOut.setOrganization(getOwnOrganization());
        invoiceOut.setAgent(createSimpleCounterparty());

        api.entity().invoiceout().post(invoiceOut);

        return invoiceOut;
    }

    public LossDocumentEntity createSimpleLoss() throws IOException, LognexApiException {
        LossDocumentEntity loss = new LossDocumentEntity();
        loss.setName("loss_" + randomString(3) + "_" + new Date().getTime());
        loss.setDescription(randomString());
        loss.setOrganization(getOwnOrganization());
        loss.setStore(getMainStore());

        api.entity().loss().post(loss);

        return loss;
    }

    public MoveDocumentEntity createSimpleMove() throws IOException, LognexApiException {
        MoveDocumentEntity move = new MoveDocumentEntity();
        move.setName("move_" + randomString(3) + "_" + new Date().getTime());
        move.setDescription(randomString());
        move.setOrganization(getOwnOrganization());
        move.setSourceStore(getMainStore());
        move.setTargetStore(createSimpleStore());

        api.entity().move().post(move);

        return move;
    }

    public PaymentInDocumentEntity createSimplePaymentIn() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = new PaymentInDocumentEntity();
        paymentIn.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        paymentIn.setOrganization(getOwnOrganization());
        paymentIn.setAgent(createSimpleCounterparty());

        api.entity().paymentin().post(paymentIn);

        return paymentIn;
    }

    public PaymentOutDocumentEntity createSimplePaymentOut() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = new PaymentOutDocumentEntity();
        paymentOut.setName("paymentout_" + randomString(3) + "_" + new Date().getTime());
        paymentOut.setOrganization(getOwnOrganization());
        paymentOut.setAgent(createSimpleCounterparty());
        paymentOut.setExpenseItem(createSimpleExpenseItem());

        api.entity().paymentout().post(paymentOut);

        return paymentOut;
    }

    public PricelistDocumentEntity createSimplePricelist() throws IOException, LognexApiException {
        PricelistDocumentEntity priceList = new PricelistDocumentEntity();
        priceList.setName("pricelist_" + randomString(3) + "_" + new Date().getTime());

        List<PricelistDocumentEntity.ColumnsItem> columns = new ArrayList<>();
        PricelistDocumentEntity.ColumnsItem item = new PricelistDocumentEntity.ColumnsItem();
        item.setName(randomString());
        item.setPercentageDiscount(randomInteger(1, 10000));
        columns.add(item);
        priceList.setColumns(columns);

        ProductEntity product = createSimpleProduct();
        ListEntity<PricelistDocumentEntity.PricelistRow> positions = new ListEntity<>();
        positions.setRows(new ArrayList<>());
        positions.getRows().add(new PricelistDocumentEntity.PricelistRow());
        positions.getRows().get(0).setAssortment(product);
        positions.getRows().get(0).setCells(new ArrayList<>());
        positions.getRows().get(0).getCells().add(new PricelistDocumentEntity.PricelistRow.CellsItem());
        positions.getRows().get(0).getCells().get(0).setColumn(columns.get(0).getName());
        positions.getRows().get(0).getCells().get(0).setSum(randomLong(1, 10000));
        priceList.setPositions(positions);

        api.entity().pricelist().post(priceList);

        return priceList;
    }

    public ProcessingDocumentEntity createSimpleProcessing() throws IOException, LognexApiException {
        ProcessingDocumentEntity processing = new ProcessingDocumentEntity();
        processing.setName("processing_" + randomString(3) + "_" + new Date().getTime());
        processing.setOrganization(getOwnOrganization());

        processing.setMaterials(new ListEntity<>());
        processing.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = createSimpleProduct();
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(randomDouble(1, 5, 10));
        processing.getMaterials().getRows().add(materialPosition);

        processing.setProducts(new ListEntity<>());
        processing.getProducts().setRows(new ArrayList<>());
        ProductEntity product = createSimpleProduct();
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(randomDouble(1, 5, 10));
        processing.getProducts().getRows().add(productPosition);

        processing.setProcessingSum(randomLong(10, 10000));
        processing.setQuantity(randomDouble(1, 5, 10));

        StoreEntity store = getMainStore();
        processing.setMaterialsStore(store);
        processing.setProductsStore(store);

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
        processing.setProcessingPlan(processingPlan);

        api.entity().processing().post(processing);

        return processing;
    }

    public ProcessingOrderDocumentEntity createSimpleProcessingOrder() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity processingOrder = new ProcessingOrderDocumentEntity();
        processingOrder.setName("processingorder_" + randomString(3) + "_" + new Date().getTime());
        processingOrder.setOrganization(getOwnOrganization());

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
        processingOrder.setProcessingPlan(processingPlan);

        processingOrder.setPositions(new ListEntity<>());
        processingOrder.getPositions().setRows(new ArrayList<>());
        DocumentPosition position = new DocumentPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        processingOrder.getPositions().getRows().add(position);

        api.entity().processingorder().post(processingOrder);

        return processingOrder;
    }

    public ProcessingPlanDocumentEntity createSimpleProcessingPlan() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = createSimpleProduct();
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);

        return processingPlan;
    }

    public PurchaseOrderDocumentEntity createSimplePurchaseOrder() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = new PurchaseOrderDocumentEntity();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setOrganization(getOwnOrganization());
        purchaseOrder.setAgent(createSimpleCounterparty());

        api.entity().purchaseorder().post(purchaseOrder);

        return purchaseOrder;
    }

    public PurchaseReturnDocumentEntity createSimplePurchaseReturn() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = new PurchaseReturnDocumentEntity();
        purchaseReturn.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
        purchaseReturn.setOrganization(getOwnOrganization());
        purchaseReturn.setAgent(createSimpleCounterparty());
        purchaseReturn.setStore(getMainStore());

        api.entity().purchasereturn().post(purchaseReturn);

        return purchaseReturn;
    }

    public RetailDemandDocumentEntity createSimpleRetailDemand() throws IOException, LognexApiException {
        RetailDemandDocumentEntity retailDemand = new RetailDemandDocumentEntity();
        retailDemand.setName("retaildemand_" + randomString(3) + "_" + new Date().getTime());
        retailDemand.setDescription(randomString());
        retailDemand.setOrganization(getOwnOrganization());
        retailDemand.setAgent(createSimpleCounterparty());
        retailDemand.setStore(getMainStore());

        api.entity().retaildemand().post(retailDemand);

        return retailDemand;
    }

    public SalesReturnDocumentEntity createSimpleSalesReturn() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = new SalesReturnDocumentEntity();
        salesReturn.setName("salesreturn_" + randomString(3) + "_" + new Date().getTime());
        salesReturn.setOrganization(getOwnOrganization());
        salesReturn.setAgent(createSimpleCounterparty());
        salesReturn.setStore(getMainStore());

        api.entity().salesreturn().post(salesReturn);

        return salesReturn;
    }

    public SupplyDocumentEntity createSimpleSupply() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setDescription(randomString());
        supply.setOrganization(getOwnOrganization());
        supply.setAgent(createSimpleCounterparty());
        supply.setStore(getMainStore());

        api.entity().supply().post(supply);

        return supply;
    }
}
