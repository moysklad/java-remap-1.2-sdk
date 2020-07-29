package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Расчётные Счёта
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgentAccount extends MetaEntity {
    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Является ли счёт основным счётом юрлица
     */
    private Boolean isDefault;

    /**
     * Номер счёта
     */
    private String accountNumber;

    /**
     * Наименование банка
     */
    private String bankName;

    /**
     * Адрес банка
     */
    private String bankLocation;

    /**
     * Корр счет
     */
    private String correspondentAccount;

    /**
     * БИК
     */
    private String bic;

    public AgentAccount(String id) {
        super(id);
    }
}
