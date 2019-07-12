package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
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

public class FactureInDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        FactureInDocumentEntity factureIn = new FactureInDocumentEntity();
        factureIn.setName("facturein_" + randomString(3) + "_" + new Date().getTime());
        factureIn.setDescription(randomString());
        factureIn.setMoment(LocalDateTime.now());
        factureIn.setIncomingNumber(randomString());
        factureIn.setIncomingDate(LocalDateTime.now());
        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        supplies.add(simpleEntityManager.createSimpleSupply());
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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().facturein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newBySuppliesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = simpleEntityManager.createSimpleSupply();

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
        PaymentOutDocumentEntity paymentOut = simpleEntityManager.createSimplePaymentOut();

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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        FactureInDocumentEntity originalFactureIn = (FactureInDocumentEntity) originalEntity;
        FactureInDocumentEntity retrievedFactureIn = (FactureInDocumentEntity) retrievedEntity;

        assertEquals(originalFactureIn.getName(), retrievedFactureIn.getName());
        assertEquals(originalFactureIn.getIncomingNumber(), retrievedFactureIn.getIncomingNumber());
        assertEquals(originalFactureIn.getIncomingDate(), retrievedFactureIn.getIncomingDate());
        assertEquals(originalFactureIn.getOrganization().getMeta().getHref(), retrievedFactureIn.getOrganization().getMeta().getHref());
        assertEquals(originalFactureIn.getSupplies().get(0).getMeta().getHref(), retrievedFactureIn.getSupplies().get(0).getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        FactureInDocumentEntity originalFactureIn = (FactureInDocumentEntity) originalEntity;
        FactureInDocumentEntity updatedFactureIn = (FactureInDocumentEntity) updatedEntity;

        assertNotEquals(originalFactureIn.getName(), updatedFactureIn.getName());
        assertEquals(changedField, updatedFactureIn.getName());
        assertEquals(originalFactureIn.getIncomingNumber(), updatedFactureIn.getIncomingNumber());
        assertEquals(originalFactureIn.getIncomingDate(), updatedFactureIn.getIncomingDate());
        assertEquals(originalFactureIn.getOrganization().getMeta().getHref(), updatedFactureIn.getOrganization().getMeta().getHref());
        assertEquals(originalFactureIn.getSupplies().get(0).getMeta().getHref(), updatedFactureIn.getSupplies().get(0).getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().facturein();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return FactureInDocumentEntity.class;
    }
}
