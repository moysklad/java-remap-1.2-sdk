# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 2.0-ALPHA8 (03.12.2018)
### Совместимость
* `metadata()` теперь возвращает `DocumentMetadataClient`
* `expand` из параметров метода `get()` переоформлен в виде списка API-параметров `expand()`
* Все эндпоинты `[entity]([entity.id]).method(...)` заменены на `[entity].method([entity.id], ...)`

# Добавлено
* Аттрибуты выборки для `get()` запросов:
  * expand
  * filter
  * limit
  * offset
  * order
  * search
* Сущности:
  * `CashierEntity`
  * `ContactPersonEntity`
  * `ExportRequestEntity`
  * `NoteEntity`
  * `ProductImageEntity`
  * `TemplateEntity`
  * `WebHookEntity`
  * Скидки
    * `AccumulationDiscountEntity`
    * `BonusProgramDiscountEntity`
    * `DiscountEntity`
    * `PersonalDiscountEntity`
    * `SpecialPriceDiscountEntity`
  * Документы
    * `FactureInDocumentEntity`
    * `FactureOutDocumentEntity`
    * `PricelistDocumentEntity`
* Сущности и клиенты для
  * `CompanySettings`
  * `CustomEntity`
  * `Metadata`
* Конечные звенья запросов
  * `DocumentMetadataEndpoint`
  * `DocumentNewEndpoint`
  * `DocumentPositionsEndpoint`
  * `ExportEndpoint`
  * `GetByIdEndpoint`
  * `MetadataAttributeEndpoint`
  * `MetadataTemplatesEndpoint`
  * `PostByIdEndpoint`
  * `PutByIdEndpoint`

## 2.0-ALPHA7.2.1 (01.11.2018)
### Исправлено
* Ошибка с `PutEndpoint` 

## 2.0-ALPHA7.2 (17.10.2018)
### Добавлено
* Возможность отключать форсирование HTTPS при создании экземпляра LognexApi 

## 2.0-ALPHA7.1 (15.10.2018)

### Добавлено
* Аннотации Lombok над сущностью `AlcoholEntity`
* Поля `contract` и `paymentPurpose` в сущностях:
  * `CashInDocumentEntity` 
  * `CashOutDocumentEntity` 
  * `PaymentInDocumentEntity` 
  * `PaymentOutDocumentEntity` 
    
### Изменено
* Тип поля `vatSum` (`Integer` ⇒ `Long`) в сущностях:
  * `CashInDocumentEntity` 
  * `CashOutDocumentEntity` 
  * `PaymentInDocumentEntity` 
  * `PaymentOutDocumentEntity` 

## 2.0-ALPHA7 (03.08.2018)

### Добавлено
* Сущности:
  * `ProjectEntity`
  * `ExpenseItemEntity`
  * `CashOutDocumentEntity`
  * `InventoryDocumentEntity`
  * `InvoiceInDocumentEntity`
  * `PaymentInDocumentEntity`
  * `InvoiceOutDocumentEntity`
  * `PaymentOutDocumentEntity`
  * `ProcessingDocumentEntity`
  * `InternalOrderDocumentEntity`
  * `CommissionReportInDocumentEntity`
  * `CommissionReportOutDocumentEntity`
* Методы API: 
  * `GET /entity/group/`
  * `GET /entity/cashout/`
  * `GET /entity/project/`
  * `GET /entity/inventory/`
  * `GET /entity/invoicein/`
  * `GET /entity/paymentin/`
  * `GET /entity/invoiceout/`
  * `GET /entity/paymentout/`
  * `GET /entity/processing/`
  * `GET /entity/expenseitem/`
  * `GET /entity/internalorder/`
  * `GET /entity/commissionreportin/`
  * `GET /entity/commissionreportout/`
  * `GET /entity/cashout/metadata`
  * `GET /entity/project/metadata`
  * `GET /entity/paymentin/metadata`
  * `GET /entity/paymentout/metadata`
  * `POST /entity/group/`
  * `POST /entity/cashout/`
  * `POST /entity/invoicein/`
  * `POST /entity/inventory/`
  * `POST /entity/paymentin/`
  * `POST /entity/invoiceout/`
  * `POST /entity/paymentout/`
  * `POST /entity/processing/`
  * `POST /entity/expenseitem/`
  * `POST /entity/internalorder/`
  * `POST /entity/commissionreportin/`
  * `POST /entity/commissionreportout/`

## 2.0-ALPHA6 (26.07.2018)

### Добавлено
* Методы API: 
  * `GET /entity/group/`
  * `POST /entity/group/`

### Изменено
* Тип поля `owner` во всех сущностях (`OwnerEntity` ⇒ `EmployeeEntity`)

### Удалено
* Сущность `OwnerEntity`

## 2.0-ALPHA5 (19.07.2018)

### Добавлено

* Сущности:
  * `ConsignmentEntity`
  * `LossDocumentEntity`
  * `MoveDocumentEntity`
  * `EnterDocumentEntity`
  * `SalesReturnDocumentEntity`
  * `PurchaseOrderDocumentEntity`
  * `ProcessingPlanDocumentEntity`
  * `PurchaseReturnDocumentEntity`
  * `ProcessingOrderDocumentEntity`
* Методы API: 
  * `GET /entity/loss/`
  * `GET /entity/move/`
  * `GET /entity/enter/`
  * `GET /entity/consignment/`
  * `GET /entity/salesreturn/`
  * `GET /entity/purchaseorder/`
  * `GET /entity/processingplan/`
  * `GET /entity/processingorder/`
  * `POST /entity/loss/`
  * `POST /entity/move/`
  * `POST /entity/enter/`
  * `POST /entity/consignment/`
  * `POST /entity/salesreturn/`
  * `POST /entity/purchaseorder/`
  * `POST /entity/processingplan/`
  * `POST /entity/purchasereturn/`
  * `POST /entity/processingorder/`
  
### Изменено
* Рефакторинг клиентов
* Тип полей для сумм во всех сущностях (`Integer` ⇒ `Long`)

## 2.0-ALPHA4.1 (13.07.2018)

### Добавлено
* Поле `Артикул` в сущность `Комплект`

### Изменено
* Тип поля `Сумма` в сущности `Приёмка` (`Integer` ⇒ `Long`)

## 2.0-ALPHA4 (04.07.2018)

### Добавлено

* Механизм `expand`
* Сущности:
  * `RetailShiftEntity`
  * `RetailStoreEntity`
  * `CashInDocumentEntity`
  * `RetailSalesReturnEntity`
  * `RetailDrawerCashInEntity`
  * `RetailDrawerCashOutEntity`
  * `RetailDemandDocumentEntity`
  * `DocumentMetadataStatesListResponse`
* Методы API: 
  * `GET /entity/cashin/`
  * `GET /entity/employee/`
  * `GET /entity/retailshift/`
  * `GET /entity/retailstore/`  
  * `GET /entity/retaildemand/`
  * `GET /entity/retailsalesreturn/`
  * `GET /entity/retaildrawercashin/`
  * `GET /entity/retaildrawercashout/`  
  * `POST /entity/cashin/`
  * `POST /entity/retaildemand/`
  * `POST /entity/retailsalesreturn/`
  * `POST /entity/retaildrawercashin/`
  * `POST /entity/retaildrawercashout/`  
  * `DELETE /entity/cashin/{id}`  
  * `DELETE /entity/retailshift/{id}`    
  * `DELETE /entity/retaildemand/{id}`
  * `DELETE /entity/retailsalesreturn/{id}`  
  * `DELETE /entity/retaildrawercashin/{id}`  
  * `DELETE /entity/retaildrawercashout/{id}`  
* (Де)сериализаторы для `ListResponse` и `ProductMarker`

## 2.0-ALPHA3 (21.06.2018)

### Добавлено

* Сущности:
  * `Price`
  * `Bundle`
  * `Account`
  * `Country`
  * `Service`
  * `Discount`
  * `ProductFolder`
  * `DocumentPosition`
  * `UnitOfMeasurement`
* Методы API: 
  * `GET /entity/uom/`
  * `GET /entity/bundle/`
  * `GET /entity/supply/`
  * `GET /entity/service/`
  * `GET /entity/discount/`
  * `GET /entity/variant/{id}`
  * `GET /entity/productfolder/`
  * `POST /entity/uom/`
  * `POST /entity/bundle/`
  * `POST /entity/supply/`
  * `POST /entity/service/`
  * `POST /entity/productfolder/`
  * `DELETE /entity/variant/{id}`
  * `PUT /entity/variant/{id}`

### Изменено

* Во избежание коллизий с клиентским кодом в название всех сущностям в конец добавлено `Entity` 
* Все `***RequestBuilder` классы переименованы в `***Client` 

## 2.0-ALPHA2 (04.06.2018)

### Добавлено
* Методы API: 
  * `GET /entity/store/`
  * `GET /entity/demand/`
  * `GET /entity/contract/`
  * `GET /entity/currency/`
  * `POST /entity/demand/`
  * `POST /entity/contract/`
  * `POST /entity/currency/`
  * `POST /entity/organization/`
* Возможность изменять данные доступа к API у экземпляра класса LognexAPI
* Статический конструктор парсера GSON с настроенными сериализаторами и десериализаторами сущностей проекта
* Возможность запрашивать даты с миллисекундами
* Интерфейсы `***Endpoint`

## 2.0-ALPHA1 (28.05.2018)

Первый прототип