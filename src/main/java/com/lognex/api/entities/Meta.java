package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Метаданные
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class Meta {
    /**
     * Ссылка на объект
     */
    private String href;

    /**
     * Ссылка на метаданные сущности
     */
    private String metadataHref;

    /**
     * Тип сущности
     */
    private Type type;

    /**
     * Тип данных, которые приходят в ответ от сервера, либо отправляются в теле запроса
     */
    private MediaType mediaType;

    /**
     * Ссылка на объект на UI. Присутствует не во всех сущностях. Может быть использована для получения uuid
     */
    private String uuidHref;

    /**
     * Размер выданного списка
     */
    private Integer size;

    /**
     * Максимальное количество элементов в выданном списке
     */
    private Integer limit;

    /**
     * Отступ в выданном списке
     */
    private Integer offset;

    /**
     * Тип сущности
     */
    public enum Type {
        employee, contract, counterparty, organization, group,
        account, demand, store, demandposition, note, state,
        product, service, bundle, currency, uom, productfolder,
        supplyposition, country, variant, retailstore, retailshift,
        retaildemand, salesreturnposition, consignment, move,
        purchasereturnposition, enter, supply, purchaseorder,
        purchaseorderposition, customerorder, processingplanmaterial,
        processingplanresult, processingplan, processingorder,
        processingorderposition, expenseitem, cashin, cashout,
        paymentin, paymentout, project
    }

    /**
     * Тип данных, которые приходят в ответ от сервера, либо отправляются в теле запроса
     */
    public enum MediaType {
        @SerializedName("application/json") json
    }
}
