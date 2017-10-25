package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityInfoable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseItem extends EntityInfoable {

    private boolean archived;
}
