package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.Pricelist.ColumnsItem;
import com.lognex.api.entities.documents.Pricelist.PricelistRow;
import com.lognex.api.entities.documents.Pricelist.PricelistRow.CellsItem;
import com.lognex.api.entities.products.Product;
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

public class PricelistTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        Pricelist priceList = new Pricelist();
        priceList.setName("pricelist_" + randomString(3) + "_" + new Date().getTime());
        priceList.setMoment(LocalDateTime.now());

        List<ColumnsItem> columns = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ColumnsItem item = new ColumnsItem();
            item.setName(randomString());
            item.setPercentageDiscount(randomInteger(1, 10000));
            columns.add(item);
        }
        priceList.setColumns(columns);

        List<Product> products = new ArrayList<>();

        Product product = simpleEntityManager.createSimple(Product.class);
        products.add(product);

        product = new Product();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        api.entity().product().create(product);
        products.add(product);

        ListEntity<PricelistRow> positions = new ListEntity<>();
        priceList.setPositions(positions);
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

        api.entity().pricelist().create(priceList);

        ListEntity<Pricelist> updatedEntitiesList = api.entity().pricelist().
                get(limit(50), filterEq("name", priceList.getName()), expand("positions"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Pricelist retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(priceList.getName(), retrievedEntity.getName());
        assertEquals(priceList.getMoment(), retrievedEntity.getMoment());
        Product retrievedProduct = (Product) retrievedEntity.getPositions().getRows().get(0).getAssortment();
        assertEquals(products.get(0).getMeta().getHref(), retrievedProduct.getMeta().getHref());
        assertEquals(positions.getRows().get(0).getCells().get(0).getColumn(), columns.get(0).getName());
        retrievedProduct = (Product) retrievedEntity.getPositions().getRows().get(1).getAssortment();
        assertEquals(products.get(1).getMeta().getHref(), retrievedProduct.getMeta().getHref());
        for (int i = 0; i < 5; i++) {
            assertEquals(positions.getRows().get(1).getCells().get(i).getColumn(), columns.get(i).getName());
        }
        assertEquals(priceList.getColumns(), retrievedEntity.getColumns());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().pricelist().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Pricelist originalPricelist = (Pricelist) originalEntity;
        Pricelist retrievedPricelist = (Pricelist) retrievedEntity;

        assertEquals(originalPricelist.getName(), retrievedPricelist.getName());
        assertEquals(originalPricelist.getPositions().getMeta().getSize(), retrievedPricelist.getPositions().getMeta().getSize());
        assertEquals(originalPricelist.getColumns(), retrievedPricelist.getColumns());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Pricelist originalPricelist = (Pricelist) originalEntity;
        Pricelist updatedPricelist = (Pricelist) updatedEntity;

        assertNotEquals(originalPricelist.getName(), updatedPricelist.getName());
        assertEquals(changedField, updatedPricelist.getName());
        assertEquals(originalPricelist.getPositions().getMeta().getSize(), updatedPricelist.getPositions().getMeta().getSize());
        assertEquals(originalPricelist.getColumns(), updatedPricelist.getColumns());
    }

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().pricelist();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Pricelist.class;
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(priceList.getId());

        PricelistRow position = new PricelistRow();

        Product product = new Product();
        product.setName(randomString());
        api.entity().product().create(product);

        position.setAssortment(product);
        List<CellsItem> cells = new ArrayList<>();
        CellsItem cellsItem = new CellsItem();
        cellsItem.setSum(randomLong(1, 10000));
        cellsItem.setColumn(priceList.getColumns().get(0).getName());
        cells.add(cellsItem);
        position.setCells(cells);

        api.entity().pricelist().postPosition(priceList.getId(), position);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(priceList.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getCells().equals(position.getCells())
                )
        );
    }

    @Test
    public void createPositionByEntityTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(priceList.getId());

        PricelistRow position = new PricelistRow();

        Product product = new Product();
        product.setName(randomString());
        api.entity().product().create(product);

        position.setAssortment(product);
        List<CellsItem> cells = new ArrayList<>();
        CellsItem cellsItem = new CellsItem();
        cellsItem.setSum(randomLong(1, 10000));
        cellsItem.setColumn(priceList.getColumns().get(0).getName());
        cells.add(cellsItem);
        position.setCells(cells);

        api.entity().pricelist().postPosition(priceList, position);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(priceList);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getCells().equals(position.getCells())
                )
        );
    }

    @Test
    public void createPositionsByIdTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(priceList.getId());

        List<PricelistRow> positions = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            PricelistRow position = new PricelistRow();

            Product product = new Product();
            product.setName(randomString());
            api.entity().product().create(product);
            products.add(product);

            position.setAssortment(product);
            List<CellsItem> cells = new ArrayList<>();
            CellsItem cellsItem = new CellsItem();
            cellsItem.setSum(randomLong(1, 10000));
            cellsItem.setColumn(priceList.getColumns().get(0).getName());
            cells.add(cellsItem);
            position.setCells(cells);

            positions.add(position);
        }

        api.entity().pricelist().postPositions(priceList.getId(), positions);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(priceList.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            Product product = products.get(i);
            PricelistRow position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getCells().equals(position.getCells())
                    )
            );
        }
    }

    @Test
    public void createPositionsByEntityTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);

        ListEntity<PricelistRow> originalPositions = api.entity().pricelist().getPositions(priceList.getId());

        List<PricelistRow> positions = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            PricelistRow position = new PricelistRow();

            Product product = new Product();
            product.setName(randomString());
            api.entity().product().create(product);
            products.add(product);

            position.setAssortment(product);
            List<CellsItem> cells = new ArrayList<>();
            CellsItem cellsItem = new CellsItem();
            cellsItem.setSum(randomLong(1, 10000));
            cellsItem.setColumn(priceList.getColumns().get(0).getName());
            cells.add(cellsItem);
            position.setCells(cells);

            positions.add(position);
        }

        api.entity().pricelist().postPositions(priceList, positions);
        ListEntity<PricelistRow> retrievedPositions = api.entity().pricelist().getPositions(priceList.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            Product product = products.get(i);
            PricelistRow position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getCells().equals(position.getCells())
                    )
            );
        }
    }

    @Test
    public void getPositionTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(priceList.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().pricelist().getPosition(priceList, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(priceList.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(priceList.getId(), p.getId(), p);

        putPositionAsserts(priceList, p, retrievedPosition, cellsItem);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(priceList.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(priceList, p.getId(), p);

        putPositionAsserts(priceList, p, retrievedPosition, cellsItem);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(priceList.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(priceList, p, p);

        putPositionAsserts(priceList, p, retrievedPosition, cellsItem);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        PricelistRow p = positions.get(0);
        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(priceList.getId(), p.getId());

        CellsItem cellsItem = new CellsItem();
        cellsItem.setColumn(p.getCells().get(0).getColumn());
        cellsItem.setSum(p.getCells().get(0).getSum() + randomLong(1, 100));
        p.getCells().set(0, cellsItem);
        api.entity().pricelist().putPosition(priceList, p);

        putPositionAsserts(priceList, p, retrievedPosition, cellsItem);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        ListEntity<PricelistRow> positionsBefore = api.entity().pricelist().getPositions(priceList);

        api.entity().pricelist().delete(priceList.getId(), positions.get(0).getId());

        ListEntity<PricelistRow> positionsAfter = api.entity().pricelist().getPositions(priceList);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((Product) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((Product) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        ListEntity<PricelistRow> positionsBefore = api.entity().pricelist().getPositions(priceList);

        api.entity().pricelist().delete(priceList, positions.get(0).getId());

        ListEntity<PricelistRow> positionsAfter = api.entity().pricelist().getPositions(priceList);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((Product) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((Product) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        ListEntity<PricelistRow> positionsBefore = api.entity().pricelist().getPositions(priceList);

        api.entity().pricelist().delete(priceList, positions.get(0));

        ListEntity<PricelistRow> positionsAfter = api.entity().pricelist().getPositions(priceList);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((Product) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((Product) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<PricelistRow> createSimplePositions(Pricelist priceList) throws IOException, LognexApiException {
        List<PricelistRow> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            PricelistRow position = new PricelistRow();

            Product product = new Product();
            product.setName(randomString());
            api.entity().product().create(product);

            position.setAssortment(product);
            List<CellsItem> cells = new ArrayList<>();
            CellsItem cellsItem = new CellsItem();
            cellsItem.setSum(randomLong(1, 10000));
            cellsItem.setColumn(priceList.getColumns().get(0).getName());
            cells.add(cellsItem);
            position.setCells(cells);

            positions.add(position);
        }

        return api.entity().pricelist().postPositions(priceList, positions);
    }

    private void getPositionAsserts(PricelistRow p, PricelistRow retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((Product) p.getAssortment()).getMeta().getHref(),
                ((Product) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getCells(), retrievedPosition.getCells());
    }

    private void putPositionAsserts(Pricelist priceList, PricelistRow p, PricelistRow retrievedOriginalPosition, CellsItem cellsItem) throws IOException, LognexApiException {
        PricelistRow retrievedUpdatedPosition = api.entity().pricelist().getPosition(priceList, p.getId());

        assertNotEquals(retrievedOriginalPosition.getCells(), retrievedUpdatedPosition.getCells());
        assertEquals(cellsItem, retrievedUpdatedPosition.getCells().get(0));
        assertEquals(((Product) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((Product) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
