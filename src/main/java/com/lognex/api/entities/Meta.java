package com.lognex.api.entities;

import com.google.gson.*;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.discounts.*;
import com.lognex.api.entities.documents.*;
import com.lognex.api.entities.products.BundleEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.entities.products.ServiceEntity;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.CompanySettingsResponse;
import com.lognex.api.responses.metadata.CompanySettingsMetadata;
import com.lognex.api.utils.MetaHrefUtils;
import lombok.*;

import java.util.Arrays;

/**
 * Метаданные
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public final class Meta {
    public Meta(MetaEntity entity, String host) {
        if (entity == null || entity.getId() == null || host == null) {
            return;
        }
        type = Type.find(entity);
        href = MetaHrefUtils.makeHref(type, entity, host);
        if (type != Type.CUSTOM_TEMPLATE && type != Type.EMBEDDED_TEMPLATE) {
            metadataHref = MetaHrefUtils.makeMetadataHref(type, entity, host);
        }
    }

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
        EMPLOYEE("employee", EmployeeEntity.class),
        CONTRACT("contract", ContractEntity.class),
        COUNTERPARTY("counterparty", CounterpartyEntity.class),
        ORGANIZATION("organization", OrganizationEntity.class),
        GROUP("group", GroupEntity.class),
        ACCOUNT("account", AccountEntity.class),
        DEMAND("demand", DemandDocumentEntity.class),
        STORE("store", StoreEntity.class),
        DEMAND_POSITION("demandposition", DocumentPosition.class),
        NOTE("note", NoteEntity.class),
        STATE("state", StateEntity.class),
        PRODUCT("product", ProductEntity.class),
        SERVICE("service", ServiceEntity.class),
        BUNDLE("bundle", BundleEntity.class),
        BUNDLE_COMPONENT("bundlecomponent", BundleEntity.ComponentEntity.class),
        CURRENCY("currency", CurrencyEntity.class),
        UOM("uom", UomEntity.class),
        PRODUCT_FOLDER("productfolder", ProductFolderEntity.class),
        SUPPLY_POSITION("supplyposition", DocumentPosition.class),
        COUNTRY("country", CountryEntity.class),
        VARIANT("variant", VariantEntity.class),
        RETAIL_STORE("retailstore", RetailStoreEntity.class),
        RETAIL_SHIFT("retailshift", RetailShiftDocumentEntity.class),
        RETAIL_DEMAND("retaildemand", RetailDemandDocumentEntity.class),
        RETAIL_DRAWER_CASH_IN("retaildrawercashin", RetailDrawerCashInEntity.class),
        RETAIL_DRAWER_CASH_OUT("retaildrawercashout", RetailDrawerCashOutEntity.class),
        RETAIL_SALES_RETURN("retailsalesreturn", RetailSalesReturnEntity.class),
        SALES_RETURN("salesreturn", SalesReturnDocumentEntity.class),
        SALES_RETURN_POSITION("salesreturnposition", DocumentPosition.class),
        CONSIGNMENT("consignment", ConsignmentEntity.class),
        MOVE("move", MoveDocumentEntity.class),
        MOVE_POSITION("moveposition", DocumentPosition.class),
        PURCHASE_RETURN("purchasereturn", PurchaseReturnDocumentEntity.class),
        PURCHASE_RETURN_POSITION("purchasereturnposition", DocumentPosition.class),
        ENTER("enter", EnterDocumentEntity.class),
        ENTER_POSITION("enterposition", DocumentPosition.class),
        SUPPLY("supply", SupplyDocumentEntity.class),
        PURCHASE_ORDER("purchaseorder", PurchaseOrderDocumentEntity.class),
        PURCHASE_ORDER_POSITION("purchaseorderposition", DocumentPosition.class),
        CUSTOMER_ORDER("customerorder", CustomerOrderDocumentEntity.class),
        CUSTOMER_ORDER_POSITION("customerorderposition", DocumentPosition.class),
        PROCESSING_PLAN_MATERIAL("processingplanmaterial", ProcessingPlanDocumentEntity.PlanItem.class),
        PROCESSING_PLAN_RESULT("processingplanresult", ProcessingPlanDocumentEntity.PlanItem.class),
        PROCESSING_PLAN("processingplan", ProcessingPlanDocumentEntity.class),
        PROCESSING_ORDER("processingorder", ProcessingOrderDocumentEntity.class),
        PROCESSING_ORDER_POSITION("processingorderposition", DocumentPosition.class),
        PROCESSING("processing", ProcessingDocumentEntity.class),
        PROCESSING_POSITION_RESULT("processingpositionresult", DocumentPosition.class),
        PROCESSING_POSITION_MATERIAL("processingpositionmaterial", DocumentPosition.class),
        EXPENSE_ITEM("expenseitem", ExpenseItemEntity.class),
        CASH_IN("cashin", CashInDocumentEntity.class),
        CASH_OUT("cashout", CashOutDocumentEntity.class),
        PAYMENT_IN("paymentin", PaymentInDocumentEntity.class),
        PAYMENT_OUT("paymentout", PaymentOutDocumentEntity.class),
        PROJECT("project", ProjectEntity.class),
        EMBEDDED_TEMPLATE("embeddedtemplate", TemplateEntity.class),
        CUSTOM_TEMPLATE("customtemplate", TemplateEntity.class),
        ATTRIBUTE_METADATA("attributemetadata", AttributeEntity.class),
        CUSTOM_ENTITY("customentity", CustomEntityElement.class),
        CUSTOM_ENTITY_METADATA("customentitymetadata", CompanySettingsMetadata.CustomEntityMetadata.class),
        PERSONAL_DISCOUNT("personaldiscount", PersonalDiscountEntity.class),
        SPECIAL_PRICE_DISCOUNT("specialpricediscount", SpecialPriceDiscountEntity.class),
        DISCOUNT("discount", DiscountEntity.class),
        BONUS_PROGRAM("bonusprogram", BonusProgramDiscountEntity.class),
        ACCUMULATION_DISCOUNT("accumulationdiscount", AccumulationDiscountEntity.class),
        CONTACT_PERSON("contactperson", ContactPersonEntity.class),
        PRICE_TYPE("pricetype", PriceTypeEntity.class),
        INVOICE_IN("invoicein", InvoiceInDocumentEntity.class),
        INVOICE_OUT("invoiceout", InvoiceOutDocumentEntity.class),
        INVOICE_POSITION("invoiceposition", DocumentPosition.class),
        INTERNAL_ORDER("internalorder", InternalOrderDocumentEntity.class),
        INTERNAL_ORDER_POSITION("internalorderposition", DocumentPosition.class),
        COMMISSION_REPORT_IN("commissionreportin", CommissionReportInDocumentEntity.class),
        COMMISSION_REPORT_IN_POSITION("commissionreportinposition", DocumentPosition.class),
        COMMISSION_REPORT_OUT("commissionreportout", CommissionReportOutDocumentEntity.class),
        COMMISSION_REPORT_OUT_POSITION("commissionreportoutposition", DocumentPosition.class),
        PRICE_LIST("pricelist", PricelistDocumentEntity.class),
        PRICE_LIST_ROW("pricelistrow", PricelistDocumentEntity.PricelistRow.class),
        FACTURE_IN("facturein", FactureInDocumentEntity.class),
        FACTURE_OUT("factureout", FactureOutDocumentEntity.class),
        TASK("task", TaskEntity.class),
        LOSS("loss", LossDocumentEntity.class),
        LOSS_POSITION("lossposition", DocumentPosition.class),
        INVENTORY("inventory", InventoryDocumentEntity.class),
        INVENTORY_POSITION("inventoryposition", DocumentPosition.class),
        COMPANY_SETTINGS("companysettings", CompanySettingsResponse.class),
        CASHIER("cashier", CashierEntity.class)
        ;

        @Getter
        private final Class<? extends MetaEntity> modelClass;
        @Getter
        private final String apiName;

        Type(String apiName, Class<? extends MetaEntity> clazz) {
            this.modelClass = clazz;
            this.apiName = apiName;
        }

        public static Type find(MetaEntity entity) {
            if (TemplateEntity.class.isAssignableFrom(entity.getClass())) {
                if (((TemplateEntity) entity).getIsEmbedded() == null) {
                    return CUSTOM_TEMPLATE;
                }
                return ((TemplateEntity) entity).getIsEmbedded() ? EMBEDDED_TEMPLATE : CUSTOM_TEMPLATE;
            }
            return find(entity.getClass());
        }

        public static Type find(Class<? extends MetaEntity> clazz){
            return Arrays.stream(values())
                    .filter(t -> t.modelClass.getSimpleName().equals(clazz.getSimpleName()))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("No type found for class: " + clazz.getSimpleName()));
        }

        public static Type find(String apiName){
            return Arrays.stream(values())
                    .filter(t -> t.apiName.equals(apiName))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("No type found for string: " + apiName));
        }

        public static class Serializer implements JsonSerializer<Type>, JsonDeserializer<Type> {
            @Override
            public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return Type.find(json.getAsString());
            }

            @Override
            public JsonElement serialize(Type src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
                return context.serialize(src.getApiName());
            }
        }
    }
}
