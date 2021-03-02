package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.notifications.*;

public class NotificationDeserializer implements JsonDeserializer<Notification> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public Notification deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) {
            throw new JsonParseException("Can't parse notification: meta is null");
        }
        if (me.getMeta().getType() == null) {
            throw new JsonParseException("Can't parse notification: meta.type is null");
        }

        switch (me.getMeta().getType()) {
            case NOTIFICATION_ORDER_NEW:
            case NOTIFICATION_ORDER_OVERDUE:
                return context.deserialize(json, NotificationCustomerOrder.class);

            case NOTIFICATION_INVOICE_OUT_OVERDUE:
                return context.deserialize(json, NotificationInvoiceOutOverdue.class);

            case NOTIFICATION_GOOD_COUNT_TOO_LOW:
                return context.deserialize(json, NotificationGoodCountTooLow.class);

            case NOTIFICATION_TASK_ASSIGNED:
            case NOTIFICATION_TASK_UNASSIGNED:
            case NOTIFICATION_TASK_OVERDUE:
            case NOTIFICATION_TASK_COMPLETED:
            case NOTIFICATION_TASK_REOPENED:
            case NOTIFICATION_TASK_DELETED:
                return context.deserialize(json, NotificationTask.class);

            case NOTIFICATION_TASK_CHANGED:
            case NOTIFICATION_TASK_COMMENT_CHANGED:
                return context.deserialize(json, NotificationTaskChanged.class);

            case NOTIFICATION_TASK_COMMENT_DELETED:
            case NOTIFICATION_TASK_NEW_COMMENT:
                return context.deserialize(json, NotificationTaskComment.class);

            case NOTIFICATION_IMPORT_COMPLETED:
            case NOTIFICATION_EXPORT_COMPLETED:
                return context.deserialize(json, NotificationExchange.class);

            case NOTIFICATION_SUBSCRIBE_EXPIRED:
                return context.deserialize(json, NotificationSubscribeExpired.class);

            case NOTIFICATION_SUBSCRIBE_TERMS_EXPIRED:
                return context.deserialize(json, NotificationSubscribeTermsExpired.class);

            case NOTIFICATION_RETAIL_SHIFT_OPENED:
                return context.deserialize(json, NotificationRetailShift.class);

            case NOTIFICATION_RETAIL_SHIFT_CLOSED:
                return context.deserialize(json, NotificationRetailShiftClosed.class);

            default:
                throw new JsonParseException("Can't parse notification: meta.type must be one of \"Notification*\"");
        }
    }
}
