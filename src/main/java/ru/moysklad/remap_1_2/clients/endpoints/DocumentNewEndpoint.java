package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.DocumentTemplate;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

public interface DocumentNewEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default T templateDocument() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(new Object()).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T templateDocument(DocumentTemplate documentTemplate) throws IOException, ApiClientException {
        if (documentTemplate.getDocument() != null) {
            MetaHrefUtils.fillMeta(documentTemplate.getDocument(), api().getHost() + API_PATH);
        } else if (documentTemplate.getDocuments() != null) {
            List<DocumentEntity> documents = documentTemplate.getDocuments();
            documents = documents.stream()
                    .map(d -> MetaHrefUtils.fillMeta(d, api().getHost() + API_PATH))
                    .collect(Collectors.toList());
            documentTemplate.setDocuments(documents);
        }
        return HttpRequestExecutor.
                path(api(), path() + "new").
                body(documentTemplate).
                put((Class<T>) entityClass());
    }

    @ApiEndpoint
    default T templateDocument(String templateClassString, DocumentEntity document) throws IOException, ApiClientException {
        DocumentTemplate documentTemplate = new DocumentTemplate();
        documentTemplate.setDocumentType(templateClassString);
        documentTemplate.setDocument(document);

        return templateDocument(documentTemplate);
    }

    @ApiEndpoint
    default T templateDocument(String templateClassString, List<DocumentEntity> documents) throws IOException, ApiClientException {
        DocumentTemplate documentTemplate = new DocumentTemplate();
        documentTemplate.setDocumentType(templateClassString);
        documentTemplate.setDocuments(documents);

        return templateDocument(documentTemplate);
    }
}
