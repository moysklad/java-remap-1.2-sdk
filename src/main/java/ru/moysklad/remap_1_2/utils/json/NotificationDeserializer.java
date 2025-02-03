package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.notifications.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class NotificationDeserializer extends JsonDeserializer<Notification> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();

    @Override
    public Notification deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        MetaEntity me = objectMapper.treeToValue(node, MetaEntity.class);

        if (me.getMeta() == null) {
            throw new JsonParseException(p, "Can't parse notification: meta is null");
        }
        if (me.getMeta().getType() == null) {
            throw new JsonParseException(p, "Can't parse notification: meta.type is null");
        }

        switch (me.getMeta().getType()) {
            case NOTIFICATION_ORDER_NEW:
            case NOTIFICATION_ORDER_OVERDUE:
                return p.getCodec().treeToValue(node, NotificationCustomerOrder.class);

            case NOTIFICATION_INVOICE_OUT_OVERDUE:
                return p.getCodec().treeToValue(node, NotificationInvoiceOutOverdue.class);

            case NOTIFICATION_GOOD_COUNT_TOO_LOW:
                return p.getCodec().treeToValue(node, NotificationGoodCountTooLow.class);

            case NOTIFICATION_TASK_ASSIGNED:
            case NOTIFICATION_TASK_UNASSIGNED:
            case NOTIFICATION_TASK_OVERDUE:
            case NOTIFICATION_TASK_COMPLETED:
            case NOTIFICATION_TASK_REOPENED:
            case NOTIFICATION_TASK_DELETED:
                return p.getCodec().treeToValue(node, NotificationTask.class);

            case NOTIFICATION_TASK_CHANGED:
            case NOTIFICATION_TASK_COMMENT_CHANGED:
                return p.getCodec().treeToValue(node, NotificationTaskChanged.class);

            case NOTIFICATION_TASK_COMMENT_DELETED:
            case NOTIFICATION_TASK_NEW_COMMENT:
                return p.getCodec().treeToValue(node, NotificationTaskComment.class);

            case NOTIFICATION_IMPORT_COMPLETED:
            case NOTIFICATION_EXPORT_COMPLETED:
                return p.getCodec().treeToValue(node, NotificationExchange.class);

            case NOTIFICATION_SUBSCRIBE_EXPIRED:
                return p.getCodec().treeToValue(node, NotificationSubscribeExpired.class);

            case NOTIFICATION_SUBSCRIBE_TERMS_EXPIRED:
                return p.getCodec().treeToValue(node, NotificationSubscribeTermsExpired.class);

            case NOTIFICATION_RETAIL_SHIFT_OPENED:
                return p.getCodec().treeToValue(node, NotificationRetailShift.class);

            case NOTIFICATION_RETAIL_SHIFT_CLOSED:
                return p.getCodec().treeToValue(node, NotificationRetailShiftClosed.class);

            default:
                throw new JsonParseException(p, "Can't parse notification: meta.type must be one of \"Notification*\"");
        }
    }
}
