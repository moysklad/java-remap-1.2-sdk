package com.lognex.api.entities.permissions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MailActivationRequired {

    private Boolean mailActionvationRequired;
}
