package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
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
     * Тип сущности
     */
    public Type type;

    /**
     * Тип данных, которые приходят в ответ от сервера, либо отправляются в теле запроса
     */
    public MediaType mediaType;

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

    /**
     * Тип сущности
     */
    public enum Type {
        employee, contract, counterparty, organization, group, account, demand, store, demandposition, note, state, currency
    }

    /**
     * Тип данных, которые приходят в ответ от сервера, либо отправляются в теле запроса
     */
    public enum MediaType {
        @SerializedName("application/json") json
    }
}
