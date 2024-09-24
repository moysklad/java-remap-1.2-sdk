package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GlobalMetadata extends Entity {
    private MetaEntity assortment;
    private MetaEntity bonusprogram;
    private CompanySettingsMetadata companysettings;
    private MetaEntity consignment;

    private ProductMetadata bundle;
    private ProductMetadata product;
    private ProductMetadata service;
    private VariantMetadata variant;

    private StatesCreateSharedOption customerorder;
    private StatesCreateSharedOption counterparty;

    private CreateSharedOption factureout;
    private CreateSharedOption invoicein;
    private CreateSharedOption processingorder;
    private CreateSharedOption bonustransaction;
    private CreateSharedOption project;
    private CreateSharedOption inventory;
    private CreateSharedOption employee;
    private CreateSharedOption supply;
    private CreateSharedOption productfolder;
    private CreateSharedOption retailsalesreturn;
    private CreateSharedOption loss;
    private CreateSharedOption purchaseorder;
    private CreateSharedOption facturein;
    private CreateSharedOption commissionreportin;
    private CreateSharedOption commissionreportout;
    private CreateSharedOption enter;
    private CreateSharedOption retaildemand;
    private CreateSharedOption retaildrawercashin;
    private CreateSharedOption move;
    private CreateSharedOption cashin;
    private CreateSharedOption contract;
    private CreateSharedOption paymentout;
    private CreateSharedOption store;
    private CreateSharedOption retaildrawercashout;
    private CreateSharedOption demand;
    private CreateSharedOption cashout;
    private CreateSharedOption internalorder;
    private CreateSharedOption pricelist;
    private CreateSharedOption purchasereturn;
    private CreateSharedOption invoiceout;
    private CreateSharedOption organization;
    private CreateSharedOption processing;
    private CreateSharedOption retailshift;
    private CreateSharedOption paymentin;
    private CreateSharedOption salesreturn;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class CreateSharedOption extends MetaEntity {
        private Boolean createShared;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ProductMetadata extends CreateSharedOption {
        private ListEntity<AttributeCustomEntity> attributes;
        private List<PriceTypesItem> priceTypes;

        @Getter
        @Setter
        @NoArgsConstructor
        @EqualsAndHashCode
        public static class PriceTypesItem {
            private String name;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class VariantMetadata extends ProductMetadata {
        private List<CharacteristicsItem> characteristics;

        @Getter
        @Setter
        @NoArgsConstructor
        @EqualsAndHashCode(callSuper = false)
        public static class CharacteristicsItem extends MetaEntity {
            private String type;
            private Boolean required;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class StatesCreateSharedOption extends CreateSharedOption {
        private List<StatesItem> states;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class StatesItem extends MetaEntity {
        private Integer color;
        private String entityType;
        private String stateType;
    }
}
