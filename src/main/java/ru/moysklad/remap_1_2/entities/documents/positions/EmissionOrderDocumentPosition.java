package ru.moysklad.remap_1_2.entities.documents.positions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.documents.DocumentPosition;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmissionOrderDocumentPosition extends DocumentPosition {
    private Status status;

    /**
     * Статус кодов
     */
    public enum Status {
        /**
         * Не отправлен
         */
        @JsonProperty("EMISSION_NOT_SEND") EMISSION_NOT_SEND,

        /**
         * В обработке
         */
        @JsonProperty("EMISSION_SEND") EMISSION_SEND,

        /**
         * Коды доступны
         */
        @JsonProperty("EMISSION_ACTIVE") EMISSION_ACTIVE,

        /**
         * Ошибка
         */
        @JsonProperty("EMISSION_ERROR") EMISSION_ERROR,

        /**
         * Коды получены
         */
        @JsonProperty("EMISSION_RECEIVED") EMISSION_RECEIVED,

        /**
         * Частично напечатаны
         */
        @JsonProperty("EMISSION_PRINTED_PARTLY") EMISSION_PRINTED_PARTLY,

        /**
         * Коды напечатаны
         */
        @JsonProperty("EMISSION_PRINTED_FULLY") EMISSION_PRINTED_FULLY,

        /**
         * Коды получены
         */
        @JsonProperty("EMISSION_COMPLETED") EMISSION_COMPLETED
    }
}
