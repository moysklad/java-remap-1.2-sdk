package ru.moysklad.remap_1_2.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * Валюта
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Currency extends MetaEntity implements Fetchable {
    /**
     * Полное наименование
     */
    private String fullName;

    /**
     * Курс
     */
    private Double rate;

    /**
     * Кратность курса
     */
    private MultiplicityType multiplicity;

    /**
     * Способ обновления курса
     */
    private UpdateType rateUpdateType;

    /**
     * Флаг обратного курса
     */
    private Boolean indirect;

    /**
     * Цифровой код
     */
    private String code;

    /**
     * Буквенный код
     */
    private String isoCode;

    /**
     * Основана ли эта Валюта на Валюте из системного справочника
     */
    private Boolean system;

    /**
     * Лексические формы единиц целой части (рубль, рубля, рублей)
     */
    private Unit majorUnit;

    /**
     * Лексические формы единиц дробной части (копейка, копейки, копеек)
     */
    private Unit minorUnit;

    /**
     * Флаг нахождения валюты в архиве
     */
    private Boolean archived;

    /**
     * Флаг Валюты учёта
     */
    @JsonProperty("default")
    private Boolean def;

    public Currency(String id) {
        super(id);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Unit {
        /**
         * Грамматический род единицы Валюты
         */
        private Gender gender;

        /**
         * Форма единицы Валюты, используемая при числительном, <b>заканчивающимся</b> на 1, <b>кроме</b> заканчивающихся на 11 <i>(например: <code>рубль</code>)</i>
         */
        private String s1;

        /**
         * Форма единицы Валюты, используемая при числительном, <b>заканчивающимся</b> на 2, 3 или 4, <b>кроме</b> заканчивающихся на 12, 13, 14 <i>(например: <code>рубля</code>)</i>
         */
        private String s2;

        /**
         * Форма единицы Валюты, используемая при числительных, <b>не заканчивающихся</b> на 1, 2, 3 и 4, <b>включая</b> заканчивающиеся на 11, 12, 13, 14 <i>(например: <code>рублей</code>)</i>
         */
        private String s5;

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
        public static class Serializer extends JsonSerializer<MultiplicityType> {
            @Override
            public void serialize(MultiplicityType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(Integer.parseInt(value.name().substring(1)));
            }
        }
        public static class Deserializer extends JsonDeserializer<MultiplicityType> {
            @Override
            public MultiplicityType deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException {
                int value = p.getIntValue();
                return Currency.MultiplicityType.valueOf(value);
            }
        }
    }
}
