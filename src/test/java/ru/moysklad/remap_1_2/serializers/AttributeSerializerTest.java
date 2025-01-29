package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class AttributeSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeString() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.stringValue);
        e.setValue("STRING");

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"string\",\"value\":\"STRING\"}", objectMapperCustom.writeValueAsString(e));
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.stringValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("STRING", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeLong() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.longValue);
        e.setValue(1234567L);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"long\",\"value\":1234567}", objectMapperCustom.writeValueAsString(e));
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.longValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(1234567L, parsed.getValue());
        assertEquals(Long.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeTime() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.timeValue);

        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        e.setValue(date);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"time\",\"value\":\"" + date.format(formatter) + "\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.timeValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(date, parsed.getValue());
        assertEquals(LocalDateTime.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeFile() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.fileValue);
        e.setValue("picture");
        e.setDownload(new Meta());
        e.getDownload().setHref("[URL]");
        e.getDownload().setMediaType(MediaType.octet_stream);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"file\",\"value\":\"picture\",\"download\":{\"href\":\"[URL]\",\"mediaType\":\"application/octet-stream\"}}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.fileValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("picture", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
        assertNotNull(parsed.getDownload());
        assertEquals("[URL]", parsed.getDownload().getHref());
        assertEquals(MediaType.octet_stream, parsed.getDownload().getMediaType());
    }

    @Test
    public void test_deserializeDouble() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.doubleValue);
        e.setValue(12.345);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"double\",\"value\":12.345}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.doubleValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(12.345, parsed.getValue());
        assertEquals(Double.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeBoolean() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.booleanValue);
        e.setValue(true);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"boolean\",\"value\":true}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.booleanValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(true, parsed.getValue());
        assertEquals(Boolean.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeText() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.textValue);
        e.setValue(
                "123\n" +
                        "456\n" +
                        "789\n" +
                        "abc\n" +
                        "DEF"
        );

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"text\",\"value\":\"123\\n456\\n789\\nabc\\nDEF\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.textValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("123\n456\n789\nabc\nDEF", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeLink() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.linkValue);
        e.setValue("http://moysklad.ru");

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"link\",\"value\":\"http://moysklad.ru\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.linkValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("http://moysklad.ru", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeProductEntity() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setEntityType(Meta.Type.PRODUCT);
        Product pr = new Product();
        pr.setMeta(new Meta());
        pr.getMeta().setType(Meta.Type.PRODUCT);
        pr.setName("PRODUCT");
        e.setValue(pr);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"value\":{\"name\":\"PRODUCT\",\"meta\":{\"type\":\"product\"}},\"type\":\"product\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Meta.Type.PRODUCT, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(Product.class, parsed.getValue().getClass());
        assertEquals("PRODUCT", parsed.getValueAs(Product.class).getName());
    }

    @Test
    public void test_deserializeProductFolderEntity() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setEntityType(Meta.Type.PRODUCT);
        ProductFolder prf = new ProductFolder();
        prf.setMeta(new Meta());
        prf.getMeta().setType(Meta.Type.PRODUCT_FOLDER);
        prf.setName("PRODUCT_FOLDER");
        e.setValue(prf);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"value\":{\"name\":\"PRODUCT_FOLDER\",\"meta\":{\"type\":\"productfolder\"}},\"type\":\"product\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Meta.Type.PRODUCT, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(ProductFolder.class, parsed.getValue().getClass());
        assertEquals("PRODUCT_FOLDER", parsed.getValueAs(ProductFolder.class).getName());
    }

    @Test
    public void test_deserializeCustomEntity() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Meta customEntityMeta = new Meta();
        customEntityMeta.setHref("customentity/12341234");
        customEntityMeta.setType(Meta.Type.CUSTOM_ENTITY_METADATA);

        Attribute e = new Attribute();
        e.setEntityType(Meta.Type.CUSTOM_ENTITY);
        e.setCustomEntityMeta(customEntityMeta);
        CustomEntityElement ce = new CustomEntityElement();
        ce.setMeta(new Meta());
        ce.getMeta().setType(Meta.Type.CUSTOM_ENTITY);
        ce.setName("CUSTOM VALUE");
        e.setValue(ce);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"value\":{\"name\":\"CUSTOM VALUE\",\"meta\":{\"type\":\"customentity\"}}," +
                "\"customEntityMeta\":{\"href\":\"customentity/12341234\",\"type\":\"customentitymetadata\"}," +
                "\"type\":\"customentity\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Meta.Type.CUSTOM_ENTITY, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(CustomEntityElement.class, parsed.getValue().getClass());
        assertEquals("CUSTOM VALUE", parsed.getValueAs(CustomEntityElement.class).getName());
        assertEquals("customentity/12341234", customEntityMeta.getHref());
        assertEquals(Meta.Type.CUSTOM_ENTITY_METADATA, customEntityMeta.getType());
    }

    @Test
    public void test_deserializeNullTime() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.timeValue);

        String data = objectMapperCustom.writeValueAsString(e);

        assertEquals("{\"type\":\"time\"}", data);
        Attribute parsed = objectMapperCustom.readValue(data, Attribute.class);
        assertEquals(Attribute.Type.timeValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertNull(parsed.getValue());
    }
}
