package com.lognex.api.utils.json;

import com.google.gson.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CashInDocumentEntity;
import com.lognex.api.entities.documents.CashOutDocumentEntity;
import com.lognex.api.entities.documents.PaymentInDocumentEntity;
import com.lognex.api.entities.documents.PaymentOutDocumentEntity;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;

import java.lang.reflect.Type;

/**
 * Cериализатор классов-наследников интерфейса <code>FinanceDocumentMarker</code>. В зависимости от метаданных,
 * возвращает экземпляр одного из классов: CashInDocumentEntity, CashOutDocumentEntity, PaymentInDocumentEntity,
 * PaymentOutDocumentEntity
 */
public class FinanceDocumentMarkerSerializer implements JsonSerializer<FinanceDocumentMarker>, JsonDeserializer<FinanceDocumentMarker> {
    private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

    @Override
    public JsonElement serialize(FinanceDocumentMarker src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    @Override
    public FinanceDocumentMarker deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MetaEntity me = gson.fromJson(json, MetaEntity.class);

        if (me.getMeta() == null) throw new JsonParseException("Can't parse field 'payments': meta is null");
        if (me.getMeta().getType() == null)
            throw new JsonParseException("Can't parse field 'payments': meta.type is null");

        switch (me.getMeta().getType()) {
            case CASH_IN:
                return context.deserialize(json, CashInDocumentEntity.class);

            case CASH_OUT:
                return context.deserialize(json, CashOutDocumentEntity.class);

            case PAYMENT_IN:
                return context.deserialize(json, PaymentInDocumentEntity.class);

            case PAYMENT_OUT:
                return context.deserialize(json, PaymentOutDocumentEntity.class);

            default:
                throw new JsonParseException("Can't parse field 'payments': meta.type must be one of [cashin, cashout, paymentin, paymentout]");
        }
    }
}
