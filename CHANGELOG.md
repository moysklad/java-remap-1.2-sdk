# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 1.0-BETA (18.06.2019)
### Реализовано
* Справочники
    * `Bundle`
    * `CompanySettings`
    * `Consignment`
    * `Contract`
    * `Counterparty`
    * `Currency`
    * `CustomEntity`
    * `Discount` (Без бонусных программ)
    * `Employee`
    * `ExpenseItem`
    * `Group`
    * `Organization`
    * `Product`
    * `ProductFolder`
    * `Project`
    * `RetailStore`
    * `Service`
    * `Store`
    * `Task`
    * `Uom`
    * `Variant`
* Документы
    * `CashIn`
    * `CashOut`
    * `CommissionReportIn`
    * `CommissionReportOut`
    * `CustomerOrder`
    * `Demand`
    * `Enter`
    * `FactureIn`
    * `FactureOut`
    * `InternalOrder`
    * `Inventory`
    * `InvoiceIn`
    * `InvoiceOut`
    * `Loss`
    * `Move`
    * `PaymentIn`
    * `PaymentOut`
    * `Pricelist`
    * `Processing`
    * `ProcessingOrder`
    * `ProcessingPlan`
    * `PurchaseOrder`
    * `PurchaseReturn`
    * `RetailDemand`
    * `RetailDrawerCashIn`
    * `RetailDrawerCashOut`
    * `RetailSalesReturn`
    * `RetailShift`
    * `SalesReturn`
    * `Supply`
    * Позиции документов (кроме `Pricelist`) представлены общим классом `DocumentPosition`
* Методы (если сущность их поддерживает, без массовых операций)
    * `post`
    * `get` с параметрами:
        * `expand`
        * `filter`
        * `limit`
        * `offset`
        * `order`
        * `search`
    * `get/{id}`
    * `put/{id}`
    * `delete/{id}`
    * `new` - для документов
    * `export` - для документов
