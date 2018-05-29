package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Метаданные
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class Meta {
    /**
     * Ссылка на объект
     */
    public String href;

    /**
     * Ссылка на метаданные сущности
     */
    public String metadataHref;

    /**
     * Тип объекта
     */
    public String type;

    /**
     * Тип данных, которые приходят в ответ от сервиса, либо отправляются в теле запроса
     */
    public String mediaType;

    /**
     * Ссылка на объект на UI. Присутствует не во всех сущностях. Может быть использована для получения uuid
     */
    public String uuidHref;

    /**
     * Размер выданного списка
     */
    public Integer size;

    /**
     * Максимальное количество элементов в выданном списке
     */
    public Integer limit;

    /**
     * Отступ в выданном списке
     */
    public Integer offset;
}
