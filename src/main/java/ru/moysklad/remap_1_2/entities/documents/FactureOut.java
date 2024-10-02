package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceInDocumentMarker;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Счёт-фактура выданный
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FactureOut extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
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
    private List<DocumentAttribute> attributes;

    /**
     * Контракт
     */
    private Contract contract;

    private String syncId;
    private LocalDateTime deleted;
    private ListEntity<AttachedFile> files;

    public FactureOut(String id) {
        super(id);
    }
}
