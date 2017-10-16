package com.lognex.api.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Publication {

    private AbstractTemplate template;
    private String href;
}
