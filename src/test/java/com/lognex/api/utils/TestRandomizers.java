package com.lognex.api.utils;

import com.google.common.collect.ImmutableList;
import com.lognex.api.entities.Meta.Type;

import java.util.*;
import java.util.stream.Collectors;

import static com.lognex.api.entities.Meta.Type.*;

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
        return fromIncl + (long) (rnd.nextDouble() * (toIncl - fromIncl + 1));
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

    default String randomUrl() {
        return "http://www." + randomString() + ".com";
    }

    default Type randomWebhookType() {
        // not all available types for webhooks, but should be enough for testing
        List<Type> webhookTypes = ImmutableList.of(DEMAND, LOSS, PRODUCT, CUSTOMER_ORDER, EMPLOYEE, GROUP, PROJECT, VARIANT, SERVICE, STORE);
        return webhookTypes.get(randomInteger(0, webhookTypes.size() - 1));
    }

    default <T extends Enum<?>> T randomEnumGeneric(Class<T> enumClass) {
        int x = rnd.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    default Enum randomEnum(Class<? extends Enum> enumClass) {
        int x = rnd.nextInt(enumClass.getEnumConstants().length);
        return enumClass.getEnumConstants()[x];
    }

    default String randomStringTail() {
        return randomString(3) + "_" + new Date().getTime();
    }

    default boolean randomBoolean() {
        return rnd.nextBoolean();
    }
}
