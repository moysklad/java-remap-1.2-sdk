package ru.moysklad.remap_1_2.entities;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.*;
import ru.moysklad.remap_1_2.entities.documents.positions.ProcessingOrderPosition;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.SearchParam.search;
import static org.junit.Assert.*;

@SuppressWarnings("unused")
public class SimpleEntityManager implements TestRandomizers {
    private ApiClient api;

    private static final Logger logger = LoggerFactory.getLogger(SimpleEntityManager.class);
    private static final Map<Class, List<MetaEntity>> entityPoolMap = new HashMap<>();
    private static final Map<Class, Integer> accessCounterMap = new HashMap<>();

    private static Store mainStore = null;
    private static Organization ownOrganization = null;
    private static Currency firstCurrency = null;
    private static Group mainGroup = null;

    public SimpleEntityManager(ApiClient api) {
        this.api = api;
    }

    /**
     * Создает объект переданного класса с заполнением необходимых полей
     * путем вызова соответствующего createSimple* метода
     */
    public <T extends MetaEntity> T createSimple(Class<T> entityClass) {
        return createSimple(entityClass, false);
    }

    public <T extends MetaEntity> T createSimple(Class<T> entityClass, boolean forceCreate) {
        Method method;
        String methodName = "createSimple" + entityClass.getSimpleName();

        Integer accessCount = accessCounterMap.get(entityClass);
        List<MetaEntity> entityList = entityPoolMap.get(entityClass);

        if (accessCount == null || entityList == null) {
            accessCounterMap.put(entityClass, 0);
            entityPoolMap.put(entityClass, new ArrayList<>());
            accessCount = 0;
            entityList = entityPoolMap.get(entityClass);
        }

        Object entity;

        if (!forceCreate && accessCount < entityList.size()) {
            entity = entityList.get(entityList.size() - accessCount - 1);
            accessCounterMap.put(entityClass, accessCount+1);
            return entityClass.cast(entity);
        }

        try {
            method = this.getClass().getMethod(methodName);
            entity = method.invoke(this);
            entityList.add(entityClass.cast(entity));
            accessCounterMap.put(entityClass, accessCount+1);
        } catch (NoSuchMethodException e) {
            logger.error("Невозможно получить метод " + methodName);
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error("Невозможно вызвать метод " + methodName);
            throw new IllegalArgumentException(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error("Запрашиваемый метод " + methodName + " выдал исключение: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        return entityClass.cast(entity);
    }

    public <T extends MetaEntity> void removeSimpleFromPool(T entity) {
        List<MetaEntity> entityList = entityPoolMap.get(entity.getClass());
        if (entityList != null) {
            entityList.remove(entity);
        }
    }

    public Bundle createSimpleBundle() throws IOException, ApiClientException {
        Bundle bundle = new Bundle();
        bundle.setName("bundle_" + randomStringTail());
        bundle.setArticle(randomString());

        Product product = createSimple(Product.class);
        ListEntity<Bundle.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new Bundle.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        bundle.setComponents(components);

        api.entity().bundle().create(bundle);

        return bundle;
    }

    public Consignment createSimpleConsignment() throws IOException, ApiClientException {
        Consignment consignment = new Consignment();
        consignment.setLabel("consignment_" + randomStringTail());

        Product product = createSimple(Product.class);
        consignment.setAssortment(product);

        api.entity().consignment().create(consignment);

        return consignment;
    }

    public Contract createSimpleContract() throws IOException, ApiClientException {
        Contract contract = new Contract();
        contract.setName("contract_" + randomStringTail());

        contract.setOwnAgent(getOwnOrganization());
        contract.setAgent(createSimpleCounterparty());

        api.entity().contract().create(contract);

        return contract;
    }

    public Counterparty createSimpleCounterparty() throws IOException, ApiClientException {
        Counterparty counterparty = new Counterparty();
        counterparty.setName("counterparty_" + randomStringTail());
        counterparty.setCompanyType(CompanyType.legal);

        counterparty.setInn(randomString());
        counterparty.setOgrn(randomString());
        counterparty.setLegalAddress(randomString());
        counterparty.setLegalTitle(randomString());

        api.entity().counterparty().create(counterparty);

        return counterparty;
    }

    public Currency getFirstCurrency() throws IOException, ApiClientException {
        if (firstCurrency == null) {
            ListEntity<Currency> currencyEntityList = api.entity().currency().get();
            assertNotEquals(0, currencyEntityList.getRows().size());
            firstCurrency = currencyEntityList.getRows().get(0);
        }
        return firstCurrency;
    }

    public Currency createSimpleCurrency() throws IOException, ApiClientException {
        Currency currency = new Currency();
        currency.setName("currency_" + randomStringTail());
        currency.setCode(randomString(3));
        currency.setIsoCode(randomString(3));

        api.entity().currency().create(currency);

        return currency;
    }

    public CustomEntity createSimpleCustomEntity() throws IOException, ApiClientException {
        CustomEntity customEntity = new CustomEntity();

        customEntity.setName("custom_entity_" + randomStringTail());

        api.entity().customentity().create(customEntity);
        return customEntity;
    }

    public CustomEntityElement createSimpleCustomEntityElement(CustomEntity customEntity) throws IOException, ApiClientException {
        CustomEntityElement customEntityElement = new CustomEntityElement();

        customEntityElement.setName("custom_entity_element_" + randomStringTail());
        customEntityElement.setDescription("custom_entity_desc_" + randomString(3));
        customEntityElement.setExternalCode(randomString(3));

        api.entity().customentity().createCustomEntityElement(customEntity.getId(), customEntityElement);
        return customEntityElement;
    }

    public Employee getAdminEmployee() throws IOException, ApiClientException {
        Organization ownOrganization = getOwnOrganization();
        return ownOrganization.getOwner();
    }

    public Employee createSimpleEmployee() throws IOException, ApiClientException {
        Employee employee = new Employee();
        employee.setLastName("employee_" + randomStringTail());
        employee.setFirstName(randomString());
        employee.setMiddleName(randomString());

        api.entity().employee().create(employee);

        return employee;
    }

    public ExpenseItem createSimpleExpenseItem() throws IOException, ApiClientException {
        ExpenseItem expenseItem = new ExpenseItem();
        expenseItem.setName("expenseitem_" + randomStringTail());
        expenseItem.setDescription(randomString());

        api.entity().expenseitem().create(expenseItem);

        return expenseItem;
    }

    public Group getMainGroup() throws IOException, ApiClientException {
        if (mainGroup == null) {
            ListEntity<Group> group = api.entity().group().get(search("Основной"));
            assertEquals(1, group.getRows().size());
            mainGroup = group.getRows().get(0);
        }
        return mainGroup;
    }

    public Organization getOwnOrganization() throws IOException, ApiClientException {
        if (ownOrganization == null) {
            ListEntity<Organization> orgList = api.entity().organization().get();
            Optional<Organization> orgOptional = orgList.getRows().stream().
                    min(Comparator.comparing(Organization::getCreated));

            Organization organizationEntity;
            if (orgOptional.isPresent()) {
                ownOrganization = orgOptional.get();
            } else {
                throw new IllegalStateException("Не удалось получить первое созданное юрлицо");
            }
        }
        return ownOrganization;
    }

    public Organization createSimpleOrganization() throws IOException, ApiClientException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomStringTail());
        organization.setCompanyType(CompanyType.legal);
        organization.setInn(randomString());
        organization.setOgrn(randomString());

        api.entity().organization().create(organization);

        return organization;
    }

    public Product createSimpleProduct() throws IOException, ApiClientException {
        Product product = new Product();
        product.setName("product_" + randomStringTail());
        product.setDescription(randomString());

        api.entity().product().create(product);

        return product;
    }

    public ProductFolder createSimpleProductFolder() throws IOException, ApiClientException {
        ProductFolder productFolder = new ProductFolder();
        productFolder.setName("productfolder_" + randomStringTail());
        productFolder.setDescription(randomString());

        api.entity().productfolder().create(productFolder);

        return productFolder;
    }

    public Project createSimpleProject() throws IOException, ApiClientException {
        Project project = new Project();
        project.setName("project_" + randomStringTail());
        project.setDescription(randomString());

        api.entity().project().create(project);

        return project;
    }

    public Service createSimpleService() throws IOException, ApiClientException {
        Service service = new Service();
        service.setName("service_" + randomStringTail());
        service.setDescription(randomString());

        api.entity().service().create(service);

        return service;
    }

    public Store getMainStore() throws IOException, ApiClientException {
        if (mainStore == null) {
            ListEntity<Store> store = api.entity().store().get(filterEq("name", "Основной склад"));
            assertEquals(1, store.getRows().size());
            mainStore = store.getRows().get(0);
        }
        return mainStore;
    }

    public Store createSimpleStore() throws IOException, ApiClientException {
        Store store = new Store();
        store.setName("store_" + randomStringTail());
        store.setDescription(randomString());

        api.entity().store().create(store);

        return store;
    }

    public Task createSimpleTask() throws IOException, ApiClientException {
        Employee adminEmpl = getAdminEmployee();

        Task task = new Task();
        task.setDescription("task_" + randomStringTail());
        task.setAssignee(adminEmpl);
        api.entity().task().create(task);
        return task;
    }

    public Uom createSimpleUom() throws IOException, ApiClientException {
        Uom uom = new Uom();
        uom.setName("uom_" + randomStringTail());
        uom.setCode(randomString());
        uom.setExternalCode(randomString());

        api.entity().uom().create(uom);

        return uom;
    }

    public Variant createSimpleVariant() throws IOException, ApiClientException {
        Variant variant = new Variant();
        variant.setProduct(createSimple(Product.class));

        Variant.Characteristic characteristic = new Variant.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        variant.setCharacteristics(new ArrayList<>());
        variant.getCharacteristics().add(characteristic);

        api.entity().variant().create(variant);

        return variant;
    }

    public CashIn createSimpleCashIn() throws IOException, ApiClientException {
        CashIn cashIn = new CashIn();
        cashIn.setName("cashin_" + randomStringTail());
        cashIn.setDescription(randomString());
        cashIn.setOrganization(getOwnOrganization());
        cashIn.setAgent(createSimpleCounterparty());

        api.entity().cashin().create(cashIn);

        return cashIn;
    }

    public CashOut createSimpleCashOut() throws IOException, ApiClientException {
        CashOut cashOut = new CashOut();
        cashOut.setName("cashout_" + randomStringTail());
        cashOut.setDescription(randomString());
        cashOut.setOrganization(getOwnOrganization());
        cashOut.setAgent(createSimpleCounterparty());
        cashOut.setExpenseItem(createSimpleExpenseItem());

        api.entity().cashout().create(cashOut);

        return cashOut;
    }

    public CommissionReportIn createSimpleCommissionReportIn() throws IOException, ApiClientException {
        CommissionReportIn commissionReportIn = new CommissionReportIn();
        commissionReportIn.setName("commissionreportin_" + randomStringTail());
        Organization ownOrganization = getOwnOrganization();
        commissionReportIn.setOrganization(ownOrganization);
        Counterparty agent = createSimple(Counterparty.class);
        commissionReportIn.setAgent(agent);

        Contract contract = new Contract();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(agent);
        contract.setContractType(Contract.Type.commission);
        api.entity().contract().create(contract);
        commissionReportIn.setContract(contract);

        commissionReportIn.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportIn.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportin().create(commissionReportIn);

        return commissionReportIn;
    }

    public CommissionReportOut createSimpleCommissionReportOut() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = new CommissionReportOut();
        commissionReportOut.setName("commissionreportout_" + randomStringTail());
        Organization ownOrganization = getOwnOrganization();
        commissionReportOut.setOrganization(ownOrganization);
        Counterparty agent = createSimple(Counterparty.class);
        commissionReportOut.setAgent(agent);

        Contract contract = new Contract();
        contract.setName(randomString());
        contract.setOwnAgent(ownOrganization);
        contract.setAgent(agent);
        contract.setContractType(Contract.Type.commission);
        api.entity().contract().create(contract);
        commissionReportOut.setContract(contract);

        commissionReportOut.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportOut.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().create(commissionReportOut);

        return commissionReportOut;
    }

    public CustomerOrder createSimpleCustomerOrder() throws IOException, ApiClientException {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setName("customerorder_" + randomStringTail());
        customerOrder.setDescription(randomString());
        customerOrder.setOrganization(getOwnOrganization());
        customerOrder.setAgent(createSimpleCounterparty());

        api.entity().customerorder().create(customerOrder);

        return customerOrder;
    }

    public Demand createSimpleDemand() throws IOException, ApiClientException {
        Demand demand = new Demand();
        demand.setName("demand_" + randomStringTail());
        demand.setDescription(randomString());
        demand.setOrganization(getOwnOrganization());
        demand.setAgent(createSimpleCounterparty());
        demand.setStore(getMainStore());

        api.entity().demand().create(demand);

        return demand;
    }

    public Enter createSimpleEnter() throws IOException, ApiClientException {
        Enter enter = new Enter();
        enter.setName("enter_" + randomStringTail());
        enter.setDescription(randomString());
        enter.setOrganization(getOwnOrganization());
        enter.setStore(getMainStore());

        api.entity().enter().create(enter);

        return enter;
    }

    public FactureIn createSimpleFactureIn() throws IOException, ApiClientException {
        FactureIn factureIn = new FactureIn();
        factureIn.setName("facturein_" + randomStringTail());
        factureIn.setIncomingNumber(randomString());
        factureIn.setIncomingDate(LocalDateTime.now());

        List<Supply> supplies = new ArrayList<>();
        supplies.add(createSimpleSupply());
        factureIn.setSupplies(supplies);

        api.entity().facturein().create(factureIn);

        return factureIn;
    }

    public FactureOut createSimpleFactureOut() throws IOException, ApiClientException {
        FactureOut factureOut = new FactureOut();
        factureOut.setName("factureout_" + randomStringTail());
        factureOut.setPaymentNumber(randomString());
        factureOut.setPaymentDate(LocalDateTime.now());

        List<Demand> demands = new ArrayList<>();
        demands.add(createSimpleDemand());
        factureOut.setDemands(demands);

        api.entity().factureout().create(factureOut);

        return factureOut;
    }

    public InternalOrder createSimpleInternalOrder() throws IOException, ApiClientException {
        InternalOrder internalOrder = new InternalOrder();
        internalOrder.setName("internalorder_" + randomStringTail());
        internalOrder.setDescription(randomString());
        internalOrder.setOrganization(getOwnOrganization());
        internalOrder.setStore(getMainStore());

        api.entity().internalorder().create(internalOrder);

        return internalOrder;
    }

    public Inventory createSimpleInventory() throws IOException, ApiClientException {
        Inventory inventory = new Inventory();
        inventory.setName("inventory_" + randomStringTail());
        inventory.setOrganization(getOwnOrganization());
        inventory.setStore(getMainStore());

        api.entity().inventory().create(inventory);

        return inventory;
    }

    public InvoiceIn createSimpleInvoiceIn() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = new InvoiceIn();
        invoiceIn.setName("invoicein_" + randomStringTail());
        invoiceIn.setOrganization(getOwnOrganization());
        invoiceIn.setAgent(createSimpleCounterparty());

        api.entity().invoicein().create(invoiceIn);

        return invoiceIn;
    }

    public InvoiceOut createSimpleInvoiceOut() throws IOException, ApiClientException {
        InvoiceOut invoiceOut = new InvoiceOut();
        invoiceOut.setName("invoiceout_" + randomStringTail());
        invoiceOut.setOrganization(getOwnOrganization());
        invoiceOut.setAgent(createSimpleCounterparty());

        api.entity().invoiceout().create(invoiceOut);

        return invoiceOut;
    }

    public Loss createSimpleLoss() throws IOException, ApiClientException {
        Loss loss = new Loss();
        loss.setName("loss_" + randomStringTail());
        loss.setDescription(randomString());
        loss.setOrganization(getOwnOrganization());
        loss.setStore(getMainStore());

        api.entity().loss().create(loss);

        return loss;
    }

    public Move createSimpleMove() throws IOException, ApiClientException {
        Move move = new Move();
        move.setName("move_" + randomStringTail());
        move.setDescription(randomString());
        move.setOrganization(getOwnOrganization());
        move.setSourceStore(getMainStore());
        move.setTargetStore(createSimpleStore());

        api.entity().move().create(move);

        return move;
    }

    public PaymentIn createSimplePaymentIn() throws IOException, ApiClientException {
        PaymentIn paymentIn = new PaymentIn();
        paymentIn.setName("paymentin_" + randomStringTail());
        paymentIn.setOrganization(getOwnOrganization());
        paymentIn.setAgent(createSimpleCounterparty());

        api.entity().paymentin().create(paymentIn);

        return paymentIn;
    }

    public PaymentOut createSimplePaymentOut() throws IOException, ApiClientException {
        PaymentOut paymentOut = new PaymentOut();
        paymentOut.setName("paymentout_" + randomStringTail());
        paymentOut.setOrganization(getOwnOrganization());
        paymentOut.setAgent(createSimpleCounterparty());
        paymentOut.setExpenseItem(createSimpleExpenseItem());

        api.entity().paymentout().create(paymentOut);

        return paymentOut;
    }

    public Pricelist createSimplePricelist() throws IOException, ApiClientException {
        Pricelist priceList = new Pricelist();
        priceList.setName("pricelist_" + randomStringTail());

        List<Pricelist.ColumnsItem> columns = new ArrayList<>();
        Pricelist.ColumnsItem item = new Pricelist.ColumnsItem();
        item.setName(randomString());
        item.setPercentageDiscount(randomInteger(1, 10000));
        columns.add(item);
        priceList.setColumns(columns);

        Product product = createSimple(Product.class);
        ListEntity<Pricelist.PricelistRow> positions = new ListEntity<>();
        positions.setRows(new ArrayList<>());
        positions.getRows().add(new Pricelist.PricelistRow());
        positions.getRows().get(0).setAssortment(product);
        positions.getRows().get(0).setCells(new ArrayList<>());
        positions.getRows().get(0).getCells().add(new Pricelist.PricelistRow.CellsItem());
        positions.getRows().get(0).getCells().get(0).setColumn(columns.get(0).getName());
        positions.getRows().get(0).getCells().get(0).setSum(randomLong(1, 10000));
        priceList.setPositions(positions);

        api.entity().pricelist().create(priceList);

        return priceList;
    }

    public Processing createSimpleProcessing() throws IOException, ApiClientException {
        Processing processing = new Processing();
        processing.setName("processing_" + randomStringTail());
        processing.setOrganization(getOwnOrganization());

        processing.setMaterials(new ListEntity<>());
        processing.getMaterials().setRows(new ArrayList<>());
        Product material = createSimple(Product.class);
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(randomDouble(1, 5, 10));
        processing.getMaterials().getRows().add(materialPosition);

        processing.setProducts(new ListEntity<>());
        processing.getProducts().setRows(new ArrayList<>());
        Product product = createSimple(Product.class);
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(randomDouble(1, 5, 10));
        processing.getProducts().getRows().add(productPosition);

        processing.setProcessingSum(randomLong(10, 10000));
        processing.setQuantity(randomDouble(1, 5, 10));

        Store store = getMainStore();
        processing.setMaterialsStore(store);
        processing.setProductsStore(store);

        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomStringTail());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProcessingPlan.ProductItem materialItem = new ProcessingPlan.ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProcessingPlan.ProductItem productItem = new ProcessingPlan.ProductItem();
        productItem.setAssortment(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);
        processing.setProcessingPlan(processingPlan);

        api.entity().processing().create(processing);

        return processing;
    }

    public ProcessingOrder createSimpleProcessingOrder() throws IOException, ApiClientException {
        ProcessingOrder processingOrder = new ProcessingOrder();
        processingOrder.setName("processingorder_" + randomStringTail());
        processingOrder.setOrganization(getOwnOrganization());

        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomStringTail());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = createSimple(Product.class);
        ProcessingPlan.ProductItem materialItem = new ProcessingPlan.ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(randomDouble(1, 5, 4));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = createSimple(Product.class);
        ProcessingPlan.ProductItem productItem = new ProcessingPlan.ProductItem();
        productItem.setAssortment(product);
        productItem.setQuantity(randomDouble(1, 5, 4));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);
        processingOrder.setProcessingPlan(processingPlan);

        processingOrder.setPositions(new ListEntity<>());
        processingOrder.getPositions().setRows(new ArrayList<>());
        ProcessingOrderPosition position = new ProcessingOrderPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        processingOrder.getPositions().getRows().add(position);

        api.entity().processingorder().create(processingOrder);

        return processingOrder;
    }

    public ProcessingPlan createSimpleProcessingPlan() throws IOException, ApiClientException {
        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomStringTail());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = createSimple(Product.class);
        ProcessingPlan.ProductItem materialItem = new ProcessingPlan.ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = createSimple(Product.class);
        ProcessingPlan.ProductItem productItem = new ProcessingPlan.ProductItem();
        productItem.setAssortment(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);

        return processingPlan;
    }

    public ProcessingProcess createSimpleProcessingProcess() throws IOException, ApiClientException {
        ProcessingProcess processingProcess = new ProcessingProcess();
        processingProcess.setName("processingprocess_" + randomStringTail());
        processingProcess.setDescription(randomString());

        processingProcess.setPositions(new ListEntity<>());
        processingProcess.getPositions().setRows(new ArrayList<>());
        ProcessingProcess.ProcessPosition processPosition = new ProcessingProcess.ProcessPosition();
        processPosition.setProcessingstage(createSimpleProcessingStage());
        processingProcess.getPositions().getRows().add(processPosition);

        api.entity().processingProcess().create(processingProcess);
        return processingProcess;
    }

    public ProcessingStage createSimpleProcessingStage() throws IOException, ApiClientException {
        ProcessingStage processingStage = new ProcessingStage();
        processingStage.setName("processingstage_" + randomStringTail());
        processingStage.setDescription(randomString());
        api.entity().processingStage().create(processingStage);
        return processingStage;
    }

    public PurchaseOrder createSimplePurchaseOrder() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setName("purchaseorder_" + randomStringTail());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setOrganization(getOwnOrganization());
        purchaseOrder.setAgent(createSimpleCounterparty());

        api.entity().purchaseorder().create(purchaseOrder);

        return purchaseOrder;
    }

    public PurchaseReturn createSimplePurchaseReturn() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setName("purchasereturn_" + randomStringTail());
        purchaseReturn.setOrganization(getOwnOrganization());
        purchaseReturn.setAgent(createSimpleCounterparty());
        purchaseReturn.setStore(getMainStore());

        api.entity().purchasereturn().create(purchaseReturn);

        return purchaseReturn;
    }

    public RetailDemand createSimpleRetailDemand() throws IOException, ApiClientException {
        RetailDemand retailDemand = new RetailDemand();
        retailDemand.setName("retaildemand_" + randomStringTail());
        retailDemand.setDescription(randomString());
        retailDemand.setOrganization(getOwnOrganization());
        retailDemand.setAgent(createSimpleCounterparty());
        retailDemand.setStore(getMainStore());

        api.entity().retaildemand().create(retailDemand);

        return retailDemand;
    }

    public SalesReturn createSimpleSalesReturn() throws IOException, ApiClientException {
        SalesReturn salesReturn = new SalesReturn();
        salesReturn.setName("salesreturn_" + randomStringTail());
        salesReturn.setOrganization(getOwnOrganization());
        salesReturn.setAgent(createSimpleCounterparty());
        salesReturn.setStore(getMainStore());

        api.entity().salesreturn().create(salesReturn);

        return salesReturn;
    }

    public Supply createSimpleSupply() throws IOException, ApiClientException {
        Supply supply = new Supply();
        supply.setName("supply_" + randomStringTail());
        supply.setDescription(randomString());
        supply.setOrganization(getOwnOrganization());
        supply.setAgent(createSimpleCounterparty());
        supply.setStore(getMainStore());

        api.entity().supply().create(supply);

        return supply;
    }

    public Country createSimpleCountry() throws IOException, ApiClientException {
        Country country = new Country();
        country.setName("country_" + randomStringTail());
        country.setCode(randomString());

        api.entity().country().create(country);

        return country;
    }

    public WebHook createSimpleWebHook() throws IOException, ApiClientException {
        WebHook webHook = new WebHook();
        webHook.setEntityType(randomWebhookType());
        webHook.setAction(WebHook.EntityAction.CREATE);
        webHook.setMethod(WebHook.HttpMethod.POST);
        webHook.setEnabled(false);
        webHook.setUrl(randomUrl());

        api.entity().webhook().create(webHook);

        return webHook;
    }

    public RetailStore createSimpleRetailStore() throws IOException, ApiClientException {
        RetailStore retailStore = new RetailStore();
        retailStore.setName("retailstore_" + randomStringTail());
        retailStore.setOrganization(getOwnOrganization());
        retailStore.setStore(getMainStore());
        retailStore.setPriceType(api.entity().companysettings().pricetype().getDefault());

        return api.entity().retailstore().create(retailStore);
    }

    public State createSimpleState() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().counterparty().states().create(state);

        return state;
    }

    public void clearAccessCounts() {
        for (Map.Entry<Class, Integer> entry : accessCounterMap.entrySet()) {
            entry.setValue(0);
        }
    }
}
