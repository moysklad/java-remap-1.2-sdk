package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lognex.api.LognexApi;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CashInDocumentEntity;
import com.lognex.api.entities.documents.CashOutDocumentEntity;
import com.lognex.api.entities.documents.PaymentInDocumentEntity;
import com.lognex.api.entities.documents.PaymentOutDocumentEntity;
import com.lognex.api.entities.documents.markers.FinanceDocumentMarker;
import com.lognex.api.entities.documents.markers.FinanceInDocumentMarker;
import com.lognex.api.entities.documents.markers.FinanceOutDocumentMarker;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class FinanceDocumentMarkerSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_serialize() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

        {
            FinanceDocumentMarker e = new CashInDocumentEntity();
            CashInDocumentEntity epe = ((CashInDocumentEntity) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.CASH_IN);
            epe.setVatSum(1234098745L);

            assertEquals("{\"vatSum\":1234098745,\"meta\":{\"type\":\"CASH_IN\"}}", gson.toJson(e));
            assertEquals("{\"vatSum\":1234098745,\"meta\":{\"type\":\"cashin\"}}", gsonCustom.toJson(e));
        }

        {
            FinanceInDocumentMarker e = new PaymentInDocumentEntity();
            PaymentInDocumentEntity epe = ((PaymentInDocumentEntity) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.PAYMENT_IN);
            epe.setVatSum(94356340L);

            assertEquals("{\"vatSum\":94356340,\"meta\":{\"type\":\"PAYMENT_IN\"}}", gson.toJson(e));
            assertEquals("{\"vatSum\":94356340,\"meta\":{\"type\":\"paymentin\"}}", gsonCustom.toJson(e));
        }

        {
            FinanceOutDocumentMarker e = new CashOutDocumentEntity();
            CashOutDocumentEntity epe = ((CashOutDocumentEntity) e);
            epe.setMeta(new Meta());
            epe.getMeta().setType(Meta.Type.CASH_OUT);
            epe.setVatSum(435764L);

            assertEquals("{\"vatSum\":435764,\"meta\":{\"type\":\"CASH_OUT\"}}", gson.toJson(e));
            assertEquals("{\"vatSum\":435764,\"meta\":{\"type\":\"cashout\"}}", gsonCustom.toJson(e));
        }
    }

    @Test
    public void test_deserializeCashIn() throws IllegalAccessException, InstantiationException {
        deserializationTest(CashInDocumentEntity.class, Meta.Type.CASH_IN);
    }

    @Test
    public void test_deserializeCashOut() throws IllegalAccessException, InstantiationException {
        deserializationTest(CashOutDocumentEntity.class, Meta.Type.CASH_OUT);
    }

    @Test
    public void test_deserializePaymentIn() throws IllegalAccessException, InstantiationException {
        deserializationTest(PaymentInDocumentEntity.class, Meta.Type.PAYMENT_IN);
    }

    @Test
    public void test_deserializePaymentOut() throws IllegalAccessException, InstantiationException {
        deserializationTest(PaymentOutDocumentEntity.class, Meta.Type.PAYMENT_OUT);
    }

    private void deserializationTest(Class<? extends FinanceDocumentMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson();

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
                    "Unable to invoke no-args constructor for interface com.lognex.api.entities.documents.markers.FinanceDocumentMarker. Registering an InstanceCreator with Gson for this type may fix this problem.",
                    ex.getMessage()
            );
        }

        FinanceDocumentMarker parsed = gsonCustom.fromJson(data, FinanceDocumentMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
