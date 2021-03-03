package ru.moysklad.remap_1_2.entities.permissions;

import ru.moysklad.remap_1_2.entities.permissions.value.BaseEntityPermissionValue;
import ru.moysklad.remap_1_2.entities.permissions.value.ScriptPermissionValue;
import ru.moysklad.remap_1_2.entities.permissions.value.DictionaryEntityPermissionValue;
import ru.moysklad.remap_1_2.entities.permissions.value.OperationEntityPermissionValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeePermissions {

    /**
     * User permissions
     * */

    private Boolean importData;
    private Boolean exportData;
    private Boolean onlineShops;
    private Boolean apiRequest;
    private Boolean sendEmail;
    private Boolean viewProductCostAndProfit;
    private Boolean viewDashboard;
    private Boolean viewRecycleBin;
    private Boolean viewAudit;
    private Boolean viewSaleProfit;
    private Boolean viewCommissionGoods;
    private Boolean viewPurchaseFunnel;
    private Boolean viewStockReport;
    private Boolean viewTurnover;
    private Boolean viewSerialNumbers;
    private Boolean viewCashFlow;
    private Boolean viewCustomerBalanceList;
    private Boolean viewProfitAndLoss;
    private Boolean viewCompanyCRM;
    private Boolean viewMoneyDashboard;
    private Boolean restoreFromRecycleBin;
    private Boolean deleteFromRecycleBin;
    private Boolean editDocumentsOfRestrictedPeriod;
    private Boolean editDocumentTemplates;
    private Boolean editCurrencyRateOfDocument;
    private Boolean subscriptionControl;
    private Boolean purchaseControl;
    private Boolean listenCalls;

    /**
     * Entity permissions
     * */

    private DictionaryEntityPermissionValue company;
    private BaseEntityPermissionValue myCompany;
    private DictionaryEntityPermissionValue good;
    private BaseEntityPermissionValue project;
    private DictionaryEntityPermissionValue contract;
    private BaseEntityPermissionValue employee;
    private BaseEntityPermissionValue currency;
    private BaseEntityPermissionValue warehouse;
    private BaseEntityPermissionValue customEntity;
    private BaseEntityPermissionValue retailStore;
    private BaseEntityPermissionValue country;
    private BaseEntityPermissionValue uom;
    private OperationEntityPermissionValue purchaseReturn;
    private OperationEntityPermissionValue demand;
    private OperationEntityPermissionValue salesReturn;
    private OperationEntityPermissionValue loss;
    private OperationEntityPermissionValue enter;
    private OperationEntityPermissionValue move;
    private DictionaryEntityPermissionValue inventory;
    private BaseEntityPermissionValue processing;
    private OperationEntityPermissionValue invoiceIn;
    private OperationEntityPermissionValue invoiceOut;
    private OperationEntityPermissionValue purchaseOrder;
    private OperationEntityPermissionValue customerOrder;
    private OperationEntityPermissionValue internalOrder;
    private OperationEntityPermissionValue processingOrder;
    private OperationEntityPermissionValue factureIn;
    private OperationEntityPermissionValue factureOut;
    private OperationEntityPermissionValue paymentIn;
    private OperationEntityPermissionValue paymentOut;
    private OperationEntityPermissionValue cashIn;
    private OperationEntityPermissionValue cashOut;
    private OperationEntityPermissionValue priceList;
    private OperationEntityPermissionValue retailDemand;
    private OperationEntityPermissionValue retailSalesReturn;
    private OperationEntityPermissionValue supply;
    private BaseEntityPermissionValue processingPlan;
    private OperationEntityPermissionValue commissionReportIn;
    private OperationEntityPermissionValue commissionReportOut;
    private DictionaryEntityPermissionValue retailShift;
    private OperationEntityPermissionValue retailDrawerCashIn;
    private OperationEntityPermissionValue retailDrawerCashOut;
    private OperationEntityPermissionValue bonusTransaction;
    private OperationEntityPermissionValue prepayment;
    private OperationEntityPermissionValue prepaymentReturn;
    private DictionaryEntityPermissionValue cashboxAdjustment;
    private DictionaryEntityPermissionValue accountAdjustment;
    private DictionaryEntityPermissionValue counterpartyAdjustment;

    /**
     * Script permissions
     * */

    private ScriptPermissionValue script;
}
