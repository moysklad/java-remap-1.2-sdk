package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.discounts.*;
import ru.moysklad.remap_1_2.entities.documents.*;
import ru.moysklad.remap_1_2.entities.documents.positions.*;
import ru.moysklad.remap_1_2.entities.notifications.*;
import ru.moysklad.remap_1_2.entities.permissions.EmployeeRole;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
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
        ACCOUNT("account", AgentAccount.class),
        ACCUMULATION_DISCOUNT("accumulationdiscount", AccumulationDiscount.class),
        APPLICATION("application", Application.class),
        ASSORTMENT("assortment", Assortment.class),
        ASSORTMENT_SETTINGS("assortmentsettings", AssortmentSettings.class),
        ATTRIBUTE_METADATA("attributemetadata", DocumentAttribute.class),
        BONUS_PROGRAM("bonusprogram", BonusProgram.class),
        BONUS_TRANSACTION("bonustransaction", BonusTransaction.class),
        BUNDLE("bundle", Bundle.class),
        BUNDLE_COMPONENT("bundlecomponent", Bundle.ComponentEntity.class),
        CASHIER("cashier", Cashier.class),
        CASH_IN("cashin", CashIn.class),
        CASH_OUT("cashout", CashOut.class),
        COMMISSION_REPORT_IN("commissionreportin", CommissionReportIn.class),
        COMMISSION_REPORT_IN_POSITION("commissionreportinposition", CommissionReportDocumentPosition.class),
        COMMISSION_REPORT_OUT("commissionreportout", CommissionReportOut.class),
        COMMISSION_REPORT_OUT_POSITION("commissionreportoutposition", CommissionReportDocumentPosition.class),
        COMPANY_SETTINGS("companysettings", CompanySettings.class),
        CONSIGNMENT("consignment", Consignment.class),
        CONTACT_PERSON("contactperson", ContactPerson.class),
        CONTRACT("contract", Contract.class),
        CONTRACT_PUBLICATION("contractpublication", Publication.class),
        COUNTERPARTY("counterparty", Counterparty.class),
        COUNTRY("country", Country.class),
        CURRENCY("currency", Currency.class),
        CUSTOMER_ORDER("customerorder", CustomerOrder.class),
        CUSTOMER_ORDER_POSITION("customerorderposition", CustomerOrderDocumentPosition.class),
        CUSTOM_ENTITY("customentity", CustomEntityElement.class),
        CUSTOM_ENTITY_METADATA("customentitymetadata", CompanySettingsMetadata.CustomEntityMetadata.class),
        CUSTOM_TEMPLATE("customtemplate", Template.class),
        DEMAND("demand", Demand.class),
        DEMAND_POSITION("demandposition", DemandDocumentPosition.class),
        DISCOUNT("discount", Discount.class),
        EMBEDDED_TEMPLATE("embeddedtemplate", Template.class),
        EMPLOYEE("employee", Employee.class),
        ENTER("enter", Enter.class),
        ENTER_POSITION("enterposition", EnterDocumentPosition.class),
        EXPENSE_ITEM("expenseitem", ExpenseItem.class),
        FACTURE_IN("facturein", FactureIn.class),
        FACTURE_OUT("factureout", FactureOut.class),
        GROUP("group", Group.class),
        IMAGE("image", Image.class),
        FILE("files", AttachedFile.class),
        INTERNAL_ORDER("internalorder", InternalOrder.class),
        INTERNAL_ORDER_POSITION("internalorderposition", InternalOrderDocumentPosition.class),
        INVENTORY("inventory", Inventory.class),
        INVENTORY_POSITION("inventoryposition", InventoryDocumentPosition.class),
        INVOICE_IN("invoicein", InvoiceIn.class),
        INVOICE_OUT("invoiceout", InvoiceOut.class),
        INVOICE_POSITION("invoiceposition", InvoiceDocumentPosition.class),
        LOSS("loss", Loss.class),
        LOSS_POSITION("lossposition", LossDocumentPosition.class),
        MOVE("move", Move.class),
        MOVE_POSITION("moveposition", MoveDocumentPosition.class),
        NOTE("note", Note.class),
        NOTIFICATION("notification", Notification.class),
        NOTIFICATION_EXPORT_COMPLETED("NotificationExportCompleted", NotificationExchange.class),
        NOTIFICATION_GOOD_COUNT_TOO_LOW("NotificationGoodCountTooLow", NotificationGoodCountTooLow.class),
        NOTIFICATION_IMPORT_COMPLETED("NotificationImportCompleted", NotificationExchange.class),
        NOTIFICATION_INVOICE_OUT_OVERDUE("NotificationInvoiceOutOverdue", NotificationInvoiceOutOverdue.class),
        NOTIFICATION_ORDER_NEW("NotificationOrderNew", NotificationCustomerOrder.class),
        NOTIFICATION_ORDER_OVERDUE("NotificationOrderOverdue", NotificationCustomerOrder.class),
        NOTIFICATION_RETAIL_SHIFT_CLOSED("NotificationRetailShiftClosed", NotificationRetailShiftClosed.class),
        NOTIFICATION_RETAIL_SHIFT_OPENED("NotificationRetailShiftOpened", NotificationRetailShift.class),
        NOTIFICATION_SUBSCRIBE_EXPIRED("NotificationSubscribeExpired", NotificationSubscribeExpired.class),
        NOTIFICATION_SUBSCRIBE_TERMS_EXPIRED("NotificationSubscribeTermsExpired", NotificationSubscribeTermsExpired.class),
        NOTIFICATION_TASK_ASSIGNED("NotificationTaskAssigned", NotificationTask.class),
        NOTIFICATION_TASK_CHANGED("NotificationTaskChanged", NotificationTaskChanged.class),
        NOTIFICATION_TASK_COMMENT_CHANGED("NotificationTaskCommentChanged", NotificationTaskChanged.class),
        NOTIFICATION_TASK_COMMENT_DELETED("NotificationTaskCommentDeleted", NotificationTaskComment.class),
        NOTIFICATION_TASK_COMPLETED("NotificationTaskCompleted", NotificationTask.class),
        NOTIFICATION_TASK_DELETED("NotificationTaskDeleted", NotificationTask.class),
        NOTIFICATION_TASK_NEW_COMMENT("NotificationTaskNewComment", NotificationTaskComment.class),
        NOTIFICATION_TASK_OVERDUE("NotificationTaskOverdue", NotificationTask.class),
        NOTIFICATION_TASK_REOPENED("NotificationTaskReopened", NotificationTask.class),
        NOTIFICATION_TASK_UNASSIGNED("NotificationTaskUnassigned", NotificationTask.class),
        OPERATION_PUBLICATION("operationpublication", Publication.class),
        ORGANIZATION("organization", Organization.class),
        PAYMENT_IN("paymentin", PaymentIn.class),
        PAYMENT_OUT("paymentout", PaymentOut.class),
        PERSONAL_DISCOUNT("personaldiscount", PersonalDiscount.class),
        PREPAYMENT("prepayment", Prepayment.class),
        PREPAYMENT_POSITION("prepaymentposition", PrepaymentDocumentPosition.class),
        PREPAYMENT_RETURN("prepaymentreturn", PrepaymentReturn.class),
        PREPAYMENT_RETURN_POSITION("prepaymentreturnposition", PrepaymentDocumentPosition.class),
        PRICE_LIST("pricelist", Pricelist.class),
        PRICE_LIST_ROW("pricelistrow", Pricelist.PricelistRow.class),
        PRICE_TYPE("pricetype", PriceType.class),
        PROCESSING("processing", Processing.class),
        PROCESSING_ORDER("processingorder", ProcessingOrder.class),
        PROCESSING_ORDER_POSITION("processingorderposition", ProcessingOrderPosition.class),
        PROCESSING_PLAN("processingplan", ProcessingPlan.class),
        PROCESSING_PLAN_MATERIAL("processingplanmaterial", ProcessingPlan.ProductItem.class),
        PROCESSING_PLAN_RESULT("processingplanresult", ProcessingPlan.ProductItem.class),
        PROCESSING_PLAN_STAGE("processingplanstage", ProcessingPlan.StageItem.class),
        PROCESSING_POSITION_MATERIAL("processingpositionmaterial", DocumentPosition.class),
        PROCESSING_POSITION_RESULT("processingpositionresult", DocumentPosition.class),
        PROCESSING_STAGE("processingstage", ProcessingStage.class),
        PROCESSING_PROCESS("processingprocess", ProcessingProcess.class),
        PROCESSING_PROCESS_POSITION("processingprocessposition", ProcessingProcess.ProcessPosition.class),
        PRODUCT("product", Product.class),
        PRODUCT_FOLDER("productfolder", ProductFolder.class),
        PROJECT("project", Project.class),
        PURCHASE_ORDER("purchaseorder", PurchaseOrder.class),
        PURCHASE_ORDER_POSITION("purchaseorderposition", PurchaseOrderDocumentPosition.class),
        PURCHASE_RETURN("purchasereturn", PurchaseReturn.class),
        PURCHASE_RETURN_POSITION("purchasereturnposition", PurchaseReturnDocumentPosition.class),
        RECEIPT_TEMPLATE("receipttemplate", ReceiptTemplate.class),
        REGION("region", Region.class),
        RETAIL_DEMAND("retaildemand", RetailDemand.class),
        RETAIL_DRAWER_CASH_IN("retaildrawercashin", RetailDrawerCashIn.class),
        RETAIL_DRAWER_CASH_OUT("retaildrawercashout", RetailDrawerCashOut.class),
        RETAIL_SALES_RETURN("retailsalesreturn", RetailSalesReturn.class),
        RETAIL_SHIFT("retailshift", RetailShift.class),
        RETAIL_STORE("retailstore", RetailStore.class),
        ROUND_OFF_DISCOUNT("roundoffdiscount", RoundOffDiscount.class),
        SALES_RETURN("salesreturn", SalesReturn.class),
        SALES_RETURN_POSITION("salesreturnposition", SalesReturnDocumentPosition.class),
        SERVICE("service", Service.class),
        SPECIAL_PRICE_DISCOUNT("specialpricediscount", SpecialPriceDiscount.class),
        STATE("state", State.class),
        STORE("store", Store.class),
        SUPPLY("supply", Supply.class),
        SUPPLY_POSITION("supplyposition", SupplyDocumentPosition.class),
        TASK("task", Task.class),
        TASK_NOTE("tasknote", Task.TaskNote.class),
        UOM("uom", Uom.class),
        VARIANT("variant", Variant.class),
        WEBHOOK("webhook", WebHook.class),
        COUNTERPARTY_SETTINGS("counterpartysettings", CounterpartySettings.class),
        SYSTEM_ROLE("systemrole", EmployeeRole.class),
        INDIVIDUAL_ROLE("individualrole", EmployeeRole.class),
        CUSTOM_ROLE("customrole", EmployeeRole.class)
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
            if (Template.class.isAssignableFrom(entity.getClass())) {
                if (((Template) entity).getIsEmbedded() == null) {
                    return CUSTOM_TEMPLATE;
                }
                return ((Template) entity).getIsEmbedded() ? EMBEDDED_TEMPLATE : CUSTOM_TEMPLATE;
            } else if (Publication.class.isAssignableFrom(entity.getClass())) {
                if (((Publication) entity).getType() == null) {
                    return OPERATION_PUBLICATION;
                }
                return ((Publication) entity).getType() == Publication.PublicationType.OPERATION ? OPERATION_PUBLICATION : CONTRACT_PUBLICATION;
            }
            return find(entity.getClass());
        }

        private static Type find(Class<? extends MetaEntity> clazz) {
            return Arrays.stream(values())
                    .filter(t -> t.modelClass.getSimpleName().equals(clazz.getSimpleName()))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("No type found for class: " + clazz.getSimpleName()));
        }

        public static Type find(String apiName) {
            return Arrays.stream(values())
                    .filter(t -> t.apiName.equals(apiName))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("No type found for string: " + apiName));
        }

        public static class Serializer extends StdSerializer<Type> {
            public Serializer() {
                super(Type.class);
            }

            @Override
            public void serialize(Type value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                gen.writeString(value.getApiName());
            }
        }

        public static class Deserializer extends JsonDeserializer<Type> {
            @Override
            public Type deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                JsonNode node = p.getCodec().readTree(p);
                return Type.find(node.asText());
            }
        }
    }
}
