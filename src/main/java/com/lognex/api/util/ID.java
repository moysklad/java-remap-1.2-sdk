package com.lognex.api.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ID {
    private String value;

    public String toString() {
        return value;
    }
}
