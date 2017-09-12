package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Group extends AbstractEntity {
    private String name;

    public Group(ID id) {
        setId(id);
    }
}
