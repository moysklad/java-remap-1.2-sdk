package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.lognex.api.LognexApi;
import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.MediaType;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class AttributeSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserializeString() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.stringValue);
        e.setValue("STRING");

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"string\",\"value\":\"STRING\"}", gsonCustom.toJson(e));
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.stringValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("STRING", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeLong() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.longValue);
        e.setValue(1234567L);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"long\",\"value\":1234567}", gsonCustom.toJson(e));
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.longValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(1234567L, parsed.getValue());
        assertEquals(Long.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeTime() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.timeValue);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        e.setValue(date);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"time\",\"value\":\"" + date.format(formatter) + "\"}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.timeValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(date, parsed.getValue());
        assertEquals(LocalDateTime.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeFile() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.fileValue);
        e.setValue("picture");
        e.setDownload(new Meta());
        e.getDownload().setHref("[URL]");
        e.getDownload().setMediaType(MediaType.octet_stream);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"file\",\"value\":\"picture\",\"download\":{\"href\":\"[URL]\",\"mediaType\":\"application/octet-stream\"}}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.fileValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("picture", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
        assertNotNull(parsed.getDownload());
        assertEquals("[URL]", parsed.getDownload().getHref());
        assertEquals(MediaType.octet_stream, parsed.getDownload().getMediaType());
    }

    @Test
    public void test_deserializeDouble() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.doubleValue);
        e.setValue(12.345);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"double\",\"value\":12.345}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.doubleValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(12.345, parsed.getValue());
        assertEquals(Double.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeBoolean() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.booleanValue);
        e.setValue(true);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"boolean\",\"value\":true}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.booleanValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals(true, parsed.getValue());
        assertEquals(Boolean.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeText() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.textValue);
        e.setValue(
                "123\n" +
                        "456\n" +
                        "789\n" +
                        "abc\n" +
                        "DEF"
        );

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"text\",\"value\":\"123\\n456\\n789\\nabc\\nDEF\"}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.textValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("123\n456\n789\nabc\nDEF", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeLink() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setType(AttributeEntity.Type.linkValue);
        e.setValue("http://moysklad.ru");

        String data = gsonCustom.toJson(e);

        assertEquals("{\"type\":\"link\",\"value\":\"http://moysklad.ru\"}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(AttributeEntity.Type.linkValue, parsed.getType());
        assertNull(parsed.getEntityType());
        assertEquals("http://moysklad.ru", parsed.getValue());
        assertEquals(String.class, parsed.getValue().getClass());
    }

    @Test
    public void test_deserializeProductEntity() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setEntityType(Meta.Type.product);
        ProductEntity pr = new ProductEntity();
        pr.setMeta(new Meta());
        pr.getMeta().setType(Meta.Type.product);
        pr.setName("PRODUCT");
        e.setValue(pr);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"value\":{\"name\":\"PRODUCT\",\"meta\":{\"type\":\"product\"}},\"type\":\"product\"}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(Meta.Type.product, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(ProductEntity.class, parsed.getValue().getClass());
        assertEquals("PRODUCT", parsed.getValueAs(ProductEntity.class).getName());
    }

    @Test
    public void test_deserializeCustomEntity() {
        Gson gsonCustom = LognexApi.createGson();

        AttributeEntity e = new AttributeEntity();
        e.setEntityType(Meta.Type.customentity);
        CustomEntity ce = new CustomEntity();
        ce.setMeta(new Meta());
        ce.getMeta().setType(Meta.Type.customentity);
        ce.setName("CUSTOM VALUE");
        e.setValue(ce);

        String data = gsonCustom.toJson(e);

        assertEquals("{\"value\":{\"name\":\"CUSTOM VALUE\",\"meta\":{\"type\":\"customentity\"}},\"type\":\"customentity\"}", data);
        AttributeEntity parsed = gsonCustom.fromJson(data, AttributeEntity.class);
        assertEquals(Meta.Type.customentity, parsed.getEntityType());
        assertNull(parsed.getType());
        assertEquals(CustomEntity.class, parsed.getValue().getClass());
        assertEquals("CUSTOM VALUE", parsed.getValueAs(CustomEntity.class).getName());
    }
}
