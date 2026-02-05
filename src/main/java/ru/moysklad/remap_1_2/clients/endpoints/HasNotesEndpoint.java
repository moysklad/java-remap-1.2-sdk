package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.Note;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface HasNotesEndpoint extends Endpoint {
    @ApiEndpoint
    default ListEntity<Note> getNotes(String documentId) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/").
                list(Note.class);
    }

    @ApiEndpoint
    default Note getNote(String documentId, String noteId) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/" + noteId).
                get(Note.class);
    }

    @ApiEndpoint
    default Note noteCreate(String documentId, Note note) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/").
                body(note).
                post(Note.class);
    }

    @ApiEndpoint
    default void updateNote(String documentId, String noteId, Note note) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/" + noteId).
                body(note).
                put(Note.class);
    }

    @ApiEndpoint
    default void deleteNote(String documentId, String noteId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/notes/" + noteId).
                delete();
    }

}
