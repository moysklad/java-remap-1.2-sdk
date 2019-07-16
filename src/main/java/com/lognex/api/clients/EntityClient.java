package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.documents.*;
import com.lognex.api.clients.endpoints.ApiChainElement;

public final class EntityClient {
    private final ApiClient api;

    public EntityClient(ApiClient api) {
        this.api = api;
    }

    @ApiChainElement
    public CounterpartyClientEntity counterparty() {
        return new CounterpartyClientEntity(api);
    }

    @ApiChainElement
    public OrganizationClientEntity organization() {
        return new OrganizationClientEntity(api);
    }

    @ApiChainElement
    public GroupClientEntity group() {
        return new GroupClientEntity(api);
    }

    @ApiChainElement
    public EmployeeClientEntity employee() {
        return new EmployeeClientEntity(api);
    }

    @ApiChainElement
    public DocumentCustomerOrderClientEntity customerorder() {
        return new DocumentCustomerOrderClientEntity(api);
    }

    @ApiChainElement
    public DocumentDemandClientEntity demand() {
        return new DocumentDemandClientEntity(api);
    }

    @ApiChainElement
    public DocumentEnterClientEntity enter() {
        return new DocumentEnterClientEntity(api);
    }

    @ApiChainElement
    public DocumentInternalOrderClientEntity internalorder() {
        return new DocumentInternalOrderClientEntity(api);
    }

    @ApiChainElement
    public DocumentLossClientEntity loss() {
        return new DocumentLossClientEntity(api);
    }

    @ApiChainElement
    public DocumentMoveClientEntity move() {
        return new DocumentMoveClientEntity(api);
    }

    @ApiChainElement
    public DocumentPurchaseOrderClientEntity purchaseorder() {
        return new DocumentPurchaseOrderClientEntity(api);
    }

    @ApiChainElement
    public DocumentProcessingOrderClientEntity processingorder() {
        return new DocumentProcessingOrderClientEntity(api);
    }

    @ApiChainElement
    public DocumentProcessingPlanClientEntity processingplan() {
        return new DocumentProcessingPlanClientEntity(api);
    }

    @ApiChainElement
    public DocumentSalesReturnClientEntity salesreturn() {
        return new DocumentSalesReturnClientEntity(api);
    }

    @ApiChainElement
    public DocumentPurchaseReturnClientEntity purchasereturn() {
        return new DocumentPurchaseReturnClientEntity(api);
    }

    @ApiChainElement
    public DocumentSupplyClientEntity supply() {
        return new DocumentSupplyClientEntity(api);
    }

    @ApiChainElement
    public StoreClientEntity store() {
        return new StoreClientEntity(api);
    }

    @ApiChainElement
    public ProductClientEntity product() {
        return new ProductClientEntity(api);
    }

    @ApiChainElement
    public VariantClientEntity variant() {
        return new VariantClientEntity(api);
    }

    @ApiChainElement
    public CurrencyClientEntity currency() {
        return new CurrencyClientEntity(api);
    }

    @ApiChainElement
    public DiscountClientEntity discount() {
        return new DiscountClientEntity(api);
    }

    @ApiChainElement
    public ContractClientEntity contract() {
        return new ContractClientEntity(api);
    }

    @ApiChainElement
    public ConsignmentClientEntity consignment() {
        return new ConsignmentClientEntity(api);
    }

    @ApiChainElement
    public ProductFolderClientEntity productfolder() {
        return new ProductFolderClientEntity(api);
    }

    @ApiChainElement
    public ServiceClientEntity service() {
        return new ServiceClientEntity(api);
    }

    @ApiChainElement
    public BundleClientEntity bundle() {
        return new BundleClientEntity(api);
    }

    @ApiChainElement
    public UomClientEntity uom() {
        return new UomClientEntity(api);
    }

    @ApiChainElement
    public DocumentCashInClientEntity cashin() {
        return new DocumentCashInClientEntity(api);
    }

    @ApiChainElement
    public DocumentCashOutClientEntity cashout() {
        return new DocumentCashOutClientEntity(api);
    }

    @ApiChainElement
    public RetailShiftClientEntity retailshift() {
        return new RetailShiftClientEntity(api);
    }

    @ApiChainElement
    public RetailStoreClientEntity retailstore() {
        return new RetailStoreClientEntity(api);
    }

    @ApiChainElement
    public DocumentRetailDemandClientEntity retaildemand() {
        return new DocumentRetailDemandClientEntity(api);
    }

    @ApiChainElement
    public DocumentRetailSalesReturnClientEntity retailsalesreturn() {
        return new DocumentRetailSalesReturnClientEntity(api);
    }

    @ApiChainElement
    public DocumentRetailDrawerCashInClientEntity retaildrawercashin() {
        return new DocumentRetailDrawerCashInClientEntity(api);
    }

    @ApiChainElement
    public DocumentRetailDrawerCashOutClientEntity retaildrawercashout() {
        return new DocumentRetailDrawerCashOutClientEntity(api);
    }

    @ApiChainElement
    public DocumentCommissionReportInClientEntity commissionreportin() {
        return new DocumentCommissionReportInClientEntity(api);
    }

    @ApiChainElement
    public DocumentInvoiceInClientEntity invoicein() {
        return new DocumentInvoiceInClientEntity(api);
    }

    @ApiChainElement
    public DocumentInvoiceOutClientEntity invoiceout() {
        return new DocumentInvoiceOutClientEntity(api);
    }

    @ApiChainElement
    public DocumentInventoryClientEntity inventory() {
        return new DocumentInventoryClientEntity(api);
    }

    @ApiChainElement
    public DocumentCommissionReportOutClientEntity commissionreportout() {
        return new DocumentCommissionReportOutClientEntity(api);
    }

    @ApiChainElement
    public DocumentPaymentInClientEntity paymentin() {
        return new DocumentPaymentInClientEntity(api);
    }

    @ApiChainElement
    public DocumentPaymentOutClientEntity paymentout() {
        return new DocumentPaymentOutClientEntity(api);
    }

    @ApiChainElement
    public ProjectClientEntity project() {
        return new ProjectClientEntity(api);
    }

    @ApiChainElement
    public ExpenseItemClientEntity expenseitem() {
        return new ExpenseItemClientEntity(api);
    }

    @ApiChainElement
    public DocumentProcessingClientEntity processing() {
        return new DocumentProcessingClientEntity(api);
    }

    @ApiChainElement
    public DocumentFactureInClientEntity facturein() {
        return new DocumentFactureInClientEntity(api);
    }

    @ApiChainElement
    public DocumentFactureOutClientEntity factureout() {
        return new DocumentFactureOutClientEntity(api);
    }

    @ApiChainElement
    public DocumentPricelistClientEntity pricelist() {
        return new DocumentPricelistClientEntity(api);
    }

    @ApiChainElement
    public CustomEntityClientEntity customentity() {
        return new CustomEntityClientEntity(api);
    }

    @ApiChainElement
    public MetadataClientEntity metadata() {
        return new MetadataClientEntity(api);
    }

    @ApiChainElement
    public CompanySettingsClientEntity companysettings() {
        return new CompanySettingsClientEntity(api);
    }

    @ApiChainElement
    public TaskClientEntity task() {
        return new TaskClientEntity(api);
    }
}
