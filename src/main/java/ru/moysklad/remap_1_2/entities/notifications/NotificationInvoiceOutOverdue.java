package ru.moysklad.remap_1_2.entities.notifications;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.MetaEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationInvoiceOutOverdue extends Notification {
    private InvoiceNotificationInfo invoice;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class InvoiceNotificationInfo extends MetaEntity {
        private LocalDateTime paymentPlannedMoment;
        private Long sum;
        private String customerName;
    }
}
