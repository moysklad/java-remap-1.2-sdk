package com.lognex.api.entities.notifications;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Employee;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationTask extends Notification {
    private Employee performedBy;
    private TaskNotificationInfo task;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class TaskNotificationInfo extends MetaEntity {
        private LocalDateTime deadline;
    }
}
