package com.lognex.api.model.base;

import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntity {
    private ID id;
    private ID accountId;
}
