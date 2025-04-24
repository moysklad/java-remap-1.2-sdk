package ru.moysklad.remap_1_2.entities.documents;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.EntityTestBase;
import ru.moysklad.remap_1_2.entities.FilesTest;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.TestUtils;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class PrepaymentTest extends EntityTestBase implements FilesTest<Prepayment> {

    @Test
    public void deserializePrepayment() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Prepayment prepayment = objectMapper.readValue(
                TestUtils.getFile("documentsJson/prepayment.json"), Prepayment.class
        );

        assertEquals(Meta.Type.PREPAYMENT, prepayment.getMeta().getType());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/prepayment/7944ef04-f831-11e5-7a69-971500188b19",
                prepayment.getMeta().getHref()
        );
        assertEquals("7944ef04-f831-11e5-7a69-971500188b19", prepayment.getId());
        assertEquals("00004", prepayment.getName());
        assertTrue(prepayment.getApplicable());
        assertFalse(prepayment.getPublished());
        assertFalse(prepayment.getPrinted());
        assertFalse(prepayment.getShared());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/78d0453c-2e92-11e9-ac12-000e0000002f",
                prepayment.getOwner().getMeta().getHref()
        );
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/group/783fd0fd-2e92-11e9-ac12-000b00000002",
                prepayment.getGroup().getMeta().getHref()
        );
        assertNotNull(prepayment.getUpdated());
        assertNotNull(prepayment.getMoment());
        assertNotNull(prepayment.getCreated());
        assertEquals("Комментарий", prepayment.getDescription());
        assertEquals("wkOsJvdDguUeVJOB-g1LN1", prepayment.getExternalCode());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/currency/790def39-2e92-11e9-ac12-000e00000061",
                prepayment.getRate().getCurrency().getMeta().getHref()
        );
        assertEquals(Long.valueOf(30000), prepayment.getSum());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/counterparty/790cd2d0-2e92-11e9-ac12-000e0000005f",
                prepayment.getAgent().getMeta().getHref()
        );
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/organization/7906d621-2e92-11e9-ac12-000e0000005a",
                prepayment.getOrganization().getMeta().getHref()
        );
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/prepayment/7944ef04-f831-11e5-7a69-971500188b19/positions",
                prepayment.getPositions().getMeta().getHref()
        );
        assertTrue(prepayment.getVatEnabled());
        assertTrue(prepayment.getVatIncluded());
        assertEquals(Long.valueOf(5000), prepayment.getVatSum());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/retailstore/79749705-2e92-11e9-ac12-000e00000071",
                prepayment.getRetailStore().getMeta().getHref()
        );
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/customerorder/81ff1592-366b-11e9-ac12-000b00000069",
                prepayment.getCustomerOrder().getMeta().getHref()
        );
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/retailshift/49734306-366a-11e9-ac12-000b0000002a",
                prepayment.getRetailShift().getMeta().getHref()
        );
        assertEquals(Long.valueOf(15000), prepayment.getCashSum());
        assertEquals(Long.valueOf(15000), prepayment.getNoCashSum());
        assertEquals(Long.valueOf(0), prepayment.getQrSum());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException {
        ListEntity<Attribute> attributes = api.entity().prepayment().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().prepayment().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().prepayment().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().prepayment().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().prepayment().createMetadataAttribute(attribute);

        api.entity().prepayment().deleteMetadataAttribute(created);

        try {
            api.entity().prepayment().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Ignore // cant create prepayment without retailShift
    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }
}
