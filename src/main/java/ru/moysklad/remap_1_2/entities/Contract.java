package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.agents.Organization;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Contract extends MetaEntity implements IEntityWithAttributes<Attribute> {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private Employee owner;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private Group group;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Комментарий к Договору
     */
    private String description;

    /**
     * Код Договора
     */
    private String code;

    /**
     * Внешний код Договора
     */
    private String externalCode;

    /**
     * Добавлен ли Договор в архив
     */
    private Boolean archived;

    /**
     * Дата Договора,
     */
    private LocalDateTime moment;

    /**
     * Сумма Договора,
     */
    private Long sum;

    /**
     * Тип Договора
     */
    private Type contractType;

    /**
     * Тип Вознаграждения
     */
    private RewardType rewardType;

    /**
     * Вознаграждение в процентах (от 0 до 100).
     */
    private Double rewardPercent;

    /**
     * Ссылка на ваше юрлицо в формате Метаданных
     */
    private Organization ownAgent;

    /**
     * Ссылка на Контрагента в формате Метаданные
     */
    private Agent agent;

    /**
     * Статус договора в формате Метаданных
     */
    private State state;

    /**
     * Ссылка на счёт вашего юрлица в формате Метаданных
     */
    private AgentAccount organizationAccount;

    /**
     * Ссылка на счёт контрагента в формате Метаданных
     */
    private AgentAccount agentAccount;

    /**
     * Валюта документа
     */
    private Rate rate;

    /**
     * Коллекция доп. полей
     */
    private List<Attribute> attributes;

    public Contract(String id) {
        super(id);
    }

    /**
     * Тип Договора
     */
    public enum Type {
        /**
         * Договор комиссии
         */
        @JsonProperty("Commission") commission,

        /**
         * Договор купли-продажи
         */
        @JsonProperty("Sales") sales
    }
}
