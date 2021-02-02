package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class WebHookTest extends EntityGetDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        WebHook webHook = new WebHook();
        webHook.setUrl(randomUrl());
        webHook.setMethod(WebHook.HttpMethod.POST);
        webHook.setAction(WebHook.EntityAction.CREATE);
        webHook.setEnabled(false);
        webHook.setEntityType(randomWebhookType());
        
        api.entity().webhook().create(webHook);

        ListEntity<WebHook> updatedEntitiesList = api.entity().webhook().get(filterEq("id", webHook.getId()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        WebHook retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(webHook.getEntityType(), retrievedEntity.getEntityType());
        assertEquals(webHook.getAction(), retrievedEntity.getAction());
        assertEquals(webHook.getMethod(), retrievedEntity.getMethod());
        assertEquals(webHook.getUrl(), retrievedEntity.getUrl());
        assertEquals(webHook.getEnabled(), retrievedEntity.getEnabled());
    }

    @Override
    public void deleteTest() throws IOException, ApiClientException {
        doDeleteTest("Id");
        doDeleteByIdTest("Id");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void putTest() throws IOException, ApiClientException {
        WebHook webHook = (WebHook) simpleEntityManager.createSimple(entityClass());

        // update by entity
        WebHook originalWebHook = ((GetByIdEndpoint<WebHook>) entityClient()).get(webHook.getId());
        String changedUrl = randomUrl();
        webHook.setUrl(changedUrl);
        ((PutByIdEndpoint) entityClient()).update(webHook);
        WebHook retrievedWebHook = ((GetByIdEndpoint<WebHook>) entityClient()).get(webHook.getId());
        putAsserts(originalWebHook, retrievedWebHook, changedUrl);

        // update by id and entity
        originalWebHook.set(retrievedWebHook);
        changedUrl = randomUrl();
        webHook.setUrl(changedUrl);
        ((PutByIdEndpoint) entityClient()).update(webHook.getId(), webHook);
        retrievedWebHook = ((GetByIdEndpoint<WebHook>) entityClient()).get(webHook.getId());
        putAsserts(originalWebHook, retrievedWebHook, changedUrl);
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        WebHook originalWebHook = (WebHook) originalEntity;
        WebHook retrievedWebHook = (WebHook) retrievedEntity;

        assertEquals(originalWebHook.getEntityType(), retrievedWebHook.getEntityType());
        assertEquals(originalWebHook.getMethod(), retrievedWebHook.getMethod());
        assertEquals(originalWebHook.getAction(), retrievedWebHook.getAction());
        assertEquals(originalWebHook.getUrl(), retrievedWebHook.getUrl());
        assertEquals(originalWebHook.getEnabled(), retrievedWebHook.getEnabled());
    }

    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, String changedUrl) {
        WebHook originalWebHook = (WebHook) originalEntity;
        WebHook updatedWebHook = (WebHook) updatedEntity;

        assertNotEquals(originalWebHook.getUrl(), updatedWebHook.getUrl());
        assertEquals(changedUrl, updatedWebHook.getUrl());
        assertEquals(originalWebHook.getEntityType(), updatedWebHook.getEntityType());
        assertEquals(originalWebHook.getMethod(), updatedWebHook.getMethod());
        assertEquals(originalWebHook.getAction(), updatedWebHook.getAction());
        assertEquals(originalWebHook.getEnabled(), updatedWebHook.getEnabled());    
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().webhook();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return WebHook.class;
    }
}
