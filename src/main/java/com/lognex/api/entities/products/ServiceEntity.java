package com.lognex.api.entities.products;

import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.products.markers.ProductMarker;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceEntity extends AbstractProductEntity implements SingleProductMarker, ProductMarker {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private EmployeeEntity owner;

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
     * Версия сущности
     */
    private Integer version;

    /**
     * ID учетной записи
     */
    private String accountId;

    /**
     * Отметка о том, добавлен ли Услуга в архив
     */
    private Boolean archived;

    /**
     * ID Услуги в формате UUID
     */
    private String id;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private GroupEntity group;
}
