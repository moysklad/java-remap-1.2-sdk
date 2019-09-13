package com.lognex.api.entities.notifications;

import com.google.gson.*;
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
public class Notification extends MetaEntity {
    private LocalDateTime created;
    private Boolean read;
    private String title;
    private String description;
}
