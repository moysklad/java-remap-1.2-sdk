package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.products.Bundle;
import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class BundleEntityTest extends EntityGetUpdateDeleteWithImageTest<Bundle> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Bundle bundle = new Bundle();
        bundle.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundle.setArchived(false);
        bundle.setArticle(randomString());

        Product product = simpleEntityManager.createSimple(Product.class);
        ListEntity<Bundle.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new Bundle.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        bundle.setComponents(components);

        api.entity().bundle().create(bundle);

        ListEntity<Bundle> updatedEntitiesList = api.entity().bundle().
                get(limit(20), expand("components.assortment"), filterEq("name", bundle.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Bundle retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(bundle.getName(), retrievedEntity.getName());
        assertEquals(bundle.getArchived(), retrievedEntity.getArchived());
        assertEquals(bundle.getArticle(), retrievedEntity.getArticle());
        assertEquals(bundle.getComponents().getMeta().getSize(), retrievedEntity.getComponents().getMeta().getSize());
        assertTrue(retrievedEntity.getComponents().getRows().get(0).getAssortment() instanceof Product);
        assertEquals(product.getName(), ((Product) retrievedEntity.getComponents().getRows().get(0).getAssortment()).getName());
        DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(4);
        assertEquals(df.format(components.getRows().get(0).getQuantity()), retrievedEntity.getComponents().getRows().get(0).getQuantity().toString());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Bundle originalBundle = (Bundle) originalEntity;
        Bundle updatedBundle = (Bundle) updatedEntity;

        assertNotEquals(originalBundle.getName(), updatedBundle.getName());
        assertEquals(changedField, updatedEntity.getName());
        assertEquals(originalBundle.getArticle(), updatedBundle.getArticle());
        assertEquals(originalBundle.getComponents().getMeta().getSize(), updatedBundle.getComponents().getMeta().getSize());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Bundle originalBundle = (Bundle) originalEntity;
        Bundle retrievedBundle = (Bundle) retrievedEntity;

        assertEquals(originalBundle.getName(), retrievedBundle.getName());
        assertEquals(originalBundle.getArticle(), retrievedBundle.getArticle());
        assertEquals(originalBundle.getComponents().getMeta().getSize(), retrievedBundle.getComponents().getMeta().getSize());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().bundle();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Bundle.class;
    }
}

