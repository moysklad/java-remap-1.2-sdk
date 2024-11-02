package ru.moysklad.remap_1_2.entities.products;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.Group;
import ru.moysklad.remap_1_2.entities.IEntityWithAttributes;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.products.markers.SingleProductMarker;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Service extends AbstractProduct implements SingleProductMarker, IEntityWithAttributes<Attribute> {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private Employee owner;

    /**
     * Наименование группы, в которую входит Услуга
     */
    private String pathName;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Внешний код Услуги
     */
    private String externalCode;

    /**
     * Отметка о том, добавлен ли Услуга в архив
     */
    private Boolean archived;

    /**
     * Флаг для отображения запрета скидки
     */
    private Boolean discountProhibited;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private Group group;

    /**
     * Идентификатор синхронизации
     */
    private String syncId;

    /**
     * Дополнительные поля
     */
    private List<Attribute> attributes;

    /**
     * Признак предмета расчета
     */
    private ServicePaymentItemType paymentItemType;

    /**
     * Код системы налогообложения
     */
    private GoodTaxSystem taxSystem;
}
