package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import org.junit.Test;
import com.google.gson.GsonBuilder;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProductFolder;
import ru.moysklad.remap_1_2.entities.products.Bundle;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.entities.products.Service;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.entities.products.markers.ProductAttributeMarker;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

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
                    "Unable to invoke no-args constructor for interface " + ProductAttributeMarker.class.getCanonicalName() + ". Registering an InstanceCreator with Gson for this type may fix this problem.",
                    ex.getMessage()
            );
        }

        ProductAttributeMarker parsed = gsonCustom.fromJson(data, ProductAttributeMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
