package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.markers.FinanceInDocumentMarker;
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
public class FactureOut extends DocumentEntity implements IEntityWithAttributes {
    /**
     * Контрагент
     */
    private Agent agent;

    /**
     * Грузополучатель
     */
    private Agent consignee;

    /**
     * Внешний код
     */
    private String externalCode;

    /**
     * Дата создания
     */
    private LocalDateTime created;

    /**
     * Идентификатор гос. контракта
     */
    private String stateContractId;

    /**
     * Валюта документа
     */
    private Rate rate;

    /**
     * Организация
     */
    private Organization organization;

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
    private List<Demand> demands;

    /**
     * Связанные входящие платежи и приходные ордеры
     */
    private List<FinanceInDocumentMarker> payments;

    /**
     * Связанные возвраты поставщикам
     */
    private List<PurchaseReturn> returns;

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

    /**
     * Контракт
     */
    private Contract contract;

    private String syncId;
    private LocalDateTime deleted;

    public FactureOut(String id) {
        super(id);
    }
}
