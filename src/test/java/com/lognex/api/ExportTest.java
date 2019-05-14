package com.lognex.api;

import com.lognex.api.entities.ExportExtension;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.TemplateEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.documents.DemandDocumentEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static com.lognex.api.clients.endpoints.ExportEndpoint.PrintRequest.printRequest;
import static org.junit.Assert.*;

public class ExportTest {
    private LognexApi api;

    @Before
    public void init() {
        api = new LognexApi(
                System.getenv("API_HOST"),
                false, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );
    }

    @After
    public void antiLimits() throws InterruptedException {
        Thread.sleep(1500); // Защита от лимитов
    }

    @Test
    public void test_exportSingle() throws IOException, LognexApiException {
        String docId;
        {
            OrganizationEntity ag = api.entity().organization().get().getRows().get(0);
            StoreEntity st = api.entity().store().get().getRows().get(0);

            DemandDocumentEntity doc = new DemandDocumentEntity();
            doc.setAgent(ag);
            doc.setOrganization(ag);
            doc.setStore(st);
            api.entity().demand().post(doc);

            docId = doc.getId();
        }

        ListEntity<TemplateEntity> templates = api.entity().demand().metadata().embeddedtemplate();
        assertNotNull(templates);
        assertNotNull(templates.getRows());
        assertFalse(templates.getRows().isEmpty());

        File exportFileXls = new File("test_single.xls");
        api.entity().demand(docId).export(templates.getRows().get(0), exportFileXls);
        assertTrue(exportFileXls.exists());
        assertTrue(exportFileXls.length() > 0);

        File exportFilePdf = new File("test_single.pdf");
        api.entity().demand(docId).export(templates.getRows().get(0), exportFilePdf);
        assertTrue(exportFilePdf.exists());
        assertTrue(exportFilePdf.length() > 0);

        File exportFileNoExt = new File("test_single_without_ext");
        api.entity().demand(docId).export(templates.getRows().get(0), ExportExtension.html, exportFileNoExt);
        assertTrue(exportFileNoExt.exists());
        assertTrue(exportFileNoExt.length() > 0);

        File exportFileDiffExt = new File("test_single_with_different_ext.html");
        api.entity().demand(docId).export(templates.getRows().get(0), ExportExtension.ods, exportFileDiffExt);
        assertTrue(exportFileDiffExt.exists());
        assertTrue(exportFileDiffExt.length() > 0);
    }

    @Test
    public void test_exportMultiple() throws IOException, LognexApiException {
        String docId;
        {
            OrganizationEntity ag = api.entity().organization().get().getRows().get(0);
            StoreEntity st = api.entity().store().get().getRows().get(0);

            DemandDocumentEntity doc = new DemandDocumentEntity();
            doc.setAgent(ag);
            doc.setOrganization(ag);
            doc.setStore(st);
            api.entity().demand().post(doc);

            docId = doc.getId();
        }

        ListEntity<TemplateEntity> templates = api.entity().demand().metadata().embeddedtemplate();
        assertNotNull(templates);
        assertNotNull(templates.getRows());
        assertFalse(templates.getRows().isEmpty());

        File exportFile = new File("test_complect.pdf");
        api.entity().demand(docId).export(
                exportFile,
                printRequest(templates.getRows().get(0)),
                printRequest(templates.getRows().get(1), 1),
                printRequest(templates.getRows().get(2), 2)
        );
        assertTrue(exportFile.exists());
        assertTrue(exportFile.length() > 0);
    }
}
