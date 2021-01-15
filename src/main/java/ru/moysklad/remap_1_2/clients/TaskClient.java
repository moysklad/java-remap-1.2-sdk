package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Task;
import ru.moysklad.remap_1_2.entities.Task.TaskNote;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;
import java.util.List;

public final class TaskClient
        extends EntityClientBase
        implements
        GetListEndpoint<Task>,
        GetByIdEndpoint<Task>,
        MassCreateUpdateDeleteEndpoint<Task>,
        PostEndpoint<Task>,
        PutByIdEndpoint<Task>,
        DeleteByIdEndpoint {

    TaskClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/task/");
    }

    @ApiEndpoint
    public ListEntity<TaskNote> getNotes(String taskId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + taskId + "/notes").
                apiParams(params).
                list(TaskNote.class);
    }

    @ApiEndpoint
    public ListEntity<TaskNote> getNotes(Task task, ApiParam... params) throws IOException, ApiClientException {
        return getNotes(task.getId(), params);
    }

    @ApiEndpoint
    public TaskNote createNote(String taskId, TaskNote newEntity) throws IOException, ApiClientException {
        List<TaskNote> responseEntity = HttpRequestExecutor.
                path(api(), path() + taskId + "/notes").
                body(newEntity).
                postList(TaskNote.class);

        newEntity.set(responseEntity.get(0));
        return newEntity;
    }

    @ApiEndpoint
    public TaskNote createNote(Task task, TaskNote newEntity) throws IOException, ApiClientException {
        return createNote(task.getId(), newEntity);
    }

    @ApiEndpoint
    public TaskNote getNote(String taskId, String taskNoteId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + taskId + "/notes/" + taskNoteId).
                apiParams(params).
                get(TaskNote.class);
    }

    @ApiEndpoint
    public TaskNote getNote(Task task, String taskNoteId, ApiParam... params) throws IOException, ApiClientException {
        return getNote(task.getId(), taskNoteId, params);
    }

    @ApiEndpoint
    public TaskNote getNote(Task task, TaskNote taskNote, ApiParam... params) throws IOException, ApiClientException {
        return getNote(task, taskNote.getId(), params);
    }

    @ApiEndpoint
    public void updateNote(String taskId, String taskNoteId, TaskNote updatedEntity) throws IOException, ApiClientException {
        TaskNote responseEntity = HttpRequestExecutor
                .path(api(), path() + taskId + "/notes/" + taskNoteId)
                .body(updatedEntity)
                .put(TaskNote.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void updateNote(Task task, String taskNoteId, TaskNote updatedEntity) throws IOException, ApiClientException {
        updateNote(task.getId(), taskNoteId, updatedEntity);
    }

    @ApiEndpoint
    public void updateNote(Task task, TaskNote taskNote, TaskNote updatedEntity) throws IOException, ApiClientException {
        updateNote(task, taskNote.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void updateNote(Task task, TaskNote taskNote) throws IOException, ApiClientException {
        updateNote(task, taskNote.getId(), taskNote);
    }

    @ApiEndpoint
    public void deleteNote(String taskId, String taskNoteId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + taskId + "/notes/" + taskNoteId).
                delete();
    }

    @ApiEndpoint
    public void deleteNote(Task task, String taskNoteId) throws IOException, ApiClientException {
        deleteNote(task.getId(), taskNoteId);
    }

    @ApiEndpoint
    public void deleteNote(Task task, TaskNote taskNote) throws IOException, ApiClientException {
        deleteNote(task, taskNote.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Task.class;
    }
}
