package com.lognex.api.entities.builders;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.ProjectEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import lombok.NonNull;

import java.time.LocalDateTime;

public class ProjectBuilder {
    private ProjectBuilder(LognexApi api) {
        this.api = api;
        project = new ProjectEntity();
    }

    static public ProjectBuilder getBuilder(@NonNull LognexApi api) {
        return new ProjectBuilder(api);
    }

    public ProjectBuilder setOwner(String id) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(id);
        employee.setMeta(MetaBuilder.getBuilder(api, id, api.entity().employee())
                .setMetadataHref()
                .build());
        project.setOwner(employee);
        return this;
    }

    public ProjectBuilder setShared(Boolean shared) {
        project.setShared(shared);
        return this;
    }

    public ProjectBuilder setCode(String code) {
        project.setCode(code);
        return this;
    }

    public ProjectBuilder setExternalCode(String externalCode) {
        project.setExternalCode(externalCode);
        return this;
    }

    public ProjectBuilder setDescription(String description) {
        project.setDescription(description);
        return this;
    }

    public ProjectBuilder setArchived(Boolean archived) {
        project.setArchived(archived);
        return this;
    }

    public ProjectBuilder setUpdated(LocalDateTime updated) {
        project.setUpdated(updated);
        return this;
    }

    public ProjectBuilder setGroup(String id) {
        GroupEntity group = new GroupEntity();
        group.setId(id);
        group.setMeta(MetaBuilder.getBuilder(api, id, api.entity().group())
                .setMetadataHref()
                .build());
        project.setGroup(group);
        return this;
    }

    public ProjectEntity build() {
        return project;
    }

    private ProjectEntity project;
    private LognexApi api;
}
