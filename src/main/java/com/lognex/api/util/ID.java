package com.lognex.api.util;

public class ID {
    private String value;

    public ID(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof ID) {
            ID id = (ID) o;
            return value.equals(id.getValue());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String toString() {
        return value;
    }
}
