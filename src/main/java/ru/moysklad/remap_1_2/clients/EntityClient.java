package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.documents.*;
import ru.moysklad.remap_1_2.clients.endpoints.ApiChainElement;

public final class EntityClient {
    private final ApiClient api;

    public EntityClient(ApiClient api) {
        this.api = api;
    }

    @ApiChainElement
    public CounterpartyClient counterparty() {
        return new CounterpartyClient(api);
    }

    @ApiChainElement
    public OrganizationClient organization() {
        return new OrganizationClient(api);
    }

    @ApiChainElement
    public GroupClient group() {
        return new GroupClient(api);
    }

    @ApiChainElement
    public EmployeeClient employee() {
        return new EmployeeClient(api);
    }

    @ApiChainElement
    public CustomerOrderClient customerorder() {
        return new CustomerOrderClient(api);
    }

    @ApiChainElement
    public DemandClient demand() {
        return new DemandClient(api);
    }

    @ApiChainElement
    public EnterClient enter() {
        return new EnterClient(api);
    }

    @ApiChainElement
    public InternalOrderClient internalorder() {
        return new InternalOrderClient(api);
    }

    @ApiChainElement
    public LossClient loss() {
        return new LossClient(api);
    }

    @ApiChainElement
    public MoveClient move() {
        return new MoveClient(api);
    }

    @ApiChainElement
    public PurchaseOrderClient purchaseorder() {
        return new PurchaseOrderClient(api);
    }

    @ApiChainElement
    public ProcessingOrderClient processingorder() {
        return new ProcessingOrderClient(api);
    }

    @ApiChainElement
    public ProcessingPlanClient processingplan() {
        return new ProcessingPlanClient(api);
    }

    @ApiChainElement
    public SalesReturnClient salesreturn() {
        return new SalesReturnClient(api);
    }

    @ApiChainElement
    public PurchaseReturnClient purchasereturn() {
        return new PurchaseReturnClient(api);
    }

    @ApiChainElement
    public SupplyClient supply() {
        return new SupplyClient(api);
    }

    @ApiChainElement
    public StoreClient store() {
        return new StoreClient(api);
    }

    @ApiChainElement
    public ProductClient product() {
        return new ProductClient(api);
    }

    @ApiChainElement
    public VariantClient variant() {
        return new VariantClient(api);
    }

    @ApiChainElement
    public CurrencyClient currency() {
        return new CurrencyClient(api);
    }

    @ApiChainElement
    public DiscountClient discount() {
        return new DiscountClient(api);
    }

    @ApiChainElement
    public ContractClient contract() {
        return new ContractClient(api);
    }

    @ApiChainElement
    public ConsignmentClient consignment() {
        return new ConsignmentClient(api);
    }

    @ApiChainElement
    public ProductFolderClient productfolder() {
        return new ProductFolderClient(api);
    }

    @ApiChainElement
    public ServiceClient service() {
        return new ServiceClient(api);
    }

    @ApiChainElement
    public BundleClient bundle() {
        return new BundleClient(api);
    }

    @ApiChainElement
    public UomClient uom() {
        return new UomClient(api);
    }

    @ApiChainElement
    public CashInClient cashin() {
        return new CashInClient(api);
    }

    @ApiChainElement
    public CashOutClient cashout() {
        return new CashOutClient(api);
    }

    @ApiChainElement
    public RetailShiftClient retailshift() {
        return new RetailShiftClient(api);
    }

    @ApiChainElement
    public RetailStoreClient retailstore() {
        return new RetailStoreClient(api);
    }

    @ApiChainElement
    public RetailDemandClient retaildemand() {
        return new RetailDemandClient(api);
    }

    @ApiChainElement
    public RetailSalesReturnClient retailsalesreturn() {
        return new RetailSalesReturnClient(api);
    }

    @ApiChainElement
    public RetailDrawerCashInClient retaildrawercashin() {
        return new RetailDrawerCashInClient(api);
    }

    @ApiChainElement
    public RetailDrawerCashOutClient retaildrawercashout() {
        return new RetailDrawerCashOutClient(api);
    }

    @ApiChainElement
    public CommissionReportInClient commissionreportin() {
        return new CommissionReportInClient(api);
    }

    @ApiChainElement
    public InvoiceInClient invoicein() {
        return new InvoiceInClient(api);
    }

    @ApiChainElement
    public InvoiceOutClient invoiceout() {
        return new InvoiceOutClient(api);
    }

    @ApiChainElement
    public InventoryClient inventory() {
        return new InventoryClient(api);
    }

    @ApiChainElement
    public CommissionReportOutClient commissionreportout() {
        return new CommissionReportOutClient(api);
    }

    @ApiChainElement
    public PaymentInClient paymentin() {
        return new PaymentInClient(api);
    }

    @ApiChainElement
    public PaymentOutClient paymentout() {
        return new PaymentOutClient(api);
    }

    @ApiChainElement
    public ProjectClient project() {
        return new ProjectClient(api);
    }

    @ApiChainElement
    public ExpenseItemClient expenseitem() {
        return new ExpenseItemClient(api);
    }

    @ApiChainElement
    public ProcessingClient processing() {
        return new ProcessingClient(api);
    }

    @ApiChainElement
    public FactureInClient facturein() {
        return new FactureInClient(api);
    }

    @ApiChainElement
    public FactureOutClient factureout() {
        return new FactureOutClient(api);
    }

    @ApiChainElement
    public PricelistClient pricelist() {
        return new PricelistClient(api);
    }

    @ApiChainElement
    public CustomEntityClient customentity() {
        return new CustomEntityClient(api);
    }

    @ApiChainElement
    public CountryClient country() {
        return new CountryClient(api);
    }

    @ApiChainElement
    public MetadataClient metadata() {
        return new MetadataClient(api);
    }

    @ApiChainElement
    public CompanySettingsClient companysettings() {
        return new CompanySettingsClient(api);
    }

    @ApiChainElement
    public TaskClient task() {
        return new TaskClient(api);
    }

    @ApiChainElement
    public BonusProgramClient bonusprogram() {
        return new BonusProgramClient(api);
    }

    @ApiChainElement
    public BonusTransactionClient bonustransaction() {
        return new BonusTransactionClient(api);
    }

    @ApiChainElement
    public PersonalDiscountClient personaldiscount() {
        return new PersonalDiscountClient(api);
    }

    @ApiChainElement
    public AccumulationDiscountClient accumulationdiscount() {
        return new AccumulationDiscountClient(api);
    }

    @ApiChainElement
    public RoundOffDiscountClient roundoffdiscount() {
        return new RoundOffDiscountClient(api);
    }

    @ApiChainElement
    public SpecialPriceDiscountClient specialpricediscount() {
        return new SpecialPriceDiscountClient(api);
    }

    @ApiChainElement
    public RegionClient region() {
        return new RegionClient(api);
    }

    @ApiChainElement
    public AssortmentClient assortment() {
        return new AssortmentClient(api);
    }

    @ApiChainElement
    public WebHookClient webhook() {
        return new WebHookClient(api);
    }

    @ApiChainElement
    public TokenClient token() {
        return new TokenClient(api);
    }
}
