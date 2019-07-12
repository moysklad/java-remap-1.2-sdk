package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
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

public class BundleEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        BundleEntity bundle = new BundleEntity();
        bundle.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundle.setArchived(false);
        bundle.setArticle(randomString());

        ProductEntity product = simpleEntityManager.createSimpleProduct();
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

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        BundleEntity originalBundle = (BundleEntity) originalEntity;
        BundleEntity updatedBundle = (BundleEntity) updatedEntity;

        assertNotEquals(originalBundle.getName(), updatedBundle.getName());
        assertEquals(changedField, updatedEntity.getName());
        assertEquals(originalBundle.getArticle(), updatedBundle.getArticle());
        assertEquals(originalBundle.getComponents().getMeta().getSize(), updatedBundle.getComponents().getMeta().getSize());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        BundleEntity originalBundle = (BundleEntity) originalEntity;
        BundleEntity retrievedBundle = (BundleEntity) retrievedEntity;

        assertEquals(originalBundle.getName(), retrievedBundle.getName());
        assertEquals(originalBundle.getArticle(), retrievedBundle.getArticle());
        assertEquals(originalBundle.getComponents().getMeta().getSize(), retrievedBundle.getComponents().getMeta().getSize());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().bundle();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return BundleEntity.class;
    }
}

