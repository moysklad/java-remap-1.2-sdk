package com.lognex.api.entities.documents;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.RateEntity;
import com.lognex.api.entities.StateEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.markers.FinanceInDocumentMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Счёт-фактура выданный
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FactureOutDocumentEntity extends DocumentEntity {
    /**
     * Контрагент
     */
    private AgentEntity agent;

    /**
     * Грузополучатель
     */
    private AgentEntity consignee;

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
     * Идентификатор гос. контракта
     */
    private String stateContractId;

    /**
     * Валюта
     */
    private RateEntity rate;

    /**
     * Организация
     */
    private OrganizationEntity organization;

    /**
     * Дата платежного документа
     */
    private LocalDateTime paymentDate;

    /**
     * Название платежного документа
     */
    private String paymentNumber;

    /**
     * Связанные отгрузки
     */
    private List<DemandDocumentEntity> demands;

    /**
     * Связанные входящие платежи и приходные ордеры
     */
    private List<FinanceInDocumentMarker> payments;

    /**
     * Связанные возвраты поставщикам
     */
    private List<PurchaseReturnDocumentEntity> returns;

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
