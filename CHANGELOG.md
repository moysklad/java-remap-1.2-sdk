# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 7.1-release (03.02.2025)
* Добавлено поле `standardHourcost` в `ProcessingStage`

## 6.0-release (18.07.2024)
* Изменен тип и поле с `Long` и `wait` на `Double` и `inTransit` в `PurchaseOrderDocumentPosition` 

## 5.0-release (17.06.2024)
* Исправлено обновление информации о правах сотрудника `entity/employee/{id}/security`

## 4.29-release (10.06.2024)
* Добавлено поле `standardHour` в `ProcessingPlan`

## 4.28-release (25.04.2024)
* Добавлены операции `get`, `create` для сущностей Производства `ProcessingStage`, `ProcessingProcess` и `ProcessingPlan`

## 4.20-release (11.03.2024)
* Добавлено поле `sendMarksToChestnyZnakOnCloud` в `RetailStore`

## 4.19-release (07.02.2024)
* Добавлено поле `marksCheckMode` в `RetailStore`

## 4.18-release (05.12.2023)
* Добавлено поле `showBeerOnTap` в `RetailStore`

## 4.17-release (03.10.2023)
* Добавлено поле `syncAgents` в `Retailstore`

## 4.16-release (28.09.2023)
* Добавлен обязательный заголовок сжатия `Accept-Encoding:gzip` для всех запросов
* В README обновлен домен remap-12 на `api.moysklad.ru`

## 4.15-release (31.08.2023)
* Добавлено поле `allowDeleteReceiptPositions` в `RetailStore`

## 4.14-release (10.08.2023)
* Добавлены поля `sex`, `birthDate` в `Counterparty`

## 4.11-release (14.09.2022)
* Добавлено поле `discountProhibited` в `Service`, `Bundle`

## 4.5-release (02.03.2022)
* Добавлены поля `markingSellingMode`, `sendMarksForCheck` в `RetailStore`

## 4.0-release (23.09.2021)
* Изменен тип значения цены на Double

## 3.2-release (11.06.2021)
* Add attachment files support

## 3.0-release (14.04.2021)
* Удалена ошибочно добавленная возможность работы с доп. полями в productFolder, variant
* Исправлена работа с доп. полями для bundle и service.

## 2.4-release (16.03.2021)
* Добавлено поле `partialDisposal` в сущности `Product` и `Bundle`

## 2.3-release (4.03.2021)
* Релиз в maven репозиторий

## 2.2-release (3.03.2021)
* Добавлены эндпоинты управления правами сотрудника `HasPermissionsEndpoint` и управления доступом сотрудника 
к основному сервису МойСклад `HasAccessManagmentEndpoint`

## 2.1-release (10.02.2021)
* Добавлены эндпоинты на создание/обновление/удаление доп. полей у сущностей с доп. полями
* Добавлены клиенты `Prepayment` и `PrepaymentReturn`
* Добавлены поля `Description` и `CustomEntityMeta` в сущность `Attribute`

## 2.0-release (15.01.2021)
* Изменен artifactId с java-remap-sdk на api-remap-1.2-sdk
* Изменён базовый пакет с `com.lognex.api.*` на `ru.moysklad.remap_1_2.*`.

## 1.14-release (26.01.2021)
* artifactId изменен с api-remap-1.2-sdk на java-remap-sdk

## 1.13-release (05.11.2020)
* Добавлены поля `printed` и `published` во все документы

## 1.12-release (10.12.2020)
* Добавлен запрос доп. полей документов

## 1.11-release (2.12.2020)
* Добавлен эндпоинт `states` для документов, где он отсутствовал и поле `project` для `InvoiceIn`

## 1.10-release (25.11.2020)
* Добавлены поля `fiscalType`, `qrAcqire`, `qrPayEnabled`, `qrBankPercent`, `minionToMasterType`, `masterRetailStores` в `RetailStore`
* Добавлено поле `ppeType` в `Product`
* Добавлены поля `qrSum` и `prepaymentQrSum` в `RetailDemand`
* Добавлено поле `qrSum` в `RetailSalesReturn`, `Prepayment`, `PrepaymentReturn` 
* Добавлены новые значения в справочник `TrackingType`

## 1.9-release (05.11.2020)
* Добавлены настройки справочника контрагентов `CounterpartySettings` и ассортимента `AssortmentSettings`

## 1.8-release (02.11.2020)
* Поправлена десериализация позиций документа, если они находятся внутри `ListEntity`

## 1.7-release (23.10.2020)
* Добавлена возможность создавать и изменять отделы (`Group`)
* Добавлена возможность поиска товара по `syncId`

## 1.6-release (08.10.2020)
* Добавлено поле `description` у задачи

## 1.5-release (07.10.2020)
* Исправлена сериализация поля `operation` у Задачи

## 1.4-release (14.09.2020)
* Добавлено поле `things` в позициях Отгрузки

## 1.3-release (29.07.2020)
* Добавлено возможность создания нового токета через /security/token
* Добавлен справочник: `productfolder`

## 1.2-release (27.07.2020)
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
