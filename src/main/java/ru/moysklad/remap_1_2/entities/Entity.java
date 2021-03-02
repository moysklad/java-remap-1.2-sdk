package ru.moysklad.remap_1_2.entities;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Класс сущности
 */
public abstract class Entity {
    private static final Logger logger = LoggerFactory.getLogger(Entity.class);

    /**
     * Присваивает полям этой сущности значения полей другой сущности
     */
    public <T extends Entity> void set(T other) {
        for (Method method : other.getClass().getMethods()) {
            if (!method.getName().startsWith("get")) continue;

            Method setter;
            try {
                setter = (this.getClass().getMethod(method.getName().replace("get", "set"), method.getReturnType()));
            } catch (NoSuchMethodException e) {
                logger.trace("Не удалось найти метод " + method.getName().replace("get", "set") + ". Поле не будет скопировано.");
                continue;
            }

            try {
                Object o = method.invoke(other);
                if (o instanceof Entity) {
                    o = clone((Entity) o);
                }
                setter.invoke(this, o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("Ошибка при копировании полей сущности", e);
            }
        }
    }

    /**
     * Клонирует сущность, создавая новые объекты для полей со
     * значениями оригинальной сущности
     */
    public static <T extends Entity> T clone(T original) {
        try {
            T clone = (T) original.getClass().getConstructor().newInstance();
            clone.set(original);
            return clone;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Ошибка при клонировании сущности", e);
            return original;
        }
    }
}
