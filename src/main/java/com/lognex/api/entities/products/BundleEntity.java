package com.lognex.api.entities.products;

import com.lognex.api.entities.CurrencyEntity;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.ProductImageEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BundleEntity extends AbstractProductEntity {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private EmployeeEntity owner;

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
    private GroupEntity group;

    /**
     * Изображение товара
     */
    private ProductImageEntity image;

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
        private CurrencyEntity currency;
        private Long value;
    }
}
