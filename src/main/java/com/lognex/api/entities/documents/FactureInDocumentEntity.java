package com.lognex.api.entities.documents;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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
public class FactureInDocumentEntity extends DocumentEntity {
    /**
     * Контрагента
     */
    private AgentEntity agent;

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
    private List<SupplyDocumentEntity> supplies;

    /**
     * Валюта
     */
    private RateEntity rate;

    /**
     * Организация
     */
    private OrganizationEntity organization;

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
    private StateEntity state;

    /**
     * Коллекция доп. полей
     */
    private List<AttributeEntity> attributes;
}
