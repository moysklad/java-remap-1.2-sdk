package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.ExportExtension;
import com.lognex.api.entities.ExportRequestEntity;
import com.lognex.api.entities.ExportRequestEntity.ExportRequestItemEntity;
import com.lognex.api.entities.TemplateEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface ExportEndpoint extends Endpoint {
    @ApiEndpoint
    default void export(TemplateEntity template, File file) throws IOException, LognexApiException {
        export(template, file, false);
    }

    @ApiEndpoint
    default void export(TemplateEntity template, ExportExtension extension, File file) throws IOException, LognexApiException {
        export(template, extension, file, false);
    }

    @ApiEndpoint
    default void export(File file, PrintRequest... printRequests) throws IOException, LognexApiException {
        export(file, false, printRequests);
    }

    @ApiEndpoint
    default void export(ExportRequestEntity exportRequestEntity, File file) throws IOException, LognexApiException {
        export(exportRequestEntity, file, false);
    }

    @ApiEndpoint
    default void export(TemplateEntity template, File file, boolean addPrintDocumentContentHeader) throws IOException, LognexApiException {
        ExportExtension parsedExtension = ExportExtension.extractFromFile(file);
        export(template, parsedExtension, file, addPrintDocumentContentHeader);
    }

    @ApiEndpoint
    default void export(TemplateEntity template, ExportExtension extension, File file, boolean addPrintDocumentContentHeader) throws IOException, LognexApiException {
        ExportRequestEntity exportRequestEntity = new ExportRequestEntity();
        exportRequestEntity.setTemplate(template);
        exportRequestEntity.setExtension(extension);
        export(exportRequestEntity, file, addPrintDocumentContentHeader);
    }

    @ApiEndpoint
    default void export(File file, boolean addPrintDocumentContentHeader, PrintRequest... printRequests) throws IOException, LognexApiException {
        ExportRequestEntity exportRequestEntity = new ExportRequestEntity();

        exportRequestEntity.setTemplates(new ArrayList<>());
        for (PrintRequest printRequest : printRequests) {
            ExportRequestItemEntity eri = new ExportRequestItemEntity();
            eri.setTemplate(printRequest.template);
            eri.setCount(printRequest.count);
            exportRequestEntity.getTemplates().add(eri);
        }

        export(exportRequestEntity, file, addPrintDocumentContentHeader);
    }

    @ApiEndpoint
    default void export(ExportRequestEntity exportRequestEntity, File file, boolean addPrintDocumentContentHeader) throws IOException, LognexApiException {
        HttpRequestExecutor req = HttpRequestExecutor.
                path(api(), path() + "export/").
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
