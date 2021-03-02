package ru.moysklad.remap_1_2.clients.endpoints;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Publication;
import ru.moysklad.remap_1_2.entities.Template;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface PublicationEndpoint extends Endpoint {
    @ApiEndpoint
    default ListEntity<Publication> getPublications(String documentId) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/publication/").
                list(Publication.class);
    }

    @ApiEndpoint
    default Publication getPublication(String documentId, String publicationId) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/publication/" + publicationId).
                get(Publication.class);
    }

    @ApiEndpoint
    default Publication publish(String documentId, Template template) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/publication/").
                body(new PublicationPayload(template)).
                post(Publication.class);
    }

    @ApiEndpoint
    default void delelePublication(String documentId, String publicationId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/publication/" + publicationId).
                delete();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    class PublicationPayload {
        private Template template;

        PublicationPayload(Template template) {
            this.template = template;
        }
    }
}
