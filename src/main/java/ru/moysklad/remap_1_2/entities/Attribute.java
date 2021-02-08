package ru.moysklad.remap_1_2.entities;

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
public class Attribute extends MetaEntity {
    /**
     * Тип доп. поля
     */
    private Type type;

    /**
     * Тип сущности справочника, если тип поля — Справочник
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
     * (для поля типа "Файл") Метаданные файла
     */
    private Meta download;

    /**
     * Метадата сущности справочника
     */
    private Meta customEntityMeta;

    /**
     * Тип сущности, которой принадлежит аттрибут
     */
    private transient Meta.Type attributeEntityType;

    public Attribute(String id) {
        super(id);
    }

    public Attribute(Meta.Type attributeEntityType, String id, Type type, Object value){
        super(id);
        this.type = type;
        this.value = value;
        if (MetaEntity.class.isAssignableFrom(value.getClass())) {
            entityType = Meta.Type.find((MetaEntity) value);
        }
        this.attributeEntityType = attributeEntityType;
    }

    public Attribute(Meta.Type attributeEntityType, String id, MetaEntity value){
        super(id);
        this.value = value;
        entityType = Meta.Type.find(value);
        this.attributeEntityType = attributeEntityType;
    }

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

    public <T> T getValueAs(Class<T> tClass) {
        return (T) value;
    }
}
