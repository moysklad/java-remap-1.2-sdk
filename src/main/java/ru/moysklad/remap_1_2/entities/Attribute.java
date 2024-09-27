package ru.moysklad.remap_1_2.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Дополнительное поле
 */

public interface Attribute {

    Type getType();
    void setType(Type type);
    Meta getMeta();
    void setMeta(Meta meta);
    Meta.Type getAttributeEntityType();
    void setAttributeEntityType(Meta.Type type);
    String getId();
    void setId(String id);
    Meta.Type getEntityType();
    void setEntityType(Meta.Type type);

    /**
     * Тип дополнительного поля
     */
    enum Type {
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
         * Число дробное
         */
        @SerializedName("double") doubleValue,

        /**
         * Флажок
         */
        @SerializedName("boolean") booleanValue,

        /**
         * Текст
         */
        @SerializedName("text") textValue,

        /**
         * Ссылка
         */
        @SerializedName("link") linkValue
    }
}
