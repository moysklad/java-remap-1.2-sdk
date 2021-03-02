package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CashIn;
import ru.moysklad.remap_1_2.entities.documents.CashOut;
import ru.moysklad.remap_1_2.entities.documents.PaymentIn;
import ru.moysklad.remap_1_2.entities.documents.PaymentOut;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;

import java.lang.reflect.Type;

/**
 * Cериализатор классов-наследников интерфейса <code>FinanceDocumentMarker</code>. В зависимости от метаданных,
 * возвращает экземпляр одного из классов: CashIn, CashOut, PaymentIn,
 * PaymentOut
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
                return context.deserialize(json, CashIn.class);

            case CASH_OUT:
                return context.deserialize(json, CashOut.class);

            case PAYMENT_IN:
                return context.deserialize(json, PaymentIn.class);

            case PAYMENT_OUT:
                return context.deserialize(json, PaymentOut.class);

            default:
                throw new JsonParseException("Can't parse field 'payments': meta.type must be one of [cashin, cashout, paymentin, paymentout]");
        }
    }
}
