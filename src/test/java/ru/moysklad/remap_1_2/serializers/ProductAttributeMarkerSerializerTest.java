package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.Test;
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
    public void test_deserializeProduct() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(Product.class, Meta.Type.PRODUCT);
    }

    @Test
    public void test_deserializeBundle() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(Bundle.class, Meta.Type.BUNDLE);
    }

    @Test
    public void test_deserializeVariant() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(Variant.class, Meta.Type.VARIANT);
    }

    @Test
    public void test_deserializeService() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(Service.class, Meta.Type.SERVICE);
    }

    @Test
    public void test_deserializeProductFolder() throws IllegalAccessException, InstantiationException, JsonProcessingException {
        deserializationTest(ProductFolder.class, Meta.Type.PRODUCT_FOLDER);
    }

    private void deserializationTest(Class<? extends ProductAttributeMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        ProductAttributeMarker e = cl.newInstance();
        MetaEntity epe = ((MetaEntity) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(metaType);
        epe.getMeta().setHref(randomString());

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapper.readValue(data, ProductAttributeMarker.class);
            fail("Ожидалось исключение InvalidDefinitionException!");
        } catch (InvalidDefinitionException ex) {
            assertEquals(
                    "Cannot construct instance of `" + ProductAttributeMarker.class.getCanonicalName() + "` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information",
                    ex.getOriginalMessage()
            );
        } catch (Exception ex) {
            fail("Ожидалось исключение InvalidDefinitionException!");
        }

        ProductAttributeMarker parsed = objectMapperCustom.readValue(data, ProductAttributeMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
