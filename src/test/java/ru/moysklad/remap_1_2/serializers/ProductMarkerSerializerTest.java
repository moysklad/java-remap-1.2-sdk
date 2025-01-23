package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
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
    public void test_serialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        ProductMarker e = new Product();
        Product epe = ((Product) e);
        epe.setMeta(new Meta());
        ((Product) e).setArchived(null);
        epe.getMeta().setType(Meta.Type.PRODUCT);
        epe.setThings(Arrays.asList("1", "2", "3", "4"));

        assertEquals("{\"meta\":{\"type\":\"PRODUCT\"},\"things\":[\"1\",\"2\",\"3\",\"4\"]}", objectMapper.writeValueAsString(e));
        assertEquals("{\"meta\":{\"type\":\"product\"},\"things\":[\"1\",\"2\",\"3\",\"4\"]}", objectMapperCustom.writeValueAsString(e));
    }

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

    private void deserializationTest(Class<? extends ProductMarker> cl, Meta.Type metaType) throws InstantiationException, IllegalAccessException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        ProductMarker e = cl.newInstance();
        MetaEntity epe = ((MetaEntity) e);
        epe.setMeta(new Meta());
        epe.getMeta().setType(metaType);
        epe.getMeta().setHref(randomString());

        String data = objectMapperCustom.writeValueAsString(e);

        try {
            objectMapper.readValue(data, ProductMarker.class);
            fail("Ожидалось исключение InvalidDefinitionException!");
        } catch (InvalidDefinitionException ex) {
            assertEquals(
                    "Cannot construct instance of `" + ProductMarker.class.getCanonicalName() + "` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information",
                    ex.getOriginalMessage()
            );
        } catch (Exception ex) {
            fail("Ожидалось исключение InvalidDefinitionException!");
        }

        ProductMarker parsed = objectMapperCustom.readValue(data, ProductMarker.class);

        assertEquals(cl, parsed.getClass());
        assertEquals(e, parsed);
    }
}
