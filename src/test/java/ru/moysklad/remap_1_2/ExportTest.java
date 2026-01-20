package ru.moysklad.remap_1_2;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ru.moysklad.remap_1_2.entities.ExportExtension;
import ru.moysklad.remap_1_2.entities.Template;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.TestConstants;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static ru.moysklad.remap_1_2.clients.endpoints.ExportEndpoint.PrintRequest.printRequest;
import static org.junit.Assert.*;
import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

public class ExportTest {
    private static final int PORT_NUMBER = 14286;
    @Mock
    public static WireMockRule wireMockServer = new WireMockRule(options().port(PORT_NUMBER), false);

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
    public void antiLimits() throws InterruptedException {
        wireMockServer.shutdown();
        Thread.sleep(1500); // Защита от лимитов
    }

    @Test
    public void test_exportSingle() throws IOException, ApiClientException {
        String docId = UUID.randomUUID().toString();

        ListEntity<Template> templates = api.entity().demand().metadata().embeddedtemplate();
        assertNotNull(templates);
        assertNotNull(templates.getRows());
        assertFalse(templates.getRows().isEmpty());

        File exportFileXls = new File("test_single.xls");

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/export/")
                .withRequestBody(containing("upd_new.xls"))
                .withRequestBody(containing("\"extension\":\"xls"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_export_single.json")
                ));

        mockedApi.entity().demand().export(docId, templates.getRows().get(0), exportFileXls);
        assertTrue(exportFileXls.exists());
        assertTrue(exportFileXls.length() > 0);

        File exportFilePdf = new File("test_single.pdf");

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/export/")
                .withRequestBody(containing("upd_new.xls"))
                .withRequestBody(containing("\"extension\":\"pdf"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_export_single_pdf.json")
                ));

        mockedApi.entity().demand().export(docId, templates.getRows().get(0), exportFilePdf);
        assertTrue(exportFilePdf.exists());
        assertTrue(exportFilePdf.length() > 0);

        File exportFileNoExt = new File("test_single_without_ext");

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/export/")
                .withRequestBody(containing("upd_new.xls"))
                .withRequestBody(containing("\"extension\":\"html"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_export_single_html.json")
                ));

        mockedApi.entity().demand().export(docId, templates.getRows().get(0), ExportExtension.html, exportFileNoExt);
        assertTrue(exportFileNoExt.exists());
        assertTrue(exportFileNoExt.length() > 0);

        File exportFileDiffExt = new File("test_single_with_different_ext.html");

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/export/")
                .withRequestBody(containing("upd_new.xls"))
                .withRequestBody(containing("\"extension\":\"ods"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_export_single_html.json")
                ));

        mockedApi.entity().demand().export(docId, templates.getRows().get(0), ExportExtension.ods, exportFileDiffExt);
        assertTrue(exportFileDiffExt.exists());
        assertTrue(exportFileDiffExt.length() > 0);
    }

    @Test
    public void test_exportMultiple() throws IOException, ApiClientException {
        String docId = UUID.randomUUID().toString();

        ListEntity<Template> templates = api.entity().demand().metadata().embeddedtemplate();
        assertNotNull(templates);
        assertNotNull(templates.getRows());
        assertFalse(templates.getRows().isEmpty());

        File exportFile = new File("test_complect.pdf");

        wireMockServer.stubFor(post(API_PATH + "/" + "entity/demand/" + docId + "/export/")
                .withRequestBody(containing("upd.xls"))
                .withRequestBody(containing("act.xls"))
                .withRequestBody(containing("\"count\":2"))
                .withRequestBody(containing("\"count\":1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("X-Lognex-Get-Content", "true")
                        .withBodyFile("file_export.json")
                ));
        mockedApi.entity().demand().export(
                docId,
                exportFile,
                printRequest(templates.getRows().get(0)),
                printRequest(templates.getRows().get(1), 1),
                printRequest(templates.getRows().get(2), 2)
        );
        assertTrue(exportFile.exists());
        assertTrue(exportFile.length() > 0);
    }
}
