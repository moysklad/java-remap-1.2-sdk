package com.lognex.api.clients;

import com.lognex.api.LognexApi;

public final class EntityClient {
    private final LognexApi api;

    public EntityClient(LognexApi api) {
        this.api = api;
    }

    public CounterpartyClient counterparty() {
        return new CounterpartyClient(api);
    }

    public CounterpartyByIdClient counterparty(String id) {
        return new CounterpartyByIdClient(api, id);
    }

    public OrganizationClient organization() {
        return new OrganizationClient(api);
    }

    public GroupClient group() {
        return new GroupClient(api);
    }

    public EmployeeClient employee() {
        return new EmployeeClient(api);
    }

    public DocumentCustomerOrderClient customerorder() {
        return new DocumentCustomerOrderClient(api);
    }

    public DocumentDemandClient demand() {
        return new DocumentDemandClient(api);
    }

    public DocumentEnterClient enter() {
        return new DocumentEnterClient(api);
    }

    public DocumentInternalOrderClient internalorder() {
        return new DocumentInternalOrderClient(api);
    }

    public DocumentLossClient loss() {
        return new DocumentLossClient(api);
    }

    public DocumentMoveClient move() {
        return new DocumentMoveClient(api);
    }

    public DocumentPurchaseOrderClient purchaseorder() {
        return new DocumentPurchaseOrderClient(api);
    }

    public DocumentProcessingOrderClient processingorder() {
        return new DocumentProcessingOrderClient(api);
    }

    public DocumentProcessingPlanClient processingplan() {
        return new DocumentProcessingPlanClient(api);
    }

    public DocumentSalesReturnClient salesreturn() {
        return new DocumentSalesReturnClient(api);
    }

    public DocumentPurchaseReturnClient purchasereturn() {
        return new DocumentPurchaseReturnClient(api);
    }

    public DocumentSupplyClient supply() {
        return new DocumentSupplyClient(api);
    }

    public StoreClient store() {
        return new StoreClient(api);
    }

    public ProductClient product() {
        return new ProductClient(api);
    }

    public VariantClient variant() {
        return new VariantClient(api);
    }

    public VariantByIdClient variant(String id) {
        return new VariantByIdClient(api, id);
    }

    public CurrencyClient currency() {
        return new CurrencyClient(api);
    }

    public DiscountClient discount() {
        return new DiscountClient(api);
    }

    public ContractClient contract() {
        return new ContractClient(api);
    }

    public ConsignmentClient consignment() {
        return new ConsignmentClient(api);
    }

    public ProductFolderClient productfolder() {
        return new ProductFolderClient(api);
    }

    public ServiceClient service() {
        return new ServiceClient(api);
    }

    public BundleClient bundle() {
        return new BundleClient(api);
    }

    public UomClient uom() {
        return new UomClient(api);
    }

    public DocumentCashInClient cashin() {
        return new DocumentCashInClient(api);
    }

    public DocumentCashOutClient cashout() {
        return new DocumentCashOutClient(api);
    }

    public RetailShiftClient retailshift() {
        return new RetailShiftClient(api);
    }

    public RetailStoreClient retailstore() {
        return new RetailStoreClient(api);
    }

    public DocumentRetailDemandClient retaildemand() {
        return new DocumentRetailDemandClient(api);
    }

    public DocumentRetailSalesReturnClient retailsalesreturn() {
        return new DocumentRetailSalesReturnClient(api);
    }

    public DocumentRetailDrawerCashInClient retaildrawercashin() {
        return new DocumentRetailDrawerCashInClient(api);
    }

    public DocumentRetailDrawerCashOutClient retaildrawercashout() {
        return new DocumentRetailDrawerCashOutClient(api);
    }

    public DocumentCommissionReportInClient commissionreportin() {
        return new DocumentCommissionReportInClient(api);
    }

    public DocumentInvoiceInClient invoicein() {
        return new DocumentInvoiceInClient(api);
    }

    public DocumentInvoiceOutClient invoiceout() {
        return new DocumentInvoiceOutClient(api);
    }

    public DocumentInventoryClient inventory() {
        return new DocumentInventoryClient(api);
    }

    public DocumentCommissionReportOutClient commissionreportout() {
        return new DocumentCommissionReportOutClient(api);
    }

    public DocumentPaymentInClient paymentin() {
        return new DocumentPaymentInClient(api);
    }

    public DocumentPaymentOutClient paymentout() {
        return new DocumentPaymentOutClient(api);
    }

    public ProjectClient project() {
        return new ProjectClient(api);
    }

    public ExpenseItemClient expenseitem() {
        return new ExpenseItemClient(api);
    }

    public DocumentProcessingClient processing() {
        return new DocumentProcessingClient(api);
    }
}
