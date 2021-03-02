package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.entities.products.markers.ProductMarker;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProductMarkerSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_serialize() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        ProductMarker e = new Product();
        Product epe = ((Product) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(Meta.Type.PRODUCT);
        epe.setThings(Arrays.asList("1", "2", "3", "4"));

        assertEquals("{\"things\":[\"1\",\"2\",\"3\",\"4\"],\"meta\":{\"type\":\"PRODUCT\"}}", gson.toJson(e));
        assertEquals("{\"things\":[\"1\",\"2\",\"3\",\"4\"],\"meta\":{\"type\":\"product\"}}", gsonCustom.toJson(e));
    }

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

    private void deserializationTest(Class<? extends ProductMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson();

        ProductMarker e = cl.newInstance();
        MetaEntity epe = ((MetaEntity) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(metaType);
        epe.getMeta().setHref(randomString());

        String data = gsonCustom.toJson(e);

        try {
            gson.fromJson(data, ProductMarker.class);
            fail("Ожидалось исключение RuntimeException!");
        } catch (Exception ex) {
            if (!(ex instanceof RuntimeException)) fail("Ожидалось исключение RuntimeException!");

            assertEquals(
                    "Unable to invoke no-args constructor for interface " + ProductMarker.class.getCanonicalName() + ". Registering an InstanceCreator with Gson for this type may fix this problem.",
                    ex.getMessage()
            );
        }

        ProductMarker parsed = gsonCustom.fromJson(data, ProductMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
