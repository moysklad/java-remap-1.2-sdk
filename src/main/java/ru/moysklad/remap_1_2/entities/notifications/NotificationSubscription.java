package ru.moysklad.remap_1_2.entities.notifications;

import com.google.gson.annotations.SerializedName;
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
        private @SerializedName("customer_order") NotificationGroup customerOrder;
        private NotificationGroup invoice;
        private NotificationGroup stock;
        private NotificationGroup retail;
        private NotificationGroup task;
        private @SerializedName("data_exchange") NotificationGroup dataExchange;
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
