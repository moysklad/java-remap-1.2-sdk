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

        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        api.entity().product().post(product);
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
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
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
        PricelistDocumentEntity e = createSimpleDocumentPricelist();

        PricelistDocumentEntity retrievedEntity = api.entity().pricelist().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().pricelist().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PricelistDocumentEntity e = createSimpleDocumentPricelist();

        PricelistDocumentEntity retrievedOriginalEntity = api.entity().pricelist().get(e.getId());
        String name = "pricelist_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        //Хак для того, чтобы при методе put не было попытки удалить материалы/продукты (должно быть исправлено)
        e.setPositions(null);
        api.entity().pricelist().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "pricelist_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        e.setPositions(null);
        api.entity().pricelist().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimpleDocumentPricelist();

        ListEntity<PricelistDocumentEntity> entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().pricelist().delete(e.getId());

        entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PricelistDocumentEntity e = createSimpleDocumentPricelist();

        ListEntity<PricelistDocumentEntity> entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().pricelist().delete(e);

        entitiesList = api.entity().pricelist().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().pricelist().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private PricelistDocumentEntity createSimpleDocumentPricelist() throws IOException, LognexApiException {
        PricelistDocumentEntity e = new PricelistDocumentEntity();
        e.setName("pricelist_" + randomString(3) + "_" + new Date().getTime());

        List<ColumnsItem> columns = new ArrayList<>();
        ColumnsItem item = new ColumnsItem();
        item.setName(randomString());
        item.setPercentageDiscount(randomInteger(1, 10000));
        columns.add(item);
        e.setColumns(columns);

        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        api.entity().product().post(product);

        ListEntity<PricelistRow> positions = new ListEntity<>();
        positions.setRows(new ArrayList<>());
        positions.getRows().add(new PricelistRow());
        positions.getRows().get(0).setAssortment(product);
        positions.getRows().get(0).setCells(new ArrayList<>());
        positions.getRows().get(0).getCells().add(new CellsItem());
        positions.getRows().get(0).getCells().get(0).setColumn(columns.get(0).getName());
        positions.getRows().get(0).getCells().get(0).setSum(randomLong(1, 10000));
        e.setPositions(positions);

        api.entity().pricelist().post(e);

        return e;
    }

    private void getAsserts(PricelistDocumentEntity e, PricelistDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getPositions().getMeta().getSize(), retrievedEntity.getPositions().getMeta().getSize());
        assertEquals(e.getColumns(), retrievedEntity.getColumns());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(PricelistDocumentEntity e, PricelistDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PricelistDocumentEntity retrievedUpdatedEntity = api.entity().pricelist().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getPositions().getMeta().getSize(), retrievedUpdatedEntity.getPositions().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getColumns(), retrievedUpdatedEntity.getColumns());
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
