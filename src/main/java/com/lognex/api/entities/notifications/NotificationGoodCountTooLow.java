package com.lognex.api.entities.notifications;

import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationGoodCountTooLow extends Notification {
    private Double actualBalance;
    private Double minimumBalance;
    private MetaEntity good;
}
