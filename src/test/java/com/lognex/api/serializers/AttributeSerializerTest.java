package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.MediaType;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.products.Product;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class AttributeSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeString() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.stringValue);
        e.setValue("STRING");

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"string\",\"value\":\"STRING\"}", gsonCustom.toJson(e));
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.stringValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("STRING", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeLong() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.longValue);
        e.setValue(1234567L);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"long\",\"value\":1234567}", gsonCustom.toJson(e));
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.longValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(1234567L, parsed.getValue());
        assertEquals(Long.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeTime() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.timeValue);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        e.setValue(date);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"time\",\"value\":\"" + date.format(formatter) + "\"}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.timeValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(date, parsed.getValue());
        assertEquals(LocalDateTime.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeFile() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.fileValue);
        e.setValue("picture");
        e.setDownload(new Meta());
        e.getDownload().setHref("[URL]");
        e.getDownload().setMediaType(MediaType.octet_stream);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"file\",\"value\":\"picture\",\"download\":{\"href\":\"[URL]\",\"mediaType\":\"application/octet-stream\"}}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.fileValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("picture", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
        assertNotNull(parsed.getDownload());
        assertEquals("[URL]", parsed.getDownload().getHref());
        assertEquals(MediaType.octet_stream, parsed.getDownload().getMediaType());
    }

    @Test
    public void test_deserializeDouble() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.doubleValue);
        e.setValue(12.345);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"double\",\"value\":12.345}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.doubleValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(12.345, parsed.getValue());
        assertEquals(Double.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeBoolean() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.booleanValue);
        e.setValue(true);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"boolean\",\"value\":true}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.booleanValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(true, parsed.getValue());
        assertEquals(Boolean.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeText() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.textValue);
        e.setValue(
                "123\n" +
                        "456\n" +
                        "789\n" +
                        "abc\n" +
                        "DEF"
        );

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"text\",\"value\":\"123\\n456\\n789\\nabc\\nDEF\"}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.textValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("123\n456\n789\nabc\nDEF", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeLink() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setType(Attribute.Type.linkValue);
        e.setValue("http://moysklad.ru");

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"link\",\"value\":\"http://moysklad.ru\"}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Attribute.Type.linkValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("http://moysklad.ru", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeProductEntity() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setEntityType(Meta.Type.PRODUCT);
        Product pr = new Product();
        pr.setMeta(new Meta());
        pr.getMeta().setType(Meta.Type.PRODUCT);
        pr.setName("PRODUCT");
        e.setValue(pr);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"value\":{\"name\":\"PRODUCT\",\"meta\":{\"type\":\"product\"}},\"type\":\"product\"}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Meta.Type.PRODUCT, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(Product.class, parsed.getValue().getClass());
        assertEquals("PRODUCT", parsed.getValueAs(Product.class).getName());
    }

    @Test
    public void test_deserializeCustomEntity() {
        Gson gsonCustom = ApiClient.createGson();

        Attribute e = new Attribute();
        e.setEntityType(Meta.Type.CUSTOM_ENTITY);
        CustomEntity ce = new CustomEntity();
        ce.setMeta(new Meta());
        ce.getMeta().setType(Meta.Type.CUSTOM_ENTITY);
        ce.setName("CUSTOM VALUE");
        e.setValue(ce);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"value\":{\"name\":\"CUSTOM VALUE\",\"meta\":{\"type\":\"customentity\"}},\"type\":\"customentity\"}", data);
        Attribute parsed = gsonCustom.fromJson(data, Attribute.class);
        assertEquals(Meta.Type.CUSTOM_ENTITY, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(CustomEntity.class, parsed.getValue().getClass());
        assertEquals("CUSTOM VALUE", parsed.getValueAs(CustomEntity.class).getName());
    }
}
