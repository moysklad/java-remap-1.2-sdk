package com.lognex.api.utils;

import com.google.common.collect.ImmutableList;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.Address;
import com.lognex.api.entities.Meta.Type;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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

    default Address randomAddress(ApiClient api) throws IOException, ApiClientException {
        Address address = new Address();
        address.setPostalCode(randomString());
        address.setCountry(api.entity().country().get("9df7c2c3-7782-4c5c-a8ed-1102af611608"));
        address.setRegion(api.entity().region().get("00000000-0000-0000-0000-000000000077"));
        address.setCity(randomString());
        address.setStreet(randomString());
        address.setHouse(randomString());
        address.setApartment(randomString());
        address.setAddInfo(randomString());
        address.setComment(randomString());
        return address;
    }
}
