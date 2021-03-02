package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public void test_serialize() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        {
            FinanceDocumentMarker e = new CashIn();
            CashIn epe = ((CashIn) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.CASH_IN);
            epe.setVatSum(1234098745L);

            assertEquals("{\"vatSum\":1234098745,\"meta\":{\"type\":\"CASH_IN\"}}", gson.toJson(e));
            assertEquals("{\"vatSum\":1234098745,\"meta\":{\"type\":\"cashin\"}}", gsonCustom.toJson(e));
        }

        {
            FinanceInDocumentMarker e = new PaymentIn();
            PaymentIn epe = ((PaymentIn) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.PAYMENT_IN);
            epe.setVatSum(94356340L);

            assertEquals("{\"vatSum\":94356340,\"meta\":{\"type\":\"PAYMENT_IN\"}}", gson.toJson(e));
            assertEquals("{\"vatSum\":94356340,\"meta\":{\"type\":\"paymentin\"}}", gsonCustom.toJson(e));
        }

        {
            FinanceOutDocumentMarker e = new CashOut();
            CashOut epe = ((CashOut) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.CASH_OUT);
            epe.setVatSum(435764L);

            assertEquals("{\"vatSum\":435764,\"meta\":{\"type\":\"CASH_OUT\"}}", gson.toJson(e));
            assertEquals("{\"vatSum\":435764,\"meta\":{\"type\":\"cashout\"}}", gsonCustom.toJson(e));
        }
    }

    @Test
    public void test_deserializeCashIn() throws IllegalAccessException, InstantiationException {
        deserializationTest(CashIn.class, Meta.Type.CASH_IN);
    }

    @Test
    public void test_deserializeCashOut() throws IllegalAccessException, InstantiationException {
        deserializationTest(CashOut.class, Meta.Type.CASH_OUT);
    }

    @Test
    public void test_deserializePaymentIn() throws IllegalAccessException, InstantiationException {
        deserializationTest(PaymentIn.class, Meta.Type.PAYMENT_IN);
    }

    @Test
    public void test_deserializePaymentOut() throws IllegalAccessException, InstantiationException {
        deserializationTest(PaymentOut.class, Meta.Type.PAYMENT_OUT);
    }

    private void deserializationTest(Class<? extends FinanceDocumentMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        FinanceDocumentMarker e = cl.newInstance();
        MetaEntity epe = ((MetaEntity) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(metaType);
        epe.getMeta().setHref(randomString());

        String data = gsonCustom.toJson(e);

        try {
            gson.fromJson(data, FinanceDocumentMarker.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (Exception ex) {
            if (!(ex instanceof RuntimeException)) fail("Ожидалось исключение RuntimeException!");

            assertEquals(
                    "Unable to invoke no-args constructor for interface " + FinanceDocumentMarker.class.getCanonicalName() + ". Registering an InstanceCreator with Gson for this type may fix this problem.",
                    ex.getMessage()
            );
        }

        FinanceDocumentMarker parsed = gsonCustom.fromJson(data, FinanceDocumentMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
