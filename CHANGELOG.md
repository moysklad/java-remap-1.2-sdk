# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 2.0-ALPHA4 (xx.xx.2018)

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