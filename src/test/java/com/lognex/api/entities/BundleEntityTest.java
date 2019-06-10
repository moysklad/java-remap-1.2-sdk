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
        BundleEntity bundle = new BundleEntity();
        bundle.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundle.setArchived(false);
        bundle.setArticle(randomString());

        ProductEntity product = simpleEntityFactory.createSimpleProduct();
        ListEntity<BundleEntity.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new BundleEntity.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        bundle.setComponents(components);

        api.entity().bundle().post(bundle);

        ListEntity<BundleEntity> updatedEntitiesList = api.entity().bundle().
                get(limit(20), expand("components.assortment"), filterEq("name", bundle.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        BundleEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(bundle.getName(), retrievedEntity.getName());
        assertEquals(bundle.getArchived(), retrievedEntity.getArchived());
        assertEquals(bundle.getArticle(), retrievedEntity.getArticle());
        assertEquals(bundle.getComponents().getMeta().getSize(), retrievedEntity.getComponents().getMeta().getSize());
        assertTrue(retrievedEntity.getComponents().getRows().get(0).getAssortment() instanceof ProductEntity);
        assertEquals(product.getName(), ((ProductEntity) retrievedEntity.getComponents().getRows().get(0).getAssortment()).getName());
        DecimalFormat df = new DecimalFormat("#.####");
        df.setDecimalSeparatorAlwaysShown(true);
        df.setMinimumFractionDigits(1);
        assertEquals(df.format(components.getRows().get(0).getQuantity()), retrievedEntity.getComponents().getRows().get(0).getQuantity().toString());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        BundleEntity bundle = simpleEntityFactory.createSimpleBundle();

        BundleEntity retrievedEntity = api.entity().bundle().get(bundle.getId());
        getAsserts(bundle, retrievedEntity);

        retrievedEntity = api.entity().bundle().get(bundle);
        getAsserts(bundle, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        BundleEntity bundle = simpleEntityFactory.createSimpleBundle();

        BundleEntity retrievedOriginalEntity = api.entity().bundle().get(bundle.getId());
        String name = "bundle_" + randomString(3) + "_" + new Date().getTime();
        bundle.setName(name);
        api.entity().bundle().put(bundle.getId(), bundle);
        putAsserts(bundle, retrievedOriginalEntity, name);

        bundle = simpleEntityFactory.createSimpleBundle();
        retrievedOriginalEntity = api.entity().bundle().get(bundle.getId());
        name = "bundle_" + randomString(3) + "_" + new Date().getTime();
        bundle.setName(name);
        api.entity().bundle().put(bundle);
        putAsserts(bundle, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        BundleEntity bundle = simpleEntityFactory.createSimpleBundle();

        ListEntity<BundleEntity> entitiesList = api.entity().bundle().get(filterEq("name", bundle.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().bundle().delete(bundle.getId());

        entitiesList = api.entity().bundle().get(filterEq("name", bundle.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        BundleEntity bundle = simpleEntityFactory.createSimpleBundle();

        ListEntity<BundleEntity> entitiesList = api.entity().bundle().get(filterEq("name", bundle.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().bundle().delete(bundle);

        entitiesList = api.entity().bundle().get(filterEq("name", bundle.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(BundleEntity bundle, BundleEntity retrievedEntity) {
        assertEquals(bundle.getName(), retrievedEntity.getName());
        assertEquals(bundle.getArticle(), retrievedEntity.getArticle());
        assertEquals(bundle.getComponents().getMeta().getSize(), retrievedEntity.getComponents().getMeta().getSize());
    }

    private void putAsserts(BundleEntity bundle, BundleEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        BundleEntity retrievedUpdatedEntity = api.entity().bundle().get(bundle.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getArticle(), retrievedUpdatedEntity.getArticle());
        assertEquals(retrievedOriginalEntity.getComponents().getMeta().getSize(), retrievedUpdatedEntity.getComponents().getMeta().getSize());
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return BundleEntity.class;
    }
}

