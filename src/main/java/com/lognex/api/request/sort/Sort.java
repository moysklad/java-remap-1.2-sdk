package com.lognex.api.request.sort;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Sort {
    private String fieldName;
    private Direction direction;

    public enum  Direction {
        ASC("asc"), DESC("desc");

        private final String val;

        Direction(String val) {
            this.val = val;
        }
    }

    public String toSortString() {
        return fieldName + "," + direction.val;
    }
}
