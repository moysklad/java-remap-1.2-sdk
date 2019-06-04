package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class FactureInDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = new FactureInDocumentEntity();
        e.setName("facturein_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());
        e.setIncomingNumber(randomString());
        e.setIncomingDate(LocalDateTime.now());
        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        supplies.add(createSimpleSupply());
        e.setSupplies(supplies);

        api.entity().facturein().post(e);

        ListEntity<FactureInDocumentEntity> updatedEntitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        FactureInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getIncomingNumber(), retrievedEntity.getIncomingNumber());
        assertEquals(e.getIncomingDate(), retrievedEntity.getIncomingDate());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getSupplies().get(0).getMeta().getHref(), retrievedEntity.getSupplies().get(0).getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = createSimpleFactureIn();

        FactureInDocumentEntity retrievedEntity = api.entity().facturein().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().facturein().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        FactureInDocumentEntity e = createSimpleFactureIn();

        FactureInDocumentEntity retrievedOriginalEntity = api.entity().facturein().get(e.getId());
        String name = "facturein_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().facturein().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "facturein_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().facturein().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = createSimpleFactureIn();

        ListEntity<FactureInDocumentEntity> entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().facturein().delete(e.getId());

        entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = createSimpleFactureIn();

        ListEntity<FactureInDocumentEntity> entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().facturein().delete(e);

        entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().facturein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newBySuppliesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = createSimpleSupply();

        FactureInDocumentEntity e = api.entity().facturein().newDocument("supplies", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(supply.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getSupplies().size());
        assertEquals(supply.getMeta().getHref(), e.getSupplies().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPaymentsOutTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = createSimplePaymentOut();

        FactureInDocumentEntity e = api.entity().facturein().newDocument("payments", Collections.singletonList(paymentOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(paymentOut.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getPayments().size());
        assertEquals(paymentOut.getMeta().getHref(), ((PaymentOutDocumentEntity) e.getPayments().get(0)).getMeta().getHref());
        assertEquals(paymentOut.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(paymentOut.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(paymentOut.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private void getAsserts(FactureInDocumentEntity e, FactureInDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getIncomingNumber(), retrievedEntity.getIncomingNumber());
        assertEquals(e.getIncomingDate(), retrievedEntity.getIncomingDate());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getSupplies().get(0).getMeta().getHref(), retrievedEntity.getSupplies().get(0).getMeta().getHref());
    }

    private void putAsserts(FactureInDocumentEntity e, FactureInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        FactureInDocumentEntity retrievedUpdatedEntity = api.entity().facturein().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getIncomingNumber(), retrievedUpdatedEntity.getIncomingNumber());
        assertEquals(retrievedOriginalEntity.getIncomingDate(), retrievedUpdatedEntity.getIncomingDate());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getSupplies().get(0).getMeta().getHref(), retrievedUpdatedEntity.getSupplies().get(0).getMeta().getHref());
    }
}
