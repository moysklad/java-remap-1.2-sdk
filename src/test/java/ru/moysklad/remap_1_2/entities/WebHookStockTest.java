package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;

public class WebHookStockTest extends EntityGetDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        WebHookStock webHookStock = new WebHookStock();
        webHookStock.setUrl(randomUrl());
        webHookStock.setMethod(WebHookStock.HttpMethod.POST);
        webHookStock.setStockType(WebHookStock.StockType.STOCK);
        webHookStock.setEnabled(false);
        webHookStock.setReportType(WebHookStock.ReportType.ALL);

        api.entity().webhookStock().create(webHookStock);

        ListEntity<WebHookStock> updatedEntitiesList = api.entity().webhookStock().get(filterEq("id", webHookStock.getId()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        WebHookStock retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(webHookStock.getStockType().name(), retrievedEntity.getStockType());
        assertEquals(webHookStock.getReportType(), retrievedEntity.getReportType());
        assertEquals(webHookStock.getMethod(), retrievedEntity.getMethod());
        assertEquals(webHookStock.getUrl(), retrievedEntity.getUrl());
        assertEquals(webHookStock.getEnabled(), retrievedEntity.getEnabled());
    }

    @Override
    public void deleteTest() throws IOException, ApiClientException {
        doDeleteTest("Id");
        doDeleteByIdTest("Id");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void putTest() throws IOException, ApiClientException {
        WebHookStock webHookStock = (WebHookStock) simpleEntityManager.createSimple(entityClass());

        // update by entity
        WebHookStock originalWebHookStok = ((GetByIdEndpoint<WebHookStock>) entityClient()).get(webHookStock.getId());
        String changedUrl = randomUrl();
        webHookStock.setUrl(changedUrl);
        ((PutByIdEndpoint) entityClient()).update(webHookStock);
        WebHookStock retrievedWebHookStock = ((GetByIdEndpoint<WebHookStock>) entityClient()).get(webHookStock.getId());
        putAsserts(originalWebHookStok, retrievedWebHookStock, changedUrl);

        // update by id and entity
        originalWebHookStok.set(retrievedWebHookStock);
        changedUrl = randomUrl();
        webHookStock.setUrl(changedUrl);
        ((PutByIdEndpoint) entityClient()).update(webHookStock.getId(), webHookStock);
        retrievedWebHookStock = ((GetByIdEndpoint<WebHookStock>) entityClient()).get(webHookStock.getId());
        putAsserts(originalWebHookStok, retrievedWebHookStock, changedUrl);
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        WebHookStock originalWebHookStock = (WebHookStock) originalEntity;
        WebHookStock retrievedWebHookStock = (WebHookStock) retrievedEntity;

        assertEquals(originalWebHookStock.getStockType().name(), retrievedWebHookStock.getStockType());
        assertEquals(originalWebHookStock.getMethod(), retrievedWebHookStock.getMethod());
        assertEquals(originalWebHookStock.getReportType(), retrievedWebHookStock.getReportType());
        assertEquals(originalWebHookStock.getUrl(), retrievedWebHookStock.getUrl());
        assertEquals(originalWebHookStock.getEnabled(), retrievedWebHookStock.getEnabled());
    }

    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, String changedUrl) {
        WebHookStock originalWebHookStock = (WebHookStock) originalEntity;
        WebHookStock updatedWebHookStock = (WebHookStock) updatedEntity;

        assertNotEquals(originalWebHookStock.getUrl(), updatedWebHookStock.getUrl());
        assertEquals(changedUrl, updatedWebHookStock.getUrl());
        assertEquals(originalWebHookStock.getStockType(), updatedWebHookStock.getStockType());
        assertEquals(originalWebHookStock.getMethod(), updatedWebHookStock.getMethod());
        assertEquals(originalWebHookStock.getReportType(), updatedWebHookStock.getReportType());
        assertEquals(originalWebHookStock.getEnabled(), updatedWebHookStock.getEnabled());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().webhookStock();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return WebHookStock.class;
    }
}
