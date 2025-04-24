package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CashIn;
import ru.moysklad.remap_1_2.entities.documents.CashOut;
import ru.moysklad.remap_1_2.entities.documents.PaymentIn;
import ru.moysklad.remap_1_2.entities.documents.PaymentOut;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceInDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceOutDocumentMarker;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FinanceDocumentMarkerSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        {
            FinanceDocumentMarker e = new CashIn();
            CashIn epe = ((CashIn) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.CASH_IN);
            epe.setVatSum(1234098745L);

            assertEquals("{\"meta\":{\"type\":\"CASH_IN\"},\"vatSum\":1234098745}", objectMapper.writeValueAsString(e));
            assertEquals("{\"meta\":{\"type\":\"cashin\"},\"vatSum\":1234098745}", objectMapperCustom.writeValueAsString(e));
        }

        {
            FinanceInDocumentMarker e = new PaymentIn();
            PaymentIn epe = ((PaymentIn) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.PAYMENT_IN);
            epe.setVatSum(94356340L);

            assertEquals("{\"meta\":{\"type\":\"PAYMENT_IN\"},\"vatSum\":94356340}", objectMapper.writeValueAsString(e));
            assertEquals("{\"meta\":{\"type\":\"paymentin\"},\"vatSum\":94356340}", objectMapperCustom.writeValueAsString(e));
        }

        {
            FinanceOutDocumentMarker e = new CashOut();
            CashOut epe = ((CashOut) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.CASH_OUT);
            epe.setVatSum(435764L);

            assertEquals("{\"meta\":{\"type\":\"CASH_OUT\"},\"vatSum\":435764}", objectMapper.writeValueAsString(e));
            assertEquals("{\"meta\":{\"type\":\"cashout\"},\"vatSum\":435764}", objectMapperCustom.writeValueAsString(e));
        }
    }

    @Test
    public void test_deserializeCashIn() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(CashIn.class, Meta.Type.CASH_IN);
    }

    @Test
    public void test_deserializeCashOut() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(CashOut.class, Meta.Type.CASH_OUT);
    }

    @Test
    public void test_deserializePaymentIn() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(PaymentIn.class, Meta.Type.PAYMENT_IN);
    }

    @Test
    public void test_deserializePaymentOut() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(PaymentOut.class, Meta.Type.PAYMENT_OUT);
    }

    private void deserializationTest(Class<? extends FinanceDocumentMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        FinanceDocumentMarker e = cl.newInstance();
        MetaEntity epe = ((MetaEntity) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(metaType);
        epe.getMeta().setHref(randomString());

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapper.readValue(data, FinanceDocumentMarker.class);
            fail("Ожидалось исключение InvalidDefinitionException!");
        } catch (InvalidDefinitionException ex) {
            assertEquals(
                    "Cannot construct instance of `" + FinanceDocumentMarker.class.getCanonicalName() + "` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information",
                    ex.getOriginalMessage()
            );
        } catch (Exception ex) {
            fail("Ожидалось исключение InvalidDefinitionException!");
        }

        FinanceDocumentMarker parsed = objectMapperCustom.readValue(data, FinanceDocumentMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
