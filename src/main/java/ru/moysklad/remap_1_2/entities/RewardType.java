package ru.moysklad.remap_1_2.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Тип Вознаграждения
 */
public enum RewardType {
    /**
     * Процент от суммы продажи
     */
    @SerializedName("PercentOfSales") percentOfSales,

    /**
     * Не рассчитывать
     */
    @SerializedName("None") none
}
