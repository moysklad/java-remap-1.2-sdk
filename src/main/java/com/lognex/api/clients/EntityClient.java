package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.documents.*;
import com.lognex.api.clients.endpoints.ApiChainElement;

public final class EntityClient {
    private final LognexApi api;

    public EntityClient(LognexApi api) {
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
    public DocumentCustomerOrderClient customerorder() {
        return new DocumentCustomerOrderClient(api);
    }

    @ApiChainElement
    public DocumentDemandClient demand() {
        return new DocumentDemandClient(api);
    }

    @ApiChainElement
    public DocumentDemandByIdClient demand(String id) {
        return new DocumentDemandByIdClient(api, id);
    }

    @ApiChainElement
    public DocumentEnterClient enter() {
        return new DocumentEnterClient(api);
    }

    @ApiChainElement
    public DocumentInternalOrderClient internalorder() {
        return new DocumentInternalOrderClient(api);
    }

    @ApiChainElement
    public DocumentLossClient loss() {
        return new DocumentLossClient(api);
    }

    @ApiChainElement
    public DocumentMoveClient move() {
        return new DocumentMoveClient(api);
    }

    @ApiChainElement
    public DocumentPurchaseOrderClient purchaseorder() {
        return new DocumentPurchaseOrderClient(api);
    }

    @ApiChainElement
    public DocumentProcessingOrderClient processingorder() {
        return new DocumentProcessingOrderClient(api);
    }

    @ApiChainElement
    public DocumentProcessingPlanClient processingplan() {
        return new DocumentProcessingPlanClient(api);
    }

    @ApiChainElement
    public DocumentSalesReturnClient salesreturn() {
        return new DocumentSalesReturnClient(api);
    }

    @ApiChainElement
    public DocumentPurchaseReturnClient purchasereturn() {
        return new DocumentPurchaseReturnClient(api);
    }

    @ApiChainElement
    public DocumentSupplyClient supply() {
        return new DocumentSupplyClient(api);
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
    public DocumentCashInClient cashin() {
        return new DocumentCashInClient(api);
    }

    @ApiChainElement
    public DocumentCashOutClient cashout() {
        return new DocumentCashOutClient(api);
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
    public DocumentRetailDemandClient retaildemand() {
        return new DocumentRetailDemandClient(api);
    }

    @ApiChainElement
    public DocumentRetailSalesReturnClient retailsalesreturn() {
        return new DocumentRetailSalesReturnClient(api);
    }

    @ApiChainElement
    public DocumentRetailDrawerCashInClient retaildrawercashin() {
        return new DocumentRetailDrawerCashInClient(api);
    }

    @ApiChainElement
    public DocumentRetailDrawerCashOutClient retaildrawercashout() {
        return new DocumentRetailDrawerCashOutClient(api);
    }

    @ApiChainElement
    public DocumentCommissionReportInClient commissionreportin() {
        return new DocumentCommissionReportInClient(api);
    }

    @ApiChainElement
    public DocumentInvoiceInClient invoicein() {
        return new DocumentInvoiceInClient(api);
    }

    @ApiChainElement
    public DocumentInvoiceOutClient invoiceout() {
        return new DocumentInvoiceOutClient(api);
    }

    @ApiChainElement
    public DocumentInventoryClient inventory() {
        return new DocumentInventoryClient(api);
    }

    @ApiChainElement
    public DocumentCommissionReportOutClient commissionreportout() {
        return new DocumentCommissionReportOutClient(api);
    }

    @ApiChainElement
    public DocumentPaymentInClient paymentin() {
        return new DocumentPaymentInClient(api);
    }

    @ApiChainElement
    public DocumentPaymentOutClient paymentout() {
        return new DocumentPaymentOutClient(api);
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
    public DocumentProcessingClient processing() {
        return new DocumentProcessingClient(api);
    }

    @ApiChainElement
    public DocumentFactureInClient facturein() {
        return new DocumentFactureInClient(api);
    }

    @ApiChainElement
    public DocumentFactureOutClient factureout() {
        return new DocumentFactureOutClient(api);
    }

    @ApiChainElement
    public DocumentPricelistClient pricelist() {
        return new DocumentPricelistClient(api);
    }

    @ApiChainElement
    public CustomEntityClient customentity() {
        return new CustomEntityClient(api);
    }

    @ApiChainElement
    public MetadataClient metadata() {
        return new MetadataClient(api);
    }

    @ApiChainElement
    public CompanySettingsClient companysettings() {
        return new CompanySettingsClient(api);
    }
}
