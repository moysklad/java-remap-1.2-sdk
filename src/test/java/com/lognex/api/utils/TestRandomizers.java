package com.lognex.api.utils;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public interface TestRandomizers {
    Random rnd = new Random();

    default String randomString() {
        return randomString(5 + rnd.nextInt(10));
    }

    default double randomDouble(double from, double len, int decimals) {
        Integer mult = (int) Math.pow(10, decimals);
        len++;
        return ((double) rnd.nextInt((int) (len * mult)) / mult) + from;
    }

    default int randomInteger(int fromIncl, int toIncl) {
        return fromIncl + rnd.nextInt(toIncl - fromIncl + 1);
    }

    default long randomLong(long fromIncl, long toIncl) {
        return fromIncl + rnd.nextLong() % (toIncl - fromIncl + 1);
    }

    default String randomString(int length) {
        StringBuilder str = new StringBuilder();

        while (str.length() < length) {
            str.append(UUID.randomUUID().toString().replaceAll("-", ""));
        }

        if (str.length() > length) {
            str = new StringBuilder(str.substring(0, length));
        }

        return str.chars().
                mapToObj(i -> (char) i).
                map(ch -> Character.toString(rnd.nextBoolean() ? Character.toUpperCase(ch) : Character.toLowerCase(ch))).
                collect(Collectors.joining(""));
    }

    default <T extends Enum<?>> T randomEnumGeneric(Class<T> enumClass) {
        int x = rnd.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    default Enum randomEnum(Class<? extends Enum> enumClass) {
        int x = rnd.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    default boolean randomBoolean() {
        return rnd.nextBoolean();
    }
}
