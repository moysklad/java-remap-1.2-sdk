package ru.moysklad.remap_1_2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.moysklad.remap_1_2.entities.Publication;
import ru.moysklad.remap_1_2.entities.Store;
import ru.moysklad.remap_1_2.entities.Template;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.Demand;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;
import ru.moysklad.remap_1_2.utils.TestConstants;

import java.io.IOException;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.junit.Assert.*;
import static ru.moysklad.remap_1_2.ExportTest.wireMockServer;
import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

public class PublicationTest {
    private ApiClient api;
    private ApiClient mockedApi;

    @Before
    public void init() {
        api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );

        wireMockServer.start();

        mockedApi = new ApiClient(wireMockServer.baseUrl(),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );
    }

    @After
    public void afterTest() throws InterruptedException {
        wireMockServer.shutdown();
        Thread.sleep(1500);
    }

    @Test
    public void getPublicationTest() throws IOException, ApiClientException {
        String docId;
        {
            Organization ag = api.entity().organization().get().getRows().get(0);
            Store st = api.entity().store().get().getRows().get(0);

            Demand doc = new Demand();
            doc.setAgent(ag);
            doc.setOrganization(ag);
            doc.setStore(st);
            api.entity().demand().create(doc);

            docId = doc.getId();
        }

        ListEntity<Template> templates = api.entity().demand().metadata().embeddedtemplate();
        assertNotNull(templates);
        assertNotNull(templates.getRows());
        assertFalse(templates.getRows().isEmpty());

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/publication/")
                .withRequestBody(containing("upd_new.xls"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication.json")
                ));

        Publication publication = mockedApi.entity().demand().publish(docId, templates.getRows().get(0));
        assertEquals(MetaHrefUtils.getIdFromHref(templates.getRows().get(0).getMeta().getHref()).get(), publication.getHref());
        assertFalse(isEmpty(publication.getHref()));

        wireMockServer.stubFor(get(API_PATH + "/" + "entity/demand/" + docId + "/publication/" + publication.getHref())
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication.json")
                ));

        Publication retrievedPublication = mockedApi.entity().demand().getPublication(docId, publication.getHref());
        assertEquals(publication, retrievedPublication);
    }

    @Test
    public void getAndDeletePublicationsTest() throws IOException, ApiClientException {
        String docId;
        {
            Organization ag = api.entity().organization().get().getRows().get(0);
            Store st = api.entity().store().get().getRows().get(0);

            Demand doc = new Demand();
            doc.setAgent(ag);
            doc.setOrganization(ag);
            doc.setStore(st);
            api.entity().demand().create(doc);

            docId = doc.getId();
        }

        ListEntity<Template> templates = api.entity().demand().metadata().embeddedtemplate();
        assertNotNull(templates);
        assertNotNull(templates.getRows());
        assertFalse(templates.getRows().isEmpty());

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/publication/")
                .withRequestBody(containing("upd_new.xls"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication.json")
                ));

        mockedApi.entity().demand().publish(docId, templates.getRows().get(0));

        wireMockServer.stubFor(get(API_PATH + "/" + "entity/demand/" + docId + "/publication/")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication_1.json")
                ));

        ListEntity<Publication> publications = mockedApi.entity().demand().getPublications(docId);
        assertEquals(Integer.valueOf(1), publications.getMeta().getSize());

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/publication/")
                .withRequestBody(containing("upd.xls"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication.json")
                ));
        mockedApi.entity().demand().publish(docId, templates.getRows().get(1));

        wireMockServer.stubFor(get(API_PATH + "/" + "entity/demand/" + docId + "/publication/")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication_another.json")
                ));

        publications = mockedApi.entity().demand().getPublications(docId);
        assertEquals(Integer.valueOf(2), publications.getMeta().getSize());

        String publicationToDelete = publications.getMeta().getHref();
        Optional<String> publicationId = MetaHrefUtils.getIdFromHref(publicationToDelete);
        assertTrue(publicationId.isPresent());

        wireMockServer.stubFor(delete(API_PATH + "/" + "entity/demand/" + docId + "/publication/" + publicationId.get())
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                ));

        mockedApi.entity().demand().delelePublication(docId, publicationId.get());
        wireMockServer.stubFor(get(API_PATH + "/" + "entity/demand/" + docId + "/publication/")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_publication_1.json")
                ));
        publications = mockedApi.entity().demand().getPublications(docId);
        assertEquals(Integer.valueOf(1), publications.getMeta().getSize());
        assertNotEquals(publicationToDelete, publications.getMeta().getHref());
    }
}
