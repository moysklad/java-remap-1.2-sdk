package com.lognex.api.entities.notifications;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.RetailStore;
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
public class NotificationRetailShift extends Notification {
    private Employee user;
    private RetailStore retailStore;
    private RetailShiftNotificationInfo retailShift;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class RetailShiftNotificationInfo extends MetaEntity {
        private LocalDateTime open;
        private LocalDateTime close;
        private Long proceed;
    }
}
