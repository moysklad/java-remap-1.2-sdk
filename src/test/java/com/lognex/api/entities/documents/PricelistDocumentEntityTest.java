package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.documents.PricelistDocumentEntity.ColumnsItem;
import com.lognex.api.entities.documents.PricelistDocumentEntity.PricelistRow;
import com.lognex.api.entities.documents.PricelistDocumentEntity.PricelistRow.CellsItem;
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

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class PricelistDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = new PricelistDocumentEntity();
        e.setName("pricelist_" + randomString(3) + "_" + new Date().getTime());
        e.setMoment(LocalDateTime.now());

        List<ColumnsItem> columns = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ColumnsItem item = new ColumnsItem();
            item.setName(randomString());
            item.setPercentageDiscount(randomInteger(1, 10000));
            columns.add(item);
        }
        e.setColumns(columns);

        List<ProductEntity> products = new ArrayList<>();

        ProductEntity product = createSimpleProduct();
        products.add(product);

        product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        api.entity().product().post(product);
        products.add(product);

        ListEntity<PricelistRow> positions = new ListEntity<>();
        e.setPositions(positions);
        positions.setRows(new ArrayList<>());
        positions.getRows().add(new PricelistRow());
        positions.getRows().get(0).setAssortment(products.get(0));
        positions.getRows().get(0).setCells(new ArrayList<>());
        positions.getRows().get(0).getCells().add(new CellsItem());
        positions.getRows().get(0).getCells().get(0).setColumn(columns.get(0).getName());
        positions.getRows().get(0).getCells().get(0).setSum(randomLong(1, 10000));

        positions.getRows().add(new PricelistRow());
        positions.getRows().get(1).setAssortment(products.get(1));
        positions.getRows().get(1).setCells(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            positions.getRows().get(1).getCells().add(new CellsItem());
            positions.getRows().get(1).getCells().get(i).setColumn(columns.get(i).getName());
            positions.getRows().get(1).getCells().get(i).setSum(randomLong(1, 10000));
        }

        api.entity().pricelist().post(e);

        ListEntity<PricelistDocumentEntity> updatedEntitiesList = api.entity().pricelist().
                get(limit(50), filterEq("name", e.getName()), expand("positions"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PricelistDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getPositions().getRows().get(0).getAssortment();
        assertEquals(products.get(0).getMeta().getHref(), retrievedProduct.getMeta().getHref());
        assertEquals(positions.getRows().get(0).getCells().get(0).getColumn(), columns.get(0).getName());
        retrievedProduct = (ProductEntity) retrievedEntity.getPositions().getRows().get(1).getAssortment();
        assertEquals(products.get(1).getMeta().getHref(), retrievedProduct.getMeta().getHref());
        for (int i = 0; i < 5; i++) {
            assertEquals(positions.getRows().get(1).getCells().get(i).getColumn(), columns.get(i).getName());
        }
        assertEquals(e.getColumns(), retrievedEntity.getColumns());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        PricelistDocumentEntity retrievedEntity = api.entity().pricelist().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().pricelist().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PricelistDocumentEntity e = createSimplePricelist();

        PricelistDocumentEntity retrievedOriginalEntity = api.entity().pricelist().get(e.getId());
        String name = "pricelist_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().pricelist().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "pricelist_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().pricelist().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        ListEntity<PricelistDocumentEntity> entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().pricelist().delete(e.getId());

        entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        ListEntity<PricelistDocumentEntity> entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().pricelist().delete(e);

        entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().pricelist().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(PricelistDocumentEntity e, PricelistDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getPositions().getMeta().getSize(), retrievedEntity.getPositions().getMeta().getSize());
        assertEquals(e.getColumns(), retrievedEntity.getColumns());
    }

    private void putAsserts(PricelistDocumentEntity e, PricelistDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PricelistDocumentEntity retrievedUpdatedEntity = api.entity().pricelist().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getPositions().getMeta().getSize(), retrievedUpdatedEntity.getPositions().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getColumns(), retrievedUpdatedEntity.getColumns());
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(e.getId());

        PricelistRow position = new PricelistRow();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        List<CellsItem> cells = new ArrayList<>();
        CellsItem cellsItem = new CellsItem();
        cellsItem.setSum(randomLong(1, 10000));
        cellsItem.setColumn(e.getColumns().get(0).getName());
        cells.add(cellsItem);
        position.setCells(cells);

        api.entity().pricelist().postPosition(e.getId(), position);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getCells().equals(position.getCells())
                )
        );
    }

    @Test
    public void createPositionByEntityTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(e.getId());

        PricelistRow position = new PricelistRow();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        List<CellsItem> cells = new ArrayList<>();
        CellsItem cellsItem = new CellsItem();
        cellsItem.setSum(randomLong(1, 10000));
        cellsItem.setColumn(e.getColumns().get(0).getName());
        cells.add(cellsItem);
        position.setCells(cells);

        api.entity().pricelist().postPosition(e, position);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(e);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getCells().equals(position.getCells())
                )
        );
    }

    @Test
    public void createPositionsByIdTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(e.getId());

        List<PricelistRow> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            PricelistRow position = new PricelistRow();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);
            products.add(product);

            position.setAssortment(product);
            List<CellsItem> cells = new ArrayList<>();
            CellsItem cellsItem = new CellsItem();
            cellsItem.setSum(randomLong(1, 10000));
            cellsItem.setColumn(e.getColumns().get(0).getName());
            cells.add(cellsItem);
            position.setCells(cells);

            positions.add(position);
        }

        api.entity().pricelist().postPositions(e.getId(), positions);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            PricelistRow position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getCells().equals(position.getCells())
                    )
            );
        }
    }

    @Test
    public void createPositionsByEntityTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(e.getId());

        List<PricelistRow> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            PricelistRow position = new PricelistRow();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);
            products.add(product);

            position.setAssortment(product);
            List<CellsItem> cells = new ArrayList<>();
            CellsItem cellsItem = new CellsItem();
            cellsItem.setSum(randomLong(1, 10000));
            cellsItem.setColumn(e.getColumns().get(0).getName());
            cells.add(cellsItem);
            position.setCells(cells);

            positions.add(position);
        }

        api.entity().pricelist().postPositions(e, positions);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            PricelistRow position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getCells().equals(position.getCells())
                    )
            );
        }
    }

    @Test
    public void getPositionTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().pricelist().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(e.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, cellsItem);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(e.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, cellsItem);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(e.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, cellsItem);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(e.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, cellsItem);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        ListEntity<PricelistRow> positionsBefore = api.entity().pricelist().getPositions(e);

        api.entity().pricelist().delete(e.getId(), positions.get(0).getId());

        ListEntity<PricelistRow> positionsAfter = api.entity().pricelist().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        ListEntity<PricelistRow> positionsBefore = api.entity().pricelist().getPositions(e);

        api.entity().pricelist().delete(e, positions.get(0).getId());

        ListEntity<PricelistRow> positionsAfter = api.entity().pricelist().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimplePricelist();
        List<PricelistRow> positions = createSimplePositions(e);

        ListEntity<PricelistRow> positionsBefore = api.entity().pricelist().getPositions(e);

        api.entity().pricelist().delete(e, positions.get(0));

        ListEntity<PricelistRow> positionsAfter = api.entity().pricelist().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<PricelistRow> createSimplePositions(PricelistDocumentEntity e) throws IOException, LognexApiException {
        List<PricelistRow> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            PricelistRow position = new PricelistRow();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);

            position.setAssortment(product);
            List<CellsItem> cells = new ArrayList<>();
            CellsItem cellsItem = new CellsItem();
            cellsItem.setSum(randomLong(1, 10000));
            cellsItem.setColumn(e.getColumns().get(0).getName());
            cells.add(cellsItem);
            position.setCells(cells);

            positions.add(position);
        }

        return api.entity().pricelist().postPositions(e, positions);
    }

    private void getPositionAsserts(PricelistRow p, PricelistRow retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getCells(), retrievedPosition.getCells());
    }

    private void putPositionAsserts(PricelistDocumentEntity e, PricelistRow p, PricelistRow retrievedOriginalPosition, CellsItem cellsItem) throws IOException, LognexApiException {
        PricelistRow retrievedUpdatedPosition = api.entity().pricelist().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getCells(), retrievedUpdatedPosition.getCells());
        assertEquals(cellsItem, retrievedUpdatedPosition.getCells().get(0));
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
