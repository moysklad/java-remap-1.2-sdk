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
        FactureInDocumentEntity factureIn = new FactureInDocumentEntity();
        factureIn.setName("facturein_" + randomString(3) + "_" + new Date().getTime());
        factureIn.setDescription(randomString());
        factureIn.setMoment(LocalDateTime.now());
        factureIn.setIncomingNumber(randomString());
        factureIn.setIncomingDate(LocalDateTime.now());
        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        supplies.add(simpleEntityFactory.createSimpleSupply());
        factureIn.setSupplies(supplies);

        api.entity().facturein().post(factureIn);

        ListEntity<FactureInDocumentEntity> updatedEntitiesList = api.entity().facturein().get(filterEq("name", factureIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        FactureInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(factureIn.getName(), retrievedEntity.getName());
        assertEquals(factureIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(factureIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(factureIn.getIncomingNumber(), retrievedEntity.getIncomingNumber());
        assertEquals(factureIn.getIncomingDate(), retrievedEntity.getIncomingDate());
        assertEquals(factureIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(factureIn.getSupplies().get(0).getMeta().getHref(), retrievedEntity.getSupplies().get(0).getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        FactureInDocumentEntity factureIn = simpleEntityFactory.createSimpleFactureIn();

        FactureInDocumentEntity retrievedEntity = api.entity().facturein().get(factureIn.getId());
        getAsserts(factureIn, retrievedEntity);

        retrievedEntity = api.entity().facturein().get(factureIn);
        getAsserts(factureIn, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        FactureInDocumentEntity factureIn = simpleEntityFactory.createSimpleFactureIn();

        FactureInDocumentEntity retrievedOriginalEntity = api.entity().facturein().get(factureIn.getId());
        String name = "facturein_" + randomString(3) + "_" + new Date().getTime();
        factureIn.setName(name);
        api.entity().facturein().put(factureIn.getId(), factureIn);
        putAsserts(factureIn, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(factureIn);

        name = "facturein_" + randomString(3) + "_" + new Date().getTime();
        factureIn.setName(name);
        api.entity().facturein().put(factureIn);
        putAsserts(factureIn, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        FactureInDocumentEntity factureIn = simpleEntityFactory.createSimpleFactureIn();

        ListEntity<FactureInDocumentEntity> entitiesList = api.entity().facturein().get(filterEq("name", factureIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().facturein().delete(factureIn.getId());

        entitiesList = api.entity().facturein().get(filterEq("name", factureIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        FactureInDocumentEntity factureIn = simpleEntityFactory.createSimpleFactureIn();

        ListEntity<FactureInDocumentEntity> entitiesList = api.entity().facturein().get(filterEq("name", factureIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().facturein().delete(factureIn);

        entitiesList = api.entity().facturein().get(filterEq("name", factureIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().facturein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newBySuppliesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = simpleEntityFactory.createSimpleSupply();

        FactureInDocumentEntity factureIn = api.entity().facturein().newDocument("supplies", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", factureIn.getName());
        assertEquals(supply.getSum(), factureIn.getSum());
        assertFalse(factureIn.getShared());
        assertTrue(factureIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, factureIn.getMoment()) < 1000);
        assertEquals(1, factureIn.getSupplies().size());
        assertEquals(supply.getMeta().getHref(), factureIn.getSupplies().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), factureIn.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), factureIn.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), factureIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPaymentsOutTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = simpleEntityFactory.createSimplePaymentOut();

        FactureInDocumentEntity factureIn = api.entity().facturein().newDocument("payments", Collections.singletonList(paymentOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", factureIn.getName());
        assertEquals(paymentOut.getSum(), factureIn.getSum());
        assertFalse(factureIn.getShared());
        assertTrue(factureIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, factureIn.getMoment()) < 1000);
        assertEquals(1, factureIn.getPayments().size());
        assertEquals(paymentOut.getMeta().getHref(), ((PaymentOutDocumentEntity) factureIn.getPayments().get(0)).getMeta().getHref());
        assertEquals(paymentOut.getGroup().getMeta().getHref(), factureIn.getGroup().getMeta().getHref());
        assertEquals(paymentOut.getAgent().getMeta().getHref(), factureIn.getAgent().getMeta().getHref());
        assertEquals(paymentOut.getOrganization().getMeta().getHref(), factureIn.getOrganization().getMeta().getHref());
    }

    private void getAsserts(FactureInDocumentEntity factureIn, FactureInDocumentEntity retrievedEntity) {
        assertEquals(factureIn.getName(), retrievedEntity.getName());
        assertEquals(factureIn.getIncomingNumber(), retrievedEntity.getIncomingNumber());
        assertEquals(factureIn.getIncomingDate(), retrievedEntity.getIncomingDate());
        assertEquals(factureIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(factureIn.getSupplies().get(0).getMeta().getHref(), retrievedEntity.getSupplies().get(0).getMeta().getHref());
    }

    private void putAsserts(FactureInDocumentEntity factureIn, FactureInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        FactureInDocumentEntity retrievedUpdatedEntity = api.entity().facturein().get(factureIn.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getIncomingNumber(), retrievedUpdatedEntity.getIncomingNumber());
        assertEquals(retrievedOriginalEntity.getIncomingDate(), retrievedUpdatedEntity.getIncomingDate());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getSupplies().get(0).getMeta().getHref(), retrievedUpdatedEntity.getSupplies().get(0).getMeta().getHref());
    }
}
