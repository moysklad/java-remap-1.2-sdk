package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.EntityTestBase;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;

public class ProductionTaskTest extends EntityTestBase {

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().productionTask().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }
}
