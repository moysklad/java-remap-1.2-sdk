# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 2.3-BETA (27.07.2020)
* Добавлено массовое создание / удаление сущностей
* Добавлена возможность изменять настройки компании
* Добавлена работа со скидками
* Разделён DocumentPosition для разных типов документов
* Актуализированы поля документов 
 
## 1.1-release (08.06.2020)
* Изменены типы полей `tiny` и `miniature` в справочнике `Image` c `MetaEntity` на `Meta`
* Добавлено поле `reserve` в `DocumentPosition`
* Добавлен тип штрихкодов `UPC` в `Barcode.Type`


## 2.2-BETA (12.09.2019)
* Добавлены справочники:
    * `Assortment`
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
