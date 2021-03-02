package ru.moysklad.remap_1_2.entities.notifications;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.RetailStore;
import ru.moysklad.remap_1_2.entities.agents.Employee;

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
