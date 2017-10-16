package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityInfoable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseItem extends AbstractEntityInfoable {

    private boolean archived;
}
