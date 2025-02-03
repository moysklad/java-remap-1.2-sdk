package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CashIn;
import ru.moysklad.remap_1_2.entities.documents.CashOut;
import ru.moysklad.remap_1_2.entities.documents.PaymentIn;
import ru.moysklad.remap_1_2.entities.documents.PaymentOut;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;

import java.io.IOException;

/**
 * Cериализатор классов-наследников интерфейса <code>FinanceDocumentMarker</code>. В зависимости от метаданных,
 * возвращает экземпляр одного из классов: CashIn, CashOut, PaymentIn,
 * PaymentOut
 */
public class FinanceDocumentMarkerDeserializer extends JsonDeserializer<FinanceDocumentMarker> {
    private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();

    @Override
    public FinanceDocumentMarker deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        MetaEntity me = objectMapper.treeToValue(node, MetaEntity.class);

        if (me.getMeta() == null) {
            throw new JsonParseException(p, "Can't parse field 'payments': meta is null");
        }
        if (me.getMeta().getType() == null) {
            throw new JsonParseException(p, "Can't parse field 'payments': meta.type is null");
        }
        switch (me.getMeta().getType()) {
            case CASH_IN:
                return objectMapper.treeToValue(node, CashIn.class);

            case CASH_OUT:
                return objectMapper.treeToValue(node, CashOut.class);

            case PAYMENT_IN:
                return objectMapper.treeToValue(node, PaymentIn.class);

            case PAYMENT_OUT:
                return objectMapper.treeToValue(node, PaymentOut.class);

            default:
                throw new JsonParseException(p, "Can't parse field 'payments': meta.type must be one of [cashin, cashout, paymentin, paymentout]");
        }
    }
}
