package com.lognex.api.model.entity.good;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Alcoholic {

    private boolean excise;
    private int type;
    private double strength;
    private double volume;

}
