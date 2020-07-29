package com.lognex.api.entities.notifications;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationExchange extends Notification {
    private String message;
    private String errorMessage;
    private TaskType taskType;
    private TaskState taskState;
    private String createdDocumentName;

    public enum TaskState {
        COMPLETED,
        INTERRUPTED,
        INTERRUPTED_BY_USER,
        INTERRUPTED_BY_TIMEOUT,
        INTERRUPTED_BY_SYSTEM
    }

    public enum TaskType {
        /**
         * Export types
         */
        EXPORT_CSV_GOOD,
        EXPORT_CSV_AGENT,
        EXPORT_MS_XML,
        EXPORT_1C_V2_XML,
        EXPORT_UNISENDER,
        EXPORT_1C_V3_XML,
        EXPORT_SUBSCRIBEPRO,
        EXPORT_1C_CLIENT_BANK,
        EXPORT_ALFA_PAYMENTS,
        EXPORT_TOCHKA_PAYMENTS,
        EXPORT_MODULBANK_PAYMENTS,
        EXPORT_1C_ENTERPRISE_DATA,
        EXPORT_TINKOFF_PAYMENTS,
        EXPORT_GOOD,
        EXPORT_CUSTOM_ENTITY,
        /**
         * Import types
         */
        IMPORTER_CSV,
        IMPORTER_YML,
        IMPORTER_CSV_AGENT,
        IMPORTER_CSV_CUSTOMERORDER,
        IMPORTER_CSV_PURCHASEORDER,
        IMPORTER_CSV_PRICELIST,
        IMPORTER_MS_XML,
        IMPORTER_1C_CLIENT_BANK,
        IMPORT_ALFA_PAYMENTS,
        IMPORT_ALFA_PAYMENTS_REQUEST,
        IMPORT_ALFA_PAYMENTS_SAVE,
        IMPORT_TOCHKA_PAYMENTS,
        IMPORT_MODULBANK_PAYMENTS,
        IMPORT_TOCHKA_PAYMENTS_SAVE,
        IMPORT_MODULBANK_PAYMENTS_SAVE,
        IMPORT_TINKOFF_PAYMENTS,
        IMPORT_TINKOFF_PAYMENTS_SAVE,
        IMPORTER_GOOD,
        IMPORTER_GOOD_IN_DOC,
        IMPORT_EDO_SUPPLY,
        IMPORT_UNION_COMPANY,
        IMPORT_SBERBANK_PAYMENTS_REQUEST,
        IMPORT_SBERBANK_PAYMENTS_SAVE,
        IMPORT_UPDATE_VAT_TO_20_PERCENTS,
        IMPORT_CUSTOM_ENTITY
    }
}
