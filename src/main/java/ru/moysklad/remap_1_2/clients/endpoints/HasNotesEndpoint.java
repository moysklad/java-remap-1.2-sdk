package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Note;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;
import java.util.List;

public interface HasNotesEndpoint<T extends MetaEntity>  extends Endpoint {
    @ApiEndpoint
    default ListEntity<Note> getNotes(String documentId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/")
                        .apiParams(params).
                list(Note.class);
    }

    @ApiEndpoint
    default Note getNote(T entity, String noteId) throws IOException, ApiClientException {
        return getNote(entity.getId(), noteId);
    }

    @ApiEndpoint
    default Note getNote(T entity, Note note) throws IOException, ApiClientException {
        return getNote(entity, note.getId());
    }

    @ApiEndpoint
    default ListEntity<Note> getNotes(T entity, ApiParam... params) throws IOException, ApiClientException {
        return getNotes(entity.getId(), params);
    }

    @ApiEndpoint
    default Note getNote(String documentId, String noteId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/" + noteId)
                        .apiParams()
                        .get(Note.class);
    }

    @ApiEndpoint
    default Note createNote(String documentId, Note note) throws IOException, ApiClientException {
        List<Note> responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/notes").
                body(note).
                postList(Note.class);
        note.set(responseEntity.get(0));
        return note;
    }

    @ApiEndpoint
    default void updateNote(String documentId, String noteId, Note note) throws IOException, ApiClientException {
        Note responseNote = HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/" + noteId).
                body(note).
                put(Note.class);
        note.set(responseNote);
    }

    @ApiEndpoint
    default void updateNote(T entity, String noteId, Note updatedEntity) throws IOException, ApiClientException {
        updateNote(entity.getId(), noteId, updatedEntity);
    }

    @ApiEndpoint
    default void updateNote(T entity, Note note, Note updatedEntity) throws IOException, ApiClientException {
        updateNote(entity, note.getId(), updatedEntity);
    }

    @ApiEndpoint
    default void updateNote(T entity, Note note) throws IOException, ApiClientException {
        updateNote(entity, note.getId(), note);
    }


    @ApiEndpoint
    default void deleteNote(String documentId, String noteId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/" + noteId).
                delete();
    }

    @ApiEndpoint
    default void deleteNote(T entity, String noteId) throws IOException, ApiClientException {
        deleteNote(entity.getId(), noteId);
    }

    @ApiEndpoint
    default void deleteNote(T entity, Note note) throws IOException, ApiClientException {
        deleteNote(entity, note.getId());
    }

}
