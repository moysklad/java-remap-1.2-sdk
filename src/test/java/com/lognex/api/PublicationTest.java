package com.lognex.api;

import com.lognex.api.entities.Publication;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.Template;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.Demand;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.MetaHrefUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.junit.Assert.*;

public class PublicationTest {
    private ApiClient api;

    @Before
    public void init() {
        api = new ApiClient(
                System.getenv("API_HOST"),
                true, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );
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

        Publication publication = api.entity().demand().publish(docId, templates.getRows().get(0));
        assertEquals(templates.getRows().get(0).getMeta(), publication.getTemplate().getMeta());
        assertFalse(isEmpty(publication.getHref()));
        assertTrue(publication.getHref().endsWith(".html"));

        Optional<String> publicationId = MetaHrefUtils.getIdFromHref(publication.getMeta().getHref());
        assertTrue(publicationId.isPresent());

        Publication retrievedPublication = api.entity().demand().getPublication(docId, publicationId.get());
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

        api.entity().demand().publish(docId, templates.getRows().get(0));
        ListEntity<Publication> publications = api.entity().demand().getPublications(docId);
        assertEquals(Integer.valueOf(1), publications.getMeta().getSize());
        assertEquals(templates.getRows().get(0).getMeta(), publications.getRows().get(0).getTemplate().getMeta());

        api.entity().demand().publish(docId, templates.getRows().get(1));
        publications = api.entity().demand().getPublications(docId);
        assertEquals(Integer.valueOf(2), publications.getMeta().getSize());

        Publication publicationToDelete = publications.getRows().get(0);
        Optional<String> publicationId = MetaHrefUtils.getIdFromHref(publicationToDelete.getMeta().getHref());
        assertTrue(publicationId.isPresent());

        api.entity().demand().delelePublication(docId, publicationId.get());
        publications = api.entity().demand().getPublications(docId);
        assertEquals(Integer.valueOf(1), publications.getMeta().getSize());
        assertNotEquals(publicationToDelete, publications.getRows().get(0));
    }
}
