package ru.moysklad.remap_1_2.entities.documents;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.State;
import ru.moysklad.remap_1_2.entities.TrackingType;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.EmissionOrderDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;

/**
 * Заказ кодов маркировки
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmissionOrder extends DocumentEntity {
    private LocalDateTime created;
    private LocalDateTime deleted;
    private String description;
    private DocumentState documentState;
    private EmissionType emissionType;
    private TrackingType trackingType;
    private String externalCode;
    private Organization organization;
    private ListEntity<EmissionOrderDocumentPosition> positions;
    private State state;

    public EmissionOrder(String id) {
        super(id);
    }

    /**
     * Состояние документов маркировки
     */
    public enum DocumentState {
        /**
         * Не отправлен
         */
        @JsonProperty("CREATED") CREATED,

        /**
         * Создан
         */
        @JsonProperty("SUZ_CREATED") SUZ_CREATED,

        /**
         * Отправлен
         */
        @JsonProperty("SUZ_SEND") SUZ_SEND,

        /**
         * Закрыт
         */
        @JsonProperty("SUZ_COMPLETED") SUZ_COMPLETED
    }

    /**
     * Способ ввода в оборот
     */
    public enum EmissionType {
        /**
         * Произведен в РФ
         */
        @JsonProperty("LOCAL") LOCAL,

        /**
         * Ввезен в РФ
         */
        @JsonProperty("FOREIGN") FOREIGN,

        /**
         * Маркировка остатков
         */
        @JsonProperty("REMAINS") REMAINS,

        /**
         * Ввезен из стран ЕАЭС
         */
        @JsonProperty("CROSSBORDER") CROSSBORDER,

        /**
         * Принят на комиссию от физического лица
         */
        @JsonProperty("COMMISSION") COMMISSION
    }
}
