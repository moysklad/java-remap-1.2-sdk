package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.positions.LossDocumentPosition;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class LossTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Loss loss = new Loss();
        loss.setName("loss_" + randomString(3) + "_" + new Date().getTime());
        loss.setDescription(randomString());
        loss.setMoment(LocalDateTime.now());
        loss.setOrganization(simpleEntityManager.getOwnOrganization());
        loss.setStore(simpleEntityManager.getMainStore());

        LossDocumentPosition position = new LossDocumentPosition();
        position.setAssortment(simpleEntityManager.createSimpleProduct());
        position.setReason(randomString());
        position.setQuantity(1.);
        loss.setPositions(new ListEntity<>(position));

        api.entity().loss().create(loss);

        ListEntity<Loss> updatedEntitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Loss retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(loss.getName(), retrievedEntity.getName());
        assertEquals(loss.getDescription(), retrievedEntity.getDescription());
        assertEquals(loss.getMoment(), retrievedEntity.getMoment());
        assertEquals(loss.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());

        ListEntity<LossDocumentPosition> retrievedPositions = api.entity().loss().getPositions(retrievedEntity.getId());
        assertEquals(1, retrievedPositions.getRows().size());
        assertEquals(position.getReason(), retrievedPositions.getRows().get(0).getReason());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse response = api.entity().loss().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().loss().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        Loss loss = api.entity().loss().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", loss.getName());
        assertEquals(Long.valueOf(0), loss.getSum());
        assertFalse(loss.getShared());
        assertTrue(loss.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, loss.getMoment()) < 1000);

        assertEquals(loss.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(loss.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = simpleEntityManager.createSimple(SalesReturn.class);

        Loss loss = api.entity().loss().templateDocument("salesReturn", salesReturn);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", loss.getName());
        assertEquals(salesReturn.getSum(), loss.getSum());
        assertFalse(loss.getShared());
        assertTrue(loss.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, loss.getMoment()) < 1000);
        assertEquals(salesReturn.getMeta().getHref(), loss.getSalesReturn().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), loss.getStore().getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), loss.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), loss.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Loss originalLoss = (Loss) originalEntity;
        Loss retrievedLoss = (Loss) retrievedEntity;

        assertEquals(originalLoss.getName(), retrievedLoss.getName());
        assertEquals(originalLoss.getDescription(), retrievedLoss.getDescription());
        assertEquals(originalLoss.getOrganization().getMeta().getHref(), retrievedLoss.getOrganization().getMeta().getHref());
        assertEquals(originalLoss.getStore().getMeta().getHref(), retrievedLoss.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Loss originalLoss = (Loss) originalEntity;
        Loss updatedLoss = (Loss) updatedEntity;

        assertNotEquals(originalLoss.getName(), updatedLoss.getName());
        assertEquals(changedField, updatedLoss.getName());
        assertEquals(originalLoss.getDescription(), updatedLoss.getDescription());
        assertEquals(originalLoss.getOrganization().getMeta().getHref(), updatedLoss.getOrganization().getMeta().getHref());
        assertEquals(originalLoss.getStore().getMeta().getHref(), updatedLoss.getStore().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().loss();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Loss.class;
    }
}
