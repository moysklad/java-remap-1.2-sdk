package com.lognex.api.entities.notifications;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationSubscribeTermsExpired extends NotificationSubscribeExpired {
    private Integer daysLeft;
}
