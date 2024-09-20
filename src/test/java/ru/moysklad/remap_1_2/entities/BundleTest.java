package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.products.*;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.TestUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class BundleTest extends EntityGetUpdateDeleteWithImageTest<Bundle> implements FilesTest<Bundle> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Bundle bundle = new Bundle();
        bundle.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundle.setArchived(false);
        bundle.setArticle(randomString());
        bundle.setTrackingType(Bundle.TrackingType.OTP);
        bundle.setPaymentItemType(GoodPaymentItemType.COMPOUND_PAYMENT_ITEM);
        bundle.setTaxSystem(GoodTaxSystem.PRESUMPTIVE_TAX_SYSTEM);
        bundle.setPartialDisposal(true);

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
        assertEquals(bundle.getTrackingType(), retrievedEntity.getTrackingType());
        assertEquals(bundle.getPaymentItemType(), retrievedEntity.getPaymentItemType());
        assertEquals(bundle.getTaxSystem(), retrievedEntity.getTaxSystem());
        assertEquals(bundle.getPartialDisposal(), retrievedEntity.getPartialDisposal());
    }

    @Test
    public void createDiscountProhibitedBundle() throws IOException, ApiClientException {
        Bundle bundleDiscountProhibited = new Bundle();
        bundleDiscountProhibited.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundleDiscountProhibited.setArchived(false);
        bundleDiscountProhibited.setArticle(randomString());
        bundleDiscountProhibited.setTrackingType(Bundle.TrackingType.OTP);
        bundleDiscountProhibited.setPaymentItemType(GoodPaymentItemType.COMPOUND_PAYMENT_ITEM);
        bundleDiscountProhibited.setTaxSystem(GoodTaxSystem.PRESUMPTIVE_TAX_SYSTEM);
        bundleDiscountProhibited.setPartialDisposal(true);
        bundleDiscountProhibited.setDiscountProhibited(true);

        Product product = simpleEntityManager.createSimple(Product.class);
        ListEntity<Bundle.ComponentEntity> components = new ListEntity<>();
        components.setRows(new ArrayList<>());
        components.getRows().add(new Bundle.ComponentEntity());
        components.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components.getRows().get(0).setAssortment(product);
        bundleDiscountProhibited.setComponents(components);

        api.entity().bundle().create(bundleDiscountProhibited);

        ListEntity<Bundle> updatedEntitiesList = api.entity().bundle()
                .get(limit(20), expand("components.assortment"), filterEq("name", bundleDiscountProhibited.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());
        Bundle retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(bundleDiscountProhibited.getDiscountProhibited(), retrievedEntity.getDiscountProhibited());

        Bundle bundleDiscountNotProhibited = new Bundle();
        bundleDiscountNotProhibited.setName("bundle_" + randomString(3) + "_" + new Date().getTime());
        bundleDiscountNotProhibited.setArchived(false);
        bundleDiscountNotProhibited.setArticle(randomString());
        bundleDiscountNotProhibited.setTrackingType(Bundle.TrackingType.OTP);
        bundleDiscountNotProhibited.setPaymentItemType(GoodPaymentItemType.COMPOUND_PAYMENT_ITEM);
        bundleDiscountNotProhibited.setTaxSystem(GoodTaxSystem.PRESUMPTIVE_TAX_SYSTEM);
        bundleDiscountNotProhibited.setPartialDisposal(true);
        bundleDiscountNotProhibited.setDiscountProhibited(false);

        Product product2 = simpleEntityManager.createSimple(Product.class);
        ListEntity<Bundle.ComponentEntity> components2 = new ListEntity<>();
        components2.setRows(new ArrayList<>());
        components2.getRows().add(new Bundle.ComponentEntity());
        components2.getRows().get(0).setQuantity(randomDouble(1, 5, 2));
        components2.getRows().get(0).setAssortment(product2);
        bundleDiscountNotProhibited.setComponents(components2);

        api.entity().bundle().create(bundleDiscountNotProhibited);

        ListEntity<Bundle> updatedEntitiesList2 = api.entity().bundle().
                get(limit(20), expand("components.assortment"), filterEq("name", bundleDiscountNotProhibited.getName()));
        assertEquals(1, updatedEntitiesList2.getRows().size());
        Bundle retrievedEntity2 = updatedEntitiesList2.getRows().get(0);
        assertEquals(bundleDiscountNotProhibited.getDiscountProhibited(), retrievedEntity2.getDiscountProhibited());
    }

    @Test
    public void paymentItemTypeTest() {
        Bundle bundle = simpleEntityManager.createSimple(Bundle.class);

        Arrays.stream(GoodPaymentItemType.values()).forEach(goodPaymentItemType -> {
            bundle.setPaymentItemType(goodPaymentItemType);
            try {
                api.entity().bundle().update(bundle);
                assertEquals(goodPaymentItemType, bundle.getPaymentItemType());
            } catch (IOException | ApiClientException e) {
                fail();
            }
        });
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().bundle().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().bundle().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new AttributeEntity();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().bundle().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new AttributeEntity();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        AttributeEntity created = (AttributeEntity) api.entity().bundle().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        AttributeEntity updated = (AttributeEntity) api.entity().bundle().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        AttributeEntity attribute = new AttributeEntity();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        AttributeEntity created = (AttributeEntity) api.entity().bundle().createMetadataAttribute(attribute);

        api.entity().bundle().deleteMetadataAttribute(created);

        try {
            api.entity().bundle().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    @Ignore
    @Override
    public void massCreateDeleteTest() {
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

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().bundle();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Bundle.class;
    }
}

