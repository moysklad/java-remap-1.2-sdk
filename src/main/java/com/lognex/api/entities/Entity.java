package com.lognex.api.entities;

import java.lang.reflect.Field;

public abstract class Entity implements Cloneable {
    public <T extends Entity> void set(T other) {
        for (Field field : other.getClass().getFields()) {
            try {
                Object o = field.get(other);
                if (o instanceof Entity) {
                    o = clone((Entity) o);
                }
                field.set(this, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public static <T extends Entity> T clone(T original) {
        try {
            T clone = (T) original.clone();
            clone.set(original);
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
