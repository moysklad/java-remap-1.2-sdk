package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractTemplate extends AbstractEntity {

    private String name;
    private String type = "entity";
    private String content;
}