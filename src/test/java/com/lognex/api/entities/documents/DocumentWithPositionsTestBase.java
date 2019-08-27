package com.lognex.api.entities.documents;

import com.lognex.api.clients.endpoints.DocumentPositionsEndpoint;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public abstract class DocumentWithPositionsTestBase extends EntityGetUpdateDeleteTest {
    @Test
    public void createPositionByIdTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        DocumentPosition position = new DocumentPosition();

        Product product = simpleEntityManager.createSimple(Product.class, true);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        ((DocumentPositionsEndpoint) entityClient()).createPosition(document.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionByEntityTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        DocumentPosition position = new DocumentPosition();

        Product product = simpleEntityManager.createSimple(Product.class, true);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        ((DocumentPositionsEndpoint) entityClient()).createPosition(document, position);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionsByIdTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            Product product = simpleEntityManager.createSimple(Product.class, true);
            products.add(product);

            position.setAssortment(product);
            DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(3);
            position.setQuantity(Double.valueOf(df.format(randomDouble(1, 5, 3))));

            positions.add(position);
        }

        ((DocumentPositionsEndpoint) entityClient()).createPositions(document.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            Product product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void createPositionsByEntityTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            Product product = simpleEntityManager.createSimple(Product.class, true);
            products.add(product);

            position.setAssortment(product);
            DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(3);
            position.setQuantity(Double.valueOf(df.format(randomDouble(1, 5, 3))));

            positions.add(position);
        }

        ((DocumentPositionsEndpoint) entityClient()).createPositions(document, positions);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            Product product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((Product) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void getPositionTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(3);
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).updatePosition(document.getId(), position.getId(), position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(3);
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).updatePosition(document, position.getId(), position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(3);
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).updatePosition(document, position, position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(3);
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).updatePosition(document, position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        ListEntity<DocumentPosition> positionsBefore = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        ((DocumentPositionsEndpoint) entityClient()).deletePosition(document.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((Product) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((Product) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        ListEntity<DocumentPosition> positionsBefore = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        ((DocumentPositionsEndpoint) entityClient()).deletePosition(document, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((Product) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((Product) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, ApiClientException {
        DocumentEntity document = (DocumentEntity) simpleEntityManager.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        ListEntity<DocumentPosition> positionsBefore = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        ((DocumentPositionsEndpoint) entityClient()).deletePosition(document, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((Product) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((Product) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(DocumentEntity document) throws IOException, ApiClientException {
        List<DocumentPosition> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            Product product = simpleEntityManager.createSimple(Product.class, true);
            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        return ((DocumentPositionsEndpoint) entityClient()).createPositions(document, positions);
    }

    private void getPositionAsserts(DocumentPosition position, DocumentPosition retrievedPosition) {
        assertEquals(position.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((Product) position.getAssortment()).getMeta().getHref(),
                ((Product) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(position.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(DocumentEntity document, DocumentPosition position, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, ApiClientException {
        DocumentPosition retrievedUpdatedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document, position.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((Product) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((Product) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
