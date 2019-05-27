package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InvoiceOutDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = new InvoiceOutDocumentEntity();
        e.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        e.setVatEnabled(true);
        e.setVatIncluded(true);
        e.setMoment(LocalDateTime.now());
        e.setSum(randomLong(10, 10000));

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().invoiceout().post(e);

        ListEntity<InvoiceOutDocumentEntity> updatedEntitiesList = api.entity().invoiceout().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        InvoiceOutDocumentEntity retrievedEntity = api.entity().invoiceout().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().invoiceout().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        InvoiceOutDocumentEntity retrievedOriginalEntity = api.entity().invoiceout().get(e.getId());
        String name = "invoiceout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().invoiceout().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "invoiceout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().invoiceout().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        ListEntity<InvoiceOutDocumentEntity> entitiesList = api.entity().invoiceout().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().invoiceout().delete(e.getId());

        entitiesList = api.entity().invoiceout().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        ListEntity<InvoiceOutDocumentEntity> entitiesList = api.entity().invoiceout().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().invoiceout().delete(e);

        entitiesList = api.entity().invoiceout().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().invoiceout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private InvoiceOutDocumentEntity createSimpleDocumentInvoiceOut() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = new InvoiceOutDocumentEntity();
        e.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().invoiceout().post(e);

        return e;
    }

    private void getAsserts(InvoiceOutDocumentEntity e, InvoiceOutDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(InvoiceOutDocumentEntity e, InvoiceOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        InvoiceOutDocumentEntity retrievedUpdatedEntity = api.entity().invoiceout().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().invoiceout().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().invoiceout().postPosition(e.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().invoiceout().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionByEntityTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().invoiceout().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().invoiceout().postPosition(e, position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().invoiceout().getPositions(e);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionsByIdTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().invoiceout().getPositions(e.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);
            products.add(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        api.entity().invoiceout().postPositions(e.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().invoiceout().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void createPositionsByEntityTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().invoiceout().getPositions(e.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);
            products.add(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        api.entity().invoiceout().postPositions(e, positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().invoiceout().getPositions(e);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void getPositionTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition retrievedPosition = api.entity().invoiceout().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().invoiceout().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().invoiceout().getPosition(e.getId(), p.getId());

        double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().invoiceout().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().invoiceout().getPosition(e.getId(), p.getId());

        double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().invoiceout().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().invoiceout().getPosition(e.getId(), p.getId());

        double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().invoiceout().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().invoiceout().getPosition(e.getId(), p.getId());

        Double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().invoiceout().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().invoiceout().getPositions(e);

        api.entity().invoiceout().delete(e.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().invoiceout().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().invoiceout().getPositions(e);

        api.entity().invoiceout().delete(e, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().invoiceout().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity e = createSimpleDocumentInvoiceOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().invoiceout().getPositions(e);

        api.entity().invoiceout().delete(e, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = api.entity().invoiceout().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(InvoiceOutDocumentEntity e) throws IOException, LognexApiException {
        List<DocumentPosition> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        return api.entity().invoiceout().postPositions(e, positions);
    }

    private void getPositionAsserts(DocumentPosition p, DocumentPosition retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(InvoiceOutDocumentEntity e, DocumentPosition p, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = api.entity().invoiceout().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
