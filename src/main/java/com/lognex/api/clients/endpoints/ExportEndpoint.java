package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.ExportExtension;
import com.lognex.api.entities.ExportRequestEntity;
import com.lognex.api.entities.ExportRequestEntity.ExportRequestItemEntity;
import com.lognex.api.entities.TemplateEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.MetaHrefUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.lognex.api.utils.Constants.API_PATH;

public interface ExportEndpoint extends Endpoint {
    @ApiEndpoint
    default void export(String id, TemplateEntity template, File file) throws IOException, LognexApiException {
        export(id, template, file, false);
    }

    @ApiEndpoint
    default void export(String id, TemplateEntity template, ExportExtension extension, File file) throws IOException, LognexApiException {
        export(id, template, extension, file, false);
    }

    @ApiEndpoint
    default void export(String id, File file, PrintRequest... printRequests) throws IOException, LognexApiException {
        export(id, file, false, printRequests);
    }

    @ApiEndpoint
    default void export(String id, ExportRequestEntity exportRequestEntity, File file) throws IOException, LognexApiException {
        export(id, exportRequestEntity, file, false);
    }

    @ApiEndpoint
    default void export(String id, TemplateEntity template, File file, boolean addPrintDocumentContentHeader) throws IOException, LognexApiException {
        ExportExtension parsedExtension = ExportExtension.extractFromFile(file);
        export(id, template, parsedExtension, file, addPrintDocumentContentHeader);
    }

    @ApiEndpoint
    default void export(String id, TemplateEntity template, ExportExtension extension, File file, boolean addPrintDocumentContentHeader) throws IOException, LognexApiException {
        ExportRequestEntity exportRequestEntity = new ExportRequestEntity();
        exportRequestEntity.setTemplate(template);
        exportRequestEntity.setExtension(extension);
        export(id, exportRequestEntity, file, addPrintDocumentContentHeader);
    }

    @ApiEndpoint
    default void export(String id, File file, boolean addPrintDocumentContentHeader, PrintRequest... printRequests) throws IOException, LognexApiException {
        ExportRequestEntity exportRequestEntity = new ExportRequestEntity();

        exportRequestEntity.setTemplates(new ArrayList<>());
        for (PrintRequest printRequest : printRequests) {
            ExportRequestItemEntity eri = new ExportRequestItemEntity();
            eri.setTemplate(printRequest.template);
            eri.setCount(printRequest.count);
            exportRequestEntity.getTemplates().add(eri);
        }

        export(id, exportRequestEntity, file, addPrintDocumentContentHeader);
    }

    @ApiEndpoint
    default void export(String id, ExportRequestEntity exportRequestEntity, File file, boolean addPrintDocumentContentHeader) throws IOException, LognexApiException {
        if (exportRequestEntity.getTemplate() != null) {
            MetaHrefUtils.fillMeta(exportRequestEntity.getTemplate(), api().getHost() + API_PATH);
        } else if (exportRequestEntity.getTemplates() != null) {
            exportRequestEntity.getTemplates()
                .forEach(t -> MetaHrefUtils.fillMeta(t.getTemplate(), api().getHost() + API_PATH));
        }
        HttpRequestExecutor req = HttpRequestExecutor.
                path(api(), path() + id + "/export/").
                body(exportRequestEntity);

        if (addPrintDocumentContentHeader) req.header("X-Lognex-Get-Content", "true");

        req.postAndSaveTo(file);
    }

    public static class PrintRequest {
        private TemplateEntity template;
        private int count;

        private PrintRequest(TemplateEntity template, int count) {
            this.template = template;
            this.count = count;
        }

        public static PrintRequest printRequest(TemplateEntity template) {
            return printRequest(template, 1);
        }

        public static PrintRequest printRequest(TemplateEntity template, int count) {
            return new PrintRequest(template, count);
        }
    }
}
