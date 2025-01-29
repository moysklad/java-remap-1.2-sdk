package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;
import ru.moysklad.remap_1_2.entities.products.markers.HasFiles;
import ru.moysklad.remap_1_2.responses.ListEntity;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Task extends MetaEntity implements Fetchable, HasFiles {
    private Employee author;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String description;
    private LocalDateTime dueToDate;
    private Employee assignee;
    private Boolean done;
    private LocalDateTime completed;
    private Employee implementer;
    private Optional<Agent> agent;
    private DocumentEntity operation;
    private ListEntity<TaskNote> notes;
    private ListEntity<AttachedFile> files;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class TaskNote extends MetaEntity {
        private Employee author;
        private LocalDateTime moment;
        private String text;
    }

    public Task(String id) {
        super(id);
    }

    @JsonGetter(value = "agent")
    public Optional<Agent> getOptionalAgent() {
        return agent;
    }

    public Agent getAgent() {
        return agent == null ? null : agent.orElse(null);
    }

    public void setAgent(Agent agent) {
        if (agent == null) {
            this.agent = Optional.empty();
        } else {
            this.agent = Optional.of(agent);
        }
    }
}
