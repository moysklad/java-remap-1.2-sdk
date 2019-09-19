package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.Publication;
import com.lognex.api.entities.Template;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
