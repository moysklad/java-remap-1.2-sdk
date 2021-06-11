package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.HasImagesEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.entities.products.markers.HasImages;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class EntityGetUpdateDeleteWithImageTest<T extends MetaEntity & HasImages> extends EntityGetUpdateDeleteTest
        implements FileTestBase<T> {

    @Test
    public void getImageTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        Image image = generateImage(getFile("entities/image/examples/testImage.jpg"));
        entity.setImages(new ListEntity<>());
        entity.getImages().setRows(Arrays.asList(image));
        entity = updateInstance(entity);
        ListEntity<Image> imagesEntities = getImagesEndpoint().getImages(entity, expandImage());
        List<Image> images = imagesEntities.getRows();

        assertEquals(1, images.size());
        assertEquals(image.getFilename(), images.get(0).getFilename());
    }

    @Test
    public void addImagesTest() throws IOException, ApiClientException {
        T entity = generateInstance();

        assertTrue(Objects.isNull(entity.getImages().getRows()));

        Image image = generateImage(getFile("entities/image/examples/testImage.jpg"));
        Image image2 = generateImage(getFile("entities/image/examples/testImage2.jpg"));
        List<Image> images = getImagesEndpoint().updateImages(entity, Arrays.asList(image, image2));

        assertEquals(2, images.size());
        assertEquals(image.getFilename(), images.get(0).getFilename());
        assertEquals(image2.getFilename(), images.get(1).getFilename());

        entity = getInstance(entity.getId(), expandImage());
        images = entity.getImages().getRows();

        assertEquals(2, images.size());
        assertEquals(image.getFilename(), images.get(0).getFilename());
        assertEquals(image2.getFilename(), images.get(1).getFilename());
    }

    @Test
    public void addImageTest() throws IOException, ApiClientException {
        T entity = generateInstance();

        assertTrue(Objects.isNull(entity.getImages().getRows()));

        Image image = generateImage(getFile("entities/image/examples/testImage.jpg"));
        List<Image> images = getImagesEndpoint().addImage(entity, image);

        assertEquals(1, images.size());
        assertEquals(image.getFilename(), images.get(0).getFilename());

        entity = getInstance(entity.getId(), expandImage());
        images = entity.getImages().getRows();

        assertEquals(1, images.size());
        assertEquals(image.getFilename(), images.get(0).getFilename());
    }

    @Test
    public void updateImagesTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        Image image = generateImage(getFile("entities/image/examples/testImage.jpg"));
        entity.setImages(new ListEntity<>());
        entity.getImages().setRows(Arrays.asList(image));
        entity = updateInstance(entity);

        Image image2 = generateImage(getFile("entities/image/examples/testImage2.jpg"));
        List<Image> images = getImagesEndpoint().updateImages(entity, Arrays.asList(image2));

        assertEquals(1, images.size());
        assertEquals(image2.getFilename(), images.get(0).getFilename());

        entity = getInstance(entity.getId(), expandImage());
        images = entity.getImages().getRows();

        assertEquals(1, images.size());
        assertEquals(image2.getFilename(), images.get(0).getFilename());
    }

    @Test
    public void deleteImageTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        Image image = generateImage(getFile("entities/image/examples/testImage.jpg"));
        entity.setImages(new ListEntity<>());
        entity.getImages().setRows(Arrays.asList(image));
        entity = updateInstance(entity);

        entity = getInstance(entity.getId(), expandImage());

        assertEquals(1, entity.getImages().getRows().size());

        Image receivedImage = entity.getImages().getRows().get(0);
        getImagesEndpoint().deleteImage(entity, receivedImage);
        entity = getInstance(entity.getId(), expandImage());

        assertEquals(0, entity.getImages().getRows().size());
    }

    @Test
    public void deleteImagesTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        Image image = generateImage(getFile("entities/image/examples/testImage.jpg"));
        Image image2 = generateImage(getFile("entities/image/examples/testImage2.jpg"));
        entity.setImages(new ListEntity<>());
        entity.getImages().setRows(Arrays.asList(image, image2));
        entity = updateInstance(entity);

        entity = getInstance(entity.getId(), expandImage());

        assertEquals(2, entity.getImages().getRows().size());

        getImagesEndpoint().deleteImages(entity, entity.getImages().getRows());
        entity = getInstance(entity.getId(), expandImage());

        assertEquals(0, entity.getImages().getRows().size());
    }


    private Image generateImage(File imageFile) throws IOException {
        Image image = new Image();
        image.setContent(imageFile);
        return image;
    }

    public File getFile(String relativePath) {
        String path = this
                .getClass()
                .getClassLoader()
                .getResource(relativePath)
                .getPath();
        return new File(path);
    }

    private ApiParam expandImage() {
        return new ApiParam(ApiParam.Type.expand) {
            @Override
            protected String render(String host) {
                return "images";
            }
        };
    }

    public HasImagesEndpoint getImagesEndpoint() {
        return (HasImagesEndpoint) entityClient();
    }
}
