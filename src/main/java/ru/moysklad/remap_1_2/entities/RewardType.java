package ru.moysklad.remap_1_2.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Тип Вознаграждения
 */
public enum RewardType {
    /**
     * Процент от суммы продажи
     */
    @JsonProperty("PercentOfSales") percentOfSales,

    /**
     * Не рассчитывать
     */
    @JsonProperty("None") none
}
