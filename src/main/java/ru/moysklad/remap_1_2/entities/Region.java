package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Region extends MetaEntity implements Fetchable{
    private LocalDateTime updated;
    private String code;
    private String externalCode;

    public Region(String id) {
        super(id);
    }
}
