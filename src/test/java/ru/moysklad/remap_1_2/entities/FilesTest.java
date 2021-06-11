package ru.moysklad.remap_1_2.entities;

import com.google.common.collect.ImmutableList;
import ru.moysklad.remap_1_2.clients.endpoints.HasFilesEndpoint;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public interface FilesTest<T extends MetaEntity & HasFiles> extends FileTestBase<T> {

    void testFiles() throws IOException, ApiClientException;

    default void doTestFiles() throws IOException, ApiClientException {
        getFileTest();
        addFilesTest();
        addFileTest();
        updateFilesTest();
        deleteFilesTest();
        deleteFileTest();
    }

    default void getFileTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        AttachedFile file = generateFile(getFile("entities/image/examples/testImage.jpg"));
        entity.setFiles(new ListEntity<>());
        entity.getFiles().setRows(Arrays.asList(file));
        entity = updateInstance(entity);
        ListEntity<AttachedFile> filesEntities = getFilesEndpoint().getFiles(entity, expandFile());
        List<AttachedFile> files = filesEntities.getRows();

        assertEquals(1, files.size());
        assertEquals(file.getFilename(), files.get(0).getFilename());
    }

    default void addFilesTest() throws IOException, ApiClientException {
        T entity = generateInstance();

        assertTrue(Objects.isNull(entity.getFiles().getRows()));

        AttachedFile file = generateFile(getFile("entities/image/examples/testImage.jpg"));
        AttachedFile file2 = generateFile(getFile("entities/image/examples/testImage2.jpg"));
        List<AttachedFile> files = getFilesEndpoint().updateFiles(entity, Arrays.asList(file, file2));

        assertEquals(2, files.size());
        assertEquals(file.getFilename(), files.get(0).getFilename());
        assertEquals(file2.getFilename(), files.get(1).getFilename());

        entity = getInstance(entity.getId(), expandFile());
        files = entity.getFiles().getRows();

        assertEquals(2, files.size());
        assertEquals(file.getFilename(), files.get(0).getFilename());
        assertEquals(file2.getFilename(), files.get(1).getFilename());
    }

    default void addFileTest() throws IOException, ApiClientException {
        T entity = generateInstance();

        assertTrue(Objects.isNull(entity.getFiles().getRows()));

        AttachedFile file = generateFile(getFile("entities/image/examples/testImage.jpg"));
        List<AttachedFile> files = getFilesEndpoint().addFile(entity, file);

        assertEquals(1, files.size());
        assertEquals(file.getFilename(), files.get(0).getFilename());

        entity = getInstance(entity.getId(), expandFile());
        files = entity.getFiles().getRows();

        assertEquals(1, files.size());
        assertEquals(file.getFilename(), files.get(0).getFilename());
    }

    default void updateFilesTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        AttachedFile file = generateFile(getFile("entities/image/examples/testImage.jpg"));
        entity.setFiles(new ListEntity<>());
        entity.getFiles().setRows(Arrays.asList(file));
        entity = updateInstance(entity);

        AttachedFile file2 = generateFile(getFile("entities/image/examples/testImage2.jpg"));
        List<AttachedFile> files = getFilesEndpoint().updateFiles(entity, Arrays.asList(file2));

        final ImmutableList<String> bothFilenames = ImmutableList.of(file.getFilename(), file2.getFilename());
        assertEquals(2, files.size());
        assertTrue(files.stream().map(Attachment::getFilename).collect(Collectors.toSet()).containsAll(bothFilenames));

        entity = getInstance(entity.getId(), expandFile());
        files = entity.getFiles().getRows();

        assertEquals(2, files.size());
        assertTrue(files.stream().map(Attachment::getFilename).collect(Collectors.toSet()).containsAll(bothFilenames));
    }

    default void deleteFileTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        AttachedFile file = generateFile(getFile("entities/image/examples/testImage.jpg"));
        entity.setFiles(new ListEntity<>());
        entity.getFiles().setRows(Arrays.asList(file));
        entity = updateInstance(entity);

        entity = getInstance(entity.getId(), expandFile());

        assertEquals(1, entity.getFiles().getRows().size());

        AttachedFile receivedFile = entity.getFiles().getRows().get(0);
        getFilesEndpoint().deleteFile(entity, receivedFile);
        entity = getInstance(entity.getId(), expandFile());

        assertEquals(0, entity.getFiles().getRows().size());
    }

    default void deleteFilesTest() throws IOException, ApiClientException {
        T entity = generateInstance();
        AttachedFile file = generateFile(getFile("entities/image/examples/testImage.jpg"));
        AttachedFile file2 = generateFile(getFile("entities/image/examples/testImage2.jpg"));
        entity.setFiles(new ListEntity<>());
        entity.getFiles().setRows(Arrays.asList(file, file2));
        entity = updateInstance(entity);

        entity = getInstance(entity.getId(), expandFile());

        assertEquals(2, entity.getFiles().getRows().size());

        getFilesEndpoint().deleteFiles(entity, entity.getFiles().getRows());
        entity = getInstance(entity.getId(), expandFile());

        assertEquals(0, entity.getFiles().getRows().size());
    }


    default AttachedFile generateFile(File fileFile) throws IOException {
        AttachedFile file = new AttachedFile();
        file.setContent(fileFile);
        return file;
    }

    default ApiParam expandFile() {
        return new ApiParam(ApiParam.Type.expand) {
            @Override
            protected String render(String host) {
                return "files";
            }
        };
    }

    default HasFilesEndpoint getFilesEndpoint() {
        return (HasFilesEndpoint) entityClient();
    }
}
