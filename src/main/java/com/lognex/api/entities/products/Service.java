package com.lognex.api.entities.products;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.Group;
import com.lognex.api.entities.IEntityWithAttributes;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Service extends AbstractProduct implements SingleProductMarker, IEntityWithAttributes {
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
