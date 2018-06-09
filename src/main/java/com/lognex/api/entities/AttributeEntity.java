package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Дополнительное поле
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttributeEntity extends MetaEntity {
    /**
     * Id доп. поля
     */
    private String id;

    /**
     * Наименование доп. поля
     */
    private String name;

    /**
     * Тип доп. поля
     */
    private Type type;

    /**
     * Тип сущности справочника, если тип поля — Справочник
     * <p>
     * (TODO) Нужно настроить (де)сериализацию для этого поля
     */
    private Meta.Type entityType;

    /**
     * Значение
     */
    private Object value;

    /**
     * Флажок о том, является ли доп. поле обязательным
     */
    private Boolean required;

    /**
     * Тип дополнительного поля
     */
    public enum Type {
        /**
         * Строка
         */
        @SerializedName("string") stringValue,

        /**
         * Целое число
         */
        @SerializedName("long") longValue,

        /**
         * Дата
         */
        @SerializedName("time") timeValue,

        /**
         * Файл
         */
        @SerializedName("file") fileValue,

        /**
         *
         */
        @SerializedName("double") doubleValue,
        @SerializedName("boolean") booleanValue,
        @SerializedName("text") textValue,
        @SerializedName("link") lingValue
    }
}
