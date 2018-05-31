package com.lognex.api.entities;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;

/**
 * Валюта
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Currency extends MetaEntity implements Fetchable {
    /**
     * Краткое наименование
     */
    public String name;

    /**
     * Полное наименование
     */
    public String fullName;

    /**
     * Курс
     */
    public Double rate;

    /**
     * Кратность курса
     */
    public MultiplicityType multiplicity;

    /**
     * Способ обновления курса
     */
    public UpdateType rateUpdateType;

    /**
     * Флаг обратного курса
     */
    public Boolean indirect;

    /**
     * Цифровой код
     */
    public String code;

    /**
     * Буквенный код
     */
    public String isoCode;

    /**
     * Основана ли эта Валюта на Валюте из системного справочника
     */
    public Boolean system;

    /**
     * Лексические формы единиц целой части (рубль, рубля, рублей)
     */
    public Unit majorUnit;

    /**
     * Лексические формы единиц дробной части (копейка, копейки, копеек)
     */
    public Unit minorUnit;

    /**
     * Флаг нахождения валюты в архиве
     */
    public Boolean archived;

    /**
     * Флаг Валюты учёта
     */
    @SerializedName("default")
    public Boolean def;

    public static class Unit {
        /**
         * Грамматический род единицы Валюты
         */
        public Gender gender;

        /**
         * Форма единицы Валюты, используемая при числительном, <b>заканчивающимся</b> на 1, <b>кроме</b> заканчивающихся на 11 <i>(например: <code>рубль</code>)</i>
         */
        public String s1;

        /**
         * Форма единицы Валюты, используемая при числительном, <b>заканчивающимся</b> на 2, 3 или 4, <b>кроме</b> заканчивающихся на 12, 13, 14 <i>(например: <code>рубля</code>)</i>
         */
        public String s2;

        /**
         * Форма единицы Валюты, используемая при числительных, <b>не заканчивающихся</b> на 1, 2, 3 и 4, <b>включая</b> заканчивающиеся на 11, 12, 13, 14 <i>(например: <code>рублей</code>)</i>
         */
        public String s5;

        /**
         * Грамматический род единицы Валюты
         */
        public enum Gender {
            /**
             * Мужской род
             */
            masculine,

            /**
             * Женский род
             */
            feminine
        }
    }

    /**
     * Способ обновления курса
     */
    public enum UpdateType {
        /**
         * Ручной
         */
        manual,

        /**
         * Автоматический
         */
        auto
    }

    /**
     * Кратность курса
     */
    public enum MultiplicityType {
        _1,
        _10,
        _100,
        _1000,
        _10000;

        public static MultiplicityType valueOf(int val) {
            String s = "_" + val;

            for (MultiplicityType v : values()) {
                if (v.name().equals(s)) return v;
            }

            throw new IllegalArgumentException("No enum constant MultiplicityType." + s);
        }

        /**
         * Сериализатор/десериализатор enum'а
         */
        public static class Serializer implements JsonSerializer<MultiplicityType>, JsonDeserializer<MultiplicityType> {
            @Override
            public MultiplicityType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return Currency.MultiplicityType.valueOf(json.getAsInt());
            }

            @Override
            public JsonElement serialize(MultiplicityType src, Type typeOfSrc, JsonSerializationContext context) {
                return context.serialize(
                        Integer.parseInt(src.name().substring(1))
                );
            }
        }
    }
}
