# История версий

Все крупные изменения в проекте будут отражены в этом файле.

## 2.0-ALPHA3 (xx.xx.2018)

### Добавлено

* Сущности:
  * `Account`
  * `Country`
  * `Discount`
  * `DocumentPosition`
  * `Price`
  * `ProductFolder`
  * `UnitOfMeasurement`
  * `Bundle`
  * `Service`
* Методы API: 
  * `GET /entity/bundle/`
  * `POST /entity/bundle/`
  * `GET /entity/discount/`
  * `GET /entity/supply/`
  * `POST /entity/supply/`
  * `GET /entity/productfolder/`
  * `POST /entity/productfolder/`
  * `GET /entity/service/`
  * `POST /entity/service/`
  * `GET /entity/uom/`
  * `POST /entity/uom/`
  * `GET /entity/variant/{id}/`
  * `PUT /entity/variant/{id}/`
  * `DELETE /entity/variant/{id}/`

### Изменено

* Во избежание коллизий с клиентским кодом в название всех сущностям в конец добавлено `Entity` 
* Все `***RequestBuilder` классы переименованы в `***Client` 

## 2.0-ALPHA2 (04.06.2018)

### Добавлено
* Методы API: 
  * `GET /entity/currency/`
  * `POST /entity/currency/`
  * `GET /entity/demand/`
  * `POST /entity/demand/`
  * `GET /entity/contract/`
  * `POST /entity/contract/`
  * `GET /entity/store/`
  * `POST /entity/organization/`
* Возможность изменять данные доступа к API у экземпляра класса LognexAPI
* Статический конструктор парсера GSON с настроенными сериализаторами и десериализаторами сущностей проекта
* Возможность запрашивать даты с миллисекундами
* Интерфейсы `***Endpoint`

## 2.0-ALPHA1 (28.05.2018)

Первый прототип