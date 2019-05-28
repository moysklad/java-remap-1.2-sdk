package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InventoryDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = new InventoryDocumentEntity();
        e.setName("inventory_" + randomString(3) + "_" + new Date().getTime());
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().inventory().post(e);

        ListEntity<InventoryDocumentEntity> updatedEntitiesList = api.entity().inventory().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InventoryDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        InventoryDocumentEntity retrievedEntity = api.entity().inventory().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().inventory().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        InventoryDocumentEntity retrievedOriginalEntity = api.entity().inventory().get(e.getId());
        String name = "inventory_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().inventory().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "inventory_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().inventory().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        ListEntity<InventoryDocumentEntity> entitiesList = api.entity().inventory().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().inventory().delete(e.getId());

        entitiesList = api.entity().inventory().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        ListEntity<InventoryDocumentEntity> entitiesList = api.entity().inventory().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().inventory().delete(e);

        entitiesList = api.entity().inventory().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().inventory().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private InventoryDocumentEntity createSimpleDocumentInventory() throws IOException, LognexApiException {
        InventoryDocumentEntity e = new InventoryDocumentEntity();
        e.setName("inventory_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().inventory().post(e);

        return e;
    }

    private void getAsserts(InventoryDocumentEntity e, InventoryDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(InventoryDocumentEntity e, InventoryDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        InventoryDocumentEntity retrievedUpdatedEntity = api.entity().inventory().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        ListEntity<DocumentPosition> originalPositions = api.entity().inventory().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().inventory().postPosition(e.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().inventory().getPositions(e.getId());

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
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        ListEntity<DocumentPosition> originalPositions = api.entity().inventory().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().inventory().postPosition(e, position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().inventory().getPositions(e);

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
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        ListEntity<DocumentPosition> originalPositions = api.entity().inventory().getPositions(e.getId());

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

        api.entity().inventory().postPositions(e.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().inventory().getPositions(e.getId());

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
        InventoryDocumentEntity e = createSimpleDocumentInventory();

        ListEntity<DocumentPosition> originalPositions = api.entity().inventory().getPositions(e.getId());

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

        api.entity().inventory().postPositions(e, positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().inventory().getPositions(e);

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
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition retrievedPosition = api.entity().inventory().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().inventory().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().inventory().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().inventory().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().inventory().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().inventory().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().inventory().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().inventory().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().inventory().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().inventory().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().inventory().getPositions(e);

        api.entity().inventory().delete(e.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().inventory().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().inventory().getPositions(e);

        api.entity().inventory().delete(e, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().inventory().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        InventoryDocumentEntity e = createSimpleDocumentInventory();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().inventory().getPositions(e);

        api.entity().inventory().delete(e, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = api.entity().inventory().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(InventoryDocumentEntity e) throws IOException, LognexApiException {
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

        return api.entity().inventory().postPositions(e, positions);
    }

    private void getPositionAsserts(DocumentPosition p, DocumentPosition retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(InventoryDocumentEntity e, DocumentPosition p, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = api.entity().inventory().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
