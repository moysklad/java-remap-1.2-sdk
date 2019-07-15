package com.lognex.api.entities.documents;

import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.IEntityWithAttributes;
import com.lognex.api.entities.Rate;
import com.lognex.api.entities.State;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceOutDocumentMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Счёт-фактура полученный
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FactureIn extends DocumentEntity implements IEntityWithAttributes {
    /**
     * Контрагента
     */
    private Agent agent;

    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Печатные формы
     */
    private ListEntity<DocumentEntity> documents;

    /**
     * Дата создания
     */
    private LocalDateTime created;

    /**
     * Связанные исходящие платежи и расходные ордеры
     */
    private List<FinanceOutDocumentMarker> payments;

    /**
     * Связанные приёмки
     */
    private List<Supply> supplies;

    /**
     * Валюта документа
     */
    private Rate rate;

    /**
     * Организация
     */
    private Organization organization;

    /**
     * Входящий номер
     */
    private String incomingNumber;

    /**
     * Входящая дата
     */
    private LocalDateTime incomingDate;

    /**
     * Флаг удалённого документа
     */
    private Boolean isDeleted;

    /**
     * Комментарий
     */
    private String description;

    /**
     * Статус
     */
    private State state;

    /**
     * Коллекция доп. полей
     */
    private List<Attribute> attributes;

    private String syncId;
    private LocalDateTime deleted;
    private String stateContractId;
    private Agent consignee;

    public FactureIn(String id) {
        super(id);
    }
}
