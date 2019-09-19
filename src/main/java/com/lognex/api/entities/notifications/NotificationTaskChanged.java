package com.lognex.api.entities.notifications;

import com.lognex.api.entities.agents.Agent;
import com.lognex.api.entities.agents.Employee;
import com.lognex.api.entities.documents.DocumentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationTaskChanged extends NotificationTask {
    private Diff diff;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Change<T> {
        private T oldValue;
        private T newValue;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class Diff {
        private Change<String> description;
        private Change<LocalDateTime> deadline;
        private Change<Agent> agentLink;
        private Change<DocumentEntity> documentLink;
        private Change<Employee> assignee;
        private Change<String> noteContent;
    }
}
