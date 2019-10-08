package com.lognex.api.entities.products;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.products.markers.HasImages;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import com.lognex.api.responses.ListEntity;
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
public class Bundle extends AbstractProduct implements IEntityWithAttributes, HasImages {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private Employee owner;

    /**
     * Наименование группы, в которую входит Комплект
     */
    private String pathName;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Компоненты
     */
    private ListEntity<ComponentEntity> components;

    /**
     * Внешний код Комплекта
     */
    private String externalCode;

    /**
     * Артикул
     */
    private String article;

    /**
     * Вес
     */
    private Integer weight;

    /**
     * Объём
     */
    private Integer volume;

    /**
     * Отметка о том, добавлен ли Комплект в архив
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
     * Изображение товара
     */
    private ListEntity<Image> images;

    /**
     * Идентификатор синхронизации
     */
    private String syncId;

    /**
     * Дополнительные поля
     */
    private List<Attribute> attributes;

    /**
     * Страна
     */
    private Country country;

    /**
     * Дополнительные расходы
     */
    private Overhead overhead;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ComponentEntity extends MetaEntity {
        private SingleProductMarker assortment;
        private Double quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Overhead {
        private Currency currency;
        private Long value;
    }

    /**
     * Тип маркируемой продукции
     */
    private TrackingType trackingType;

    public enum TrackingType {
        NOT_TRACKED,
        TOBACCO,
        SHOES
    }

    /**
     * Код ТН ВЭД
     */
    private String tnved;

    /**
     * Признак предмета расчета
     */
    private GoodPaymentItemType paymentItemType;

    /**
     * Код системы налогообложения
     */
    private GoodTaxSystem taxSystem;
}
