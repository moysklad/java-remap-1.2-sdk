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
public class NotificationCustomerOrder extends Notification {
    private OrderNotificationInfo order;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class OrderNotificationInfo extends MetaEntity {
        private LocalDateTime deliveryPlannedMoment;
        private Long sum;
        private String agentName;
    }
}
