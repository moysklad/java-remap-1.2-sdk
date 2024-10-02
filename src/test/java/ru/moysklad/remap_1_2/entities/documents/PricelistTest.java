package ru.moysklad.remap_1_2.entities.documents;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.documents.Pricelist.ColumnsItem;
import ru.moysklad.remap_1_2.entities.documents.Pricelist.PricelistRow;
import ru.moysklad.remap_1_2.entities.documents.Pricelist.PricelistRow.CellsItem;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class PricelistTest extends EntityGetUpdateDeleteTest implements FilesTest<Pricelist> {
    @Test
    public void createTest() throws IOException, ApiClientException {
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
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().pricelist().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().pricelist().states().create(state);

        List<State> retrievedStates = api.entity().pricelist().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
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
    public EntityClientBase entityClient() {
        return api.entity().pricelist();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Pricelist.class;
    }

    @Test
    public void createPositionByIdTest() throws IOException, ApiClientException {
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
    public void createPositionByEntityTest() throws IOException, ApiClientException {
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
    public void createPositionsByIdTest() throws IOException, ApiClientException {
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
    public void createPositionsByEntityTest() throws IOException, ApiClientException {
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
    public void getPositionTest() throws IOException, ApiClientException {
        Pricelist priceList = simpleEntityManager.createSimple(Pricelist.class);
        List<PricelistRow> positions = createSimplePositions(priceList);

        PricelistRow retrievedPosition = api.entity().pricelist().getPosition(priceList.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().pricelist().getPosition(priceList, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, ApiClientException {
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
    public void putPositionByEntityIdTest() throws IOException, ApiClientException {
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
    public void putPositionByEntitiesTest() throws IOException, ApiClientException {
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
    public void putPositionBySelfTest() throws IOException, ApiClientException {
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
    public void deletePositionByIdsTest() throws IOException, ApiClientException {
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
    public void deletePositionByEntityIdTest() throws IOException, ApiClientException {
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
    public void deletePositionByEntitiesTest() throws IOException, ApiClientException {
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

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().pricelist().metadataAttributes();
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
        DocumentAttribute created = api.entity().pricelist().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().pricelist().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().pricelist().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().pricelist().createMetadataAttribute(attribute);

        api.entity().pricelist().deleteMetadataAttribute(created);

        try {
            api.entity().pricelist().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }
    
    private List<PricelistRow> createSimplePositions(Pricelist priceList) throws IOException, ApiClientException {
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

    private void putPositionAsserts(Pricelist priceList, PricelistRow p, PricelistRow retrievedOriginalPosition, CellsItem cellsItem) throws IOException, ApiClientException {
        PricelistRow retrievedUpdatedPosition = api.entity().pricelist().getPosition(priceList, p.getId());

        assertNotEquals(retrievedOriginalPosition.getCells(), retrievedUpdatedPosition.getCells());
        assertEquals(cellsItem, retrievedUpdatedPosition.getCells().get(0));
        assertEquals(((Product) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((Product) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
