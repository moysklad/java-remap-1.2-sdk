package com.lognex.api.entities;

import com.lognex.api.entities.products.BundleEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class BundleEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        BundleEntity e = new BundleEntity();
        e.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setArticle(randomString());

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        ListEntity<BundleEntity.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new BundleEntity.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        e.setComponents(components);

        api.entity().bundle().post(e);

        ListEntity<BundleEntity> updatedEntitiesList = api.entity().bundle().
                get(limit(20), expand("components.assortment"), filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        BundleEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getArticle(), retrievedEntity.getArticle());
        assertEquals(e.getComponents().getMeta().getSize(), retrievedEntity.getComponents().getMeta().getSize());
        assertTrue(retrievedEntity.getComponents().getRows().get(0).getAssortment() instanceof ProductEntity);
        assertEquals(product.getName(), ((ProductEntity) retrievedEntity.getComponents().getRows().get(0).getAssortment()).getName());
        DecimalFormat df = new DecimalFormat("#.####");
        df.setDecimalSeparatorAlwaysShown(true);
        df.setMinimumFractionDigits(1);
        assertEquals(df.format(components.getRows().get(0).getQuantity()), retrievedEntity.getComponents().getRows().get(0).getQuantity().toString());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        BundleEntity e = createSimpleBundle();

        BundleEntity retrievedEntity = api.entity().bundle().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().bundle().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        BundleEntity e = createSimpleBundle();

        BundleEntity retrievedOriginalEntity = api.entity().bundle().get(e.getId());
        String name = "bundle_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        e.setComponents(null);
        api.entity().bundle().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        e = createSimpleBundle();
        retrievedOriginalEntity = api.entity().bundle().get(e.getId());
        name = "bundle_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        e.setComponents(null);
        api.entity().bundle().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        BundleEntity e = createSimpleBundle();

        ListEntity<BundleEntity> entitiesList = api.entity().bundle().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().bundle().delete(e.getId());

        entitiesList = api.entity().bundle().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        BundleEntity e = createSimpleBundle();

        ListEntity<BundleEntity> entitiesList = api.entity().bundle().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().bundle().delete(e);

        entitiesList = api.entity().bundle().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    private BundleEntity createSimpleBundle() throws IOException, LognexApiException {
        BundleEntity e = new BundleEntity();
        e.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        e.setArticle(randomString());

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        ListEntity<BundleEntity.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new BundleEntity.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        e.setComponents(components);

        api.entity().bundle().post(e);

        return e;
    }

    private void getAsserts(BundleEntity e, BundleEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArticle(), retrievedEntity.getArticle());
        assertEquals(e.getComponents().getMeta().getSize(), retrievedEntity.getComponents().getMeta().getSize());
    }

    private void putAsserts(BundleEntity e, BundleEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        BundleEntity retrievedUpdatedEntity = api.entity().bundle().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getArticle(), retrievedUpdatedEntity.getArticle());
        assertEquals(retrievedOriginalEntity.getComponents().getMeta().getSize(), retrievedUpdatedEntity.getComponents().getMeta().getSize());
    }
}

