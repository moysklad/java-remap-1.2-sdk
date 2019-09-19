package com.lognex.api.entities.notifications;

import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
