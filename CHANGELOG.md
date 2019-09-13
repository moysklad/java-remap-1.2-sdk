# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 2.2-BETA (12.09.2019)
* Добавлены справочники:
    * `Assotment`
    * `Region`
    * `BonusProgram`
    * `BonusOperation`
* Добавлена поддержка публикаций (`Publication`) для документов и договоров 
* Добавлены уведомления (`Notification`)

## 2.1.2-BETA (05.08.2019)
* Исправлена ошибка при получении печатных форм `/export`
* Добавлено поле `trackingType` для `Product`

## 2.1.1-BETA (02.08.2019)
* `Product` - добавлено поле `trackingType`
* `Counterparty` - исправлен тип `priceType` на `PriceType`

## 2.1-BETA (02.08.2019)
* Справочники
    * Добавлен клиент для справочника `Country`
    * Добавлен клиент для справочника `PriceType`
* Переименованы некоторые классы и методы:
    * Сущности и их клиенты: `<сущность>Entity` → `<сущность>`
        * `AccountEntity` → `AgentAccount`
    * Документы и их клиенты: `<документ>DocumentEntity` → `<документ>`
    * `LognexApi` → `ApiClient`
    * Эндпоинты
        * `post` → `create`
        * `put` → `update`
* Добавлена поддержка для формирования `Meta` по `id` для вложенных сущностей при отправке запросов
* Фильтры по доп. полям и `href`
* Небольшие улучшения и доработки

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
