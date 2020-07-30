package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.ProductFolder;
import com.lognex.api.entities.products.Bundle;
import com.lognex.api.entities.products.Product;
import com.lognex.api.entities.products.Service;
import com.lognex.api.entities.products.Variant;
import com.lognex.api.entities.products.markers.ProductAttributeMarker;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProductAttributeMarkerSerializerTest implements TestAsserts, TestRandomizers {

    @Test
    public void test_deserializeProduct() throws IllegalAccessException, InstantiationException {
        deserializationTest(Product.class, Meta.Type.PRODUCT);
    }

    @Test
    public void test_deserializeBundle() throws IllegalAccessException, InstantiationException {
        deserializationTest(Bundle.class, Meta.Type.BUNDLE);
    }

    @Test
    public void test_deserializeVariant() throws IllegalAccessException, InstantiationException {
        deserializationTest(Variant.class, Meta.Type.VARIANT);
    }

    @Test
    public void test_deserializeService() throws IllegalAccessException, InstantiationException {
        deserializationTest(Service.class, Meta.Type.SERVICE);
    }

    @Test
    public void test_deserializeProductFolder() throws IllegalAccessException, InstantiationException {
        deserializationTest(ProductFolder.class, Meta.Type.PRODUCT_FOLDER);
    }

    private void deserializationTest(Class<? extends ProductAttributeMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        ProductAttributeMarker e = cl.newInstance();
        MetaEntity epe = ((MetaEntity) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(metaType);
        epe.getMeta().setHref(randomString());

        String data = gsonCustom.toJson(e);

        try {
            gson.fromJson(data, ProductAttributeMarker.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (Exception ex) {
            if (!(ex instanceof RuntimeException)) fail("Ожидалось исключение RuntimeException!");

            assertEquals(
                    "Unable to invoke no-args constructor for interface com.lognex.api.entities.products.markers.ProductAttributeMarker. Registering an InstanceCreator with Gson for this type may fix this problem.",
                    ex.getMessage()
            );
        }

        ProductAttributeMarker parsed = gsonCustom.fromJson(data, ProductAttributeMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
