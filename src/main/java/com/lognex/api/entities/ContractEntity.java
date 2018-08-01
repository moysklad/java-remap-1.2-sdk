package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContractEntity extends MetaEntity {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных
     */
    private EmployeeEntity owner;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private MetaEntity group;

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
    private OrganizationEntity ownAgent;

    /**
     * Ссылка на Контрагента в формате Метаданные
     */
    private AgentEntity agent;

    /**
     * Статус договора в формате Метаданных
     */
    private StateEntity state;

    /**
     * Ссылка на счёт вашего юрлица в формате Метаданных
     */
    private MetaEntity organizationAccount;

    /**
     * Ссылка на счёт контрагента в формате Метаданных
     */
    private MetaEntity agentAccount;

    /**
     * Ссылка на валюту
     */
    private RateEntity rate;

    /**
     * Коллекция доп. полей
     */
    private List<AttributeEntity> attributes;

    /**
     * Тип Договора
     */
    public enum Type {
        /**
         * Договор комиссии
         */
        @SerializedName("Commission") commission,

        /**
         * Договор купли-продажи
         */
        @SerializedName("Sales") sales
    }
}
