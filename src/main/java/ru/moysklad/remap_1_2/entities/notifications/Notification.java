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
public abstract class Notification extends MetaEntity {
    private LocalDateTime created;
    private Boolean read;
    private String title;
    private String description;
}
