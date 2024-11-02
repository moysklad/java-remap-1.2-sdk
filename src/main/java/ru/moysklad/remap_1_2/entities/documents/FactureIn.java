package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceOutDocumentMarker;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Счёт-фактура полученный
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FactureIn extends DocumentEntity implements IEntityWithAttributes<DocumentAttribute>, HasFiles {
    /**
     * Контрагента
     */
    private Agent agent;

    /**
     * Внешний код
     */
    private String externalCode;

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

    public FactureIn(String id) {
        super(id);
    }
}
