package ru.moysklad.remap_1_2.entities.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotificationSubscription {
    private NotificationGroups groups;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class NotificationGroups {
        private @JsonProperty("customer_order") NotificationGroup customerOrder;
        private NotificationGroup invoice;
        private NotificationGroup stock;
        private NotificationGroup retail;
        private NotificationGroup task;
        private @JsonProperty("data_exchange") NotificationGroup dataExchange;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class NotificationGroup {
        private Boolean enabled;
        private List<Channel> channels;
    }

    public enum Channel {
        EMAIL, PUSH
    }
}
