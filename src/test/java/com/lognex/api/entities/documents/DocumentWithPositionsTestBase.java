package com.lognex.api.entities.documents;

import com.lognex.api.clients.endpoints.DocumentPositionsEndpoint;
import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public abstract class DocumentWithPositionsTestBase extends EntityTestBase {
    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = simpleEntityFactory.createSimpleProduct();

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        ((DocumentPositionsEndpoint) entityClient()).postPosition(document.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionByEntityTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = simpleEntityFactory.createSimpleProduct();

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        ((DocumentPositionsEndpoint) entityClient()).postPosition(document, position);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionsByIdTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = simpleEntityFactory.createSimpleProduct();
            products.add(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        ((DocumentPositionsEndpoint) entityClient()).postPositions(document.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void createPositionsByEntityTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());

        ListEntity<DocumentPosition> originalPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = simpleEntityFactory.createSimpleProduct();
            products.add(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        ((DocumentPositionsEndpoint) entityClient()).postPositions(document, positions);
        ListEntity<DocumentPosition> retrievedPositions = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void getPositionTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).putPosition(document.getId(), position.getId(), position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).putPosition(document, position.getId(), position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).putPosition(document, position, position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        DocumentPosition position = positions.get(0);
        DocumentPosition retrievedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document.getId(), position.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(position.getQuantity() + randomDouble(1, 1, 3)));
        position.setQuantity(quantity);
        ((DocumentPositionsEndpoint) entityClient()).putPosition(document, position);

        putPositionAsserts(document, position, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        ListEntity<DocumentPosition> positionsBefore = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        ((DocumentPositionsEndpoint) entityClient()).delete(document.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        ListEntity<DocumentPosition> positionsBefore = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        ((DocumentPositionsEndpoint) entityClient()).delete(document, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        DocumentEntity document = (DocumentEntity) simpleEntityFactory.createSimple(entityClass());
        List<DocumentPosition> positions = createSimplePositions(document);

        ListEntity<DocumentPosition> positionsBefore = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        ((DocumentPositionsEndpoint) entityClient()).delete(document, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = ((DocumentPositionsEndpoint) entityClient()).getPositions(document);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(DocumentEntity document) throws IOException, LognexApiException {
        List<DocumentPosition> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = simpleEntityFactory.createSimpleProduct();
            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        return ((DocumentPositionsEndpoint) entityClient()).postPositions(document, positions);
    }

    private void getPositionAsserts(DocumentPosition position, DocumentPosition retrievedPosition) {
        assertEquals(position.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) position.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(position.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(DocumentEntity document, DocumentPosition position, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = ((DocumentPositionsEndpoint) entityClient()).getPosition(document, position.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
